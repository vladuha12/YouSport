package dts.logic.item;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
import dts.logic.user.UsersDao;
import dts.util.BadInputException;
import dts.util.ObjNotFoundException;

@Service
public class RdbItemsService implements EnhancedItemsService {
	private Log log = LogFactory.getLog(RdbItemsService.class);
	private ItemsDao itemsDao;
	private ItemConverter itemConverter;
	private IdGeneratorEntityDao IdGeneratorEntityDao;
	private UsersDao usersDao;

	@Autowired
	public RdbItemsService(ItemsDao itemsDao, ItemConverter itemConverter, IdGeneratorEntityDao IdGeneratorEntityDao,
			UsersDao usersDao) {
		super();
		this.itemsDao = itemsDao;
		this.itemConverter = itemConverter;
		this.IdGeneratorEntityDao = IdGeneratorEntityDao;
		this.usersDao = usersDao;
	}

	@Override
	@Transactional
	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem) throws Exception {
		try {
			UserIdBoundary createdBy = new UserIdBoundary(managerSpace, managerEmail);
			// if (!RoleValidator.canUserPerformOperation(createdBy, UserRole.MANAGER,
			// usersDao)) {
			// throw new ForbiddenException("create item - Can only be performed by a
			// MANAGER");
			// }

			ItemEntity entity = this.itemConverter.toEntity(newItem);
			if (entity.getName() == null || entity.getName().trim().isEmpty()) {
				throw new RuntimeException("item name can not be empty");
			}

			if (entity.getType() == null || entity.getType().trim().isEmpty()) {
				throw new RuntimeException("item type can not be empty");
			}

			IdGeneratorEntity idGeneratorEntity = new IdGeneratorEntity();
			idGeneratorEntity = this.IdGeneratorEntityDao.save(idGeneratorEntity);
			Long newId = idGeneratorEntity.getId();
			this.IdGeneratorEntityDao.deleteById(newId);

			IdBoundary id = new IdBoundary(newId.toString());
			entity.setItemId(id.toString());
			entity.setCreatedTimestamp(new Date());

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

		// if (!RoleValidator.canUserPerformOperation(new UserIdBoundary(managerSpace,
		// managerEmail), UserRole.MANAGER,
		// usersDao)) {
		// throw new ForbiddenException("update item - Can only be performed by a
		// MANAGER");
		// }

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
	// @Transactional(readOnly = true)
	public List<ItemBoundary> getAll(String userSpace, String userEmail) {
		throw new RuntimeException("deprecated");
		/*
		 * return StreamSupport.stream(this.itemsDao.findAll().spliterator(), false)
		 * .map(entity ->
		 * this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		 */
	}

	@Override
	@Transactional(readOnly = true)
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId)
			throws Exception {

		Optional<ItemEntity> exiting;

		// if (RoleValidator.canUserPerformOperation(new UserIdBoundary(userSpace,
		// userEmail), UserRole.PLAYER,
		// usersDao)) {
		// exiting = Optional.ofNullable(
		// this.itemsDao.findByActiveAndItemId(true, itemSpace +
		// Application.ID_DELIMITER + itemId));
		// } else {
		exiting = this.itemsDao.findById(itemSpace + Application.ID_DELIMITER + itemId);
		// }

		if (exiting.isPresent()) {
			return this.itemConverter.toBoundary(exiting.get());
		} else {
			throw new ObjNotFoundException("item with id: " + itemId + " could not be found");
		}
	}

	@Override
	@Transactional
	public void deleteAll(String adminSpace, String adminEmail) {
		// if (!RoleValidator.canUserPerformOperation(new UserIdBoundary(adminSpace,
		// adminEmail), UserRole.ADMIN,
		// usersDao)) {
		// throw new ForbiddenException("delete items - Can only be performed by a
		// ADMIN");
		// }
		this.itemsDao.deleteAll();
	}

	// TODO: add validation!!
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
	// @Transactional(readOnly = true)
	public List<ItemBoundary> getChildren(String userSpace, String userEmail, String itemSpace, String itemId) {
		throw new RuntimeException("deprecated");
		/*
		 * Optional<ItemEntity> parent = this.itemsDao.findById(itemSpace +
		 * Application.ID_DELIMITER + itemId); if (parent.isPresent()) { ItemEntity
		 * parentEntity = parent.get(); Set<ItemEntity> cildren =
		 * parentEntity.getChildren(); return
		 * StreamSupport.stream(cildren.spliterator(), false) .map(entity ->
		 * this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		 * 
		 * } else { throw new ObjNotFoundException("parent item with id: " + itemId +
		 * " could not be found"); }
		 */
	}

	@Override
	// @Transactional(readOnly = true)
	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId) {
		throw new RuntimeException("deprecated");
		/*
		 * Optional<ItemEntity> child = this.itemsDao.findById(itemSpace +
		 * Application.ID_DELIMITER + itemId); if (child.isPresent()) { ItemEntity
		 * childEntity = child.get(); Set<ItemEntity> parents =
		 * childEntity.getParents(); return StreamSupport.stream(parents.spliterator(),
		 * false) .map(entity ->
		 * this.itemConverter.toBoundary(entity)).collect(Collectors.toList());
		 * 
		 * } else { throw new ObjNotFoundException("child item with id: " + itemId +
		 * " could not be found"); }
		 */
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAll(String userSpace, String userEmail, int size, int page) {
		return this.itemsDao.findAll(PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId"))
				.getContent().stream().map(this.itemConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getChildren(String userSpace, String userEmail, String itemSpace, String itemId, int size,
			int page) {

		List<ItemEntity> children = this.itemsDao.findAllByParents_itemId(
				(itemSpace + Application.ID_DELIMITER + itemId),
				PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId"));

		return children.stream().map(this.itemConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId, int size,
			int page) {
		List<ItemEntity> parents = this.itemsDao.findAllByChildren_itemId(
				(itemSpace + Application.ID_DELIMITER + itemId),
				PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId"));

		return parents.stream().map(this.itemConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllItemsByNamePattern(String userSpace, String userEmail, String namePattern, int size,
			int page) {

		if (namePattern == null || namePattern.isEmpty()) {
			this.log.error("**** Error - input pattern must contain at least a single character");
			throw new BadInputException("input pattern must contain at least a single character");
		}

		try {
			return this.itemsDao
					.findAllByNameLike("%" + namePattern + "%",
							PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId"))
					.stream().map(this.itemConverter::toBoundary).collect(Collectors.toList());
		} finally {
			this.log.debug("**** done finding items with names containing \'" + namePattern + "\'");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllItemsByType(String userSpace, String userEmail, String type, int size, int page) {
		if (type == null || type.isEmpty()) {
			this.log.error("**** Error - input type must contain at least a single character");
			throw new BadInputException("input type must contain at least a single character");
		}

		try {
			return this.itemsDao
					.findAllByType(type, PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId"))
					.stream().map(this.itemConverter::toBoundary).collect(Collectors.toList());
		} finally {
			this.log.debug("**** done finding items with type \'" + type + "\'");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAllItemsWithinRange(String userSpace, String userEmail, float lat, float lng,
			float distance, int size, int page) {
		return this.itemsDao
				.findAllByLatBetweenAndLngBetween(lat - distance, lat + distance, lng - distance, lng + distance,
						PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "itemId"))
				.stream().map(this.itemConverter::toBoundary).collect(Collectors.toList());
	}

}
