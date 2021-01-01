package dts.logic.item;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dts.Application;
import dts.boundaries.IdBoundary;
import dts.boundaries.ItemBoundary;
import dts.boundaries.ItemIdBoundary;
import dts.boundaries.UserIdBoundary;
import dts.data.IdGeneratorEntity;
import dts.data.ItemEntity;
import dts.logic.IdGeneratorEntityDao;
import dts.util.ObjNotFoundException;

@Service
public class RdbItemsService implements EnhancedItemsService {
	private ItemsDao itemsDao;
	private ItemConverter itemConverter;
	private IdGeneratorEntityDao IdGeneratorEntityDao;

	@Autowired
	public RdbItemsService(ItemsDao itemsDao, ItemConverter itemConverter, IdGeneratorEntityDao IdGeneratorEntityDao) {
		super();
		this.itemsDao = itemsDao;
		this.itemConverter = itemConverter;
		this.IdGeneratorEntityDao = IdGeneratorEntityDao;
	}

	@Override
	@Transactional
	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem) throws Exception {
		try {
			ItemEntity entity = this.itemConverter.toEntity(newItem);
			if (entity.getName() == null || entity.getName().trim().isEmpty()) {
				throw new RuntimeException("item name can not be empty");
			}

			if (entity.getType() == null || entity.getType().trim().isEmpty()) {
				throw new RuntimeException("item type can not be empty");
			}

			IdGeneratorEntity idGeneratorEntity = new IdGeneratorEntity();
			idGeneratorEntity = this.IdGeneratorEntityDao.save(idGeneratorEntity);
			UUID newId = idGeneratorEntity.getId();
			this.IdGeneratorEntityDao.deleteById(newId);

			IdBoundary id = new IdBoundary(newId.toString());
			entity.setItemId(id.toString());
			entity.setCreatedTimestamp(new Date());

			UserIdBoundary createdBy = new UserIdBoundary(managerSpace, managerEmail);

			entity.setCreatedBy(createdBy.toString());

			return this.itemConverter.toBoundary(this.itemsDao.save(entity));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional
	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,
			ItemBoundary update) throws Exception {
		Optional<ItemEntity> exiting = this.itemsDao.findById(itemSpace + Application.ID_DELIMITER + itemId);

		if (exiting.isPresent()) {
			ItemEntity existingEntity = exiting.get();
			ItemEntity updateEntity = this.itemConverter.toEntity(update);
			if (updateEntity.getName() == null || updateEntity.getName().trim().isEmpty()) {
				throw new RuntimeException("item name can not be empty");
			}

			if (updateEntity.getType() == null || updateEntity.getType().trim().isEmpty()) {
				throw new RuntimeException("item type can not be empty");
			}

			updateEntity.setItemId(existingEntity.getItemId());
			updateEntity.setCreatedTimestamp(existingEntity.getCreatedTimestamp());
			updateEntity.setCreatedBy(existingEntity.getCreatedBy());
			updateEntity.setChildren(existingEntity.getChildren());
			updateEntity.setParents(existingEntity.getParents());

			return this.itemConverter.toBoundary(this.itemsDao.save(updateEntity));
		} else {
			throw new ObjNotFoundException("item with id: " + itemId + " could not be found");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAll(String userSpace, String userEmail) {
		return StreamSupport.stream(this.itemsDao.findAll().spliterator(), false)
				.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId)
			throws Exception {
		Optional<ItemEntity> exiting = this.itemsDao.findById(itemSpace + Application.ID_DELIMITER + itemId);

		if (exiting.isPresent()) {
			return this.itemConverter.toBoundary(exiting.get());
		} else {
			throw new ObjNotFoundException("item with id: " + itemId + " could not be found");
		}
	}

	@Override
	@Transactional
	public void deleteAll(String adminSpace, String adminEmail) {
		this.itemsDao.deleteAll();

	}

	@Override
	@Transactional
	public void bind(String managerSpace, String managerEmail, String itemSpace, String itemId, ItemIdBoundary child) {
		Optional<ItemEntity> parent = this.itemsDao.findById(itemSpace + Application.ID_DELIMITER + itemId);
		if (parent.isPresent()) {
			Optional<ItemEntity> childToBind = this.itemsDao.findById(child.toString());
			if (childToBind.isPresent()) {
				ItemEntity parentEntity = parent.get();
				ItemEntity childEntity = childToBind.get();
				parentEntity.addChild(childEntity);
				childEntity.addParent(parentEntity);
				this.itemsDao.save(parentEntity);
				this.itemsDao.save(childEntity);
			} else {
				throw new ObjNotFoundException("child item with id: " + child.getId() + " could not be found");
			}
		} else {
			throw new ObjNotFoundException("parent item with id: " + itemId + " could not be found");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getChildren(String userSpace, String userEmail, String itemSpace, String itemId) {
		Optional<ItemEntity> parent = this.itemsDao.findById(itemSpace + Application.ID_DELIMITER + itemId);
		if (parent.isPresent()) {
			ItemEntity parentEntity = parent.get();
			Set<ItemEntity> cildren = parentEntity.getChildren();
			return StreamSupport.stream(cildren.spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());

		} else {
			throw new ObjNotFoundException("parent item with id: " + itemId + " could not be found");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId) {
		Optional<ItemEntity> child = this.itemsDao.findById(itemSpace + Application.ID_DELIMITER + itemId);
		if (child.isPresent()) {
			ItemEntity childEntity = child.get();
			Set<ItemEntity> parents = childEntity.getParents();
			return StreamSupport.stream(parents.spliterator(), false)
					.map(entity -> this.itemConverter.toBoundary(entity)).collect(Collectors.toList());

		} else {
			throw new ObjNotFoundException("child item with id: " + itemId + " could not be found");
		}
	}

}
