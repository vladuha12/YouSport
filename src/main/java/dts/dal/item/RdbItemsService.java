package dts.dal.item;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dts.boundaries.IdBoundary;
import dts.boundaries.ItemBoundary;
import dts.data.ItemEntity;
import dts.logic.item.EnhancedItemsService;
import dts.logic.item.ItemConverter;
import dts.util.ObjectNotFoundException;

@Service
public class RdbItemsService implements EnhancedItemsService {
	private ItemsDao itemsDao;
	private ItemConverter itemConverter;

	@Autowired
	public RdbItemsService(ItemsDao itemsDao, ItemConverter itemConverter) {
		super();
		this.itemsDao = itemsDao;
		this.itemConverter = itemConverter;
	}

	@Override
	@Transactional
	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem) throws Exception {
		try {
			ItemEntity entity = this.itemConverter.toEntity(newItem);
			IdBoundary id = new IdBoundary();
			entity.setItemId(id.toString());
			entity.setCreatedTimestamp(new Date());

			return this.itemConverter.toBoundary(this.itemsDao.save(entity));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional
	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,
			ItemBoundary update) throws Exception {
		Optional<ItemEntity> exiting = this.itemsDao.findById(itemId);

		if (exiting.isPresent()) {
			ItemEntity existingEntity = exiting.get();
			ItemEntity updateEntity = this.itemConverter.toEntity(update);
			updateEntity.setItemId(existingEntity.getItemId());
			updateEntity.setCreatedTimestamp(existingEntity.getCreatedTimestamp());
			updateEntity.setCreatedBy(existingEntity.getCreatedBy());

			return this.itemConverter.toBoundary(this.itemsDao.save(updateEntity));
		} else {
			throw new ObjectNotFoundException("item with id: " + itemId + " could not be found");
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
		Optional<ItemEntity> exiting = this.itemsDao.findById(itemId);

		if (exiting.isPresent()) {
			return this.itemConverter.toBoundary(exiting.get());
		} else {
			throw new ObjectNotFoundException("item with id: " + itemId + " could not be found");
		}
	}

	@Override
	@Transactional
	public void deleteAll(String adminSpace, String adminEmail) {
		this.itemsDao.deleteAll();

	}
}
