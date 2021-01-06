package dts.logic.operation.operationHelpers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dts.boundaries.ItemBoundary;
import dts.boundaries.OperationBoundary;
import dts.data.ItemEntity;
import dts.logic.item.ItemConverter;
import dts.logic.item.ItemsDao;

@Component("updateFieldData")
public class UpdateFieldDataHelper implements OperationHelper {

	private ItemsDao itemsDao;
	private ItemConverter itemConverter;

	@Autowired
	public void setItemsDao(ItemsDao itemsDao) {
		this.itemsDao = itemsDao;
	}

	@Autowired
	public void setItemConverter(ItemConverter itemConverter) {
		this.itemConverter = itemConverter;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operation) {
		ItemEntity targetItem = itemsDao.findByActiveAndItemId(true, operation.getItem().toString());
		ItemBoundary itemBoundary = this.itemConverter.toBoundary(targetItem);
		Map<String, Object> itemAttributes = itemBoundary.getItemAttributes();
		Map<String, Object> newAttributes = operation.getOperationAttributes();

		itemAttributes.putAll(newAttributes);
		itemBoundary.setItemAttributes(itemAttributes);
		ItemEntity updatedItem = this.itemConverter.toEntity(itemBoundary);

		updatedItem.setCreatedBy(targetItem.getCreatedBy());
		updatedItem.setChildren(targetItem.getChildren());

		return this.itemConverter.toBoundary(this.itemsDao.save(updatedItem));
	}
}