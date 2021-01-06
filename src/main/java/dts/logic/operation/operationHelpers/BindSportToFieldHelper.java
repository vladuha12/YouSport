package dts.logic.operation.operationHelpers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dts.boundaries.ItemIdBoundary;
import dts.boundaries.OperationBoundary;
import dts.logic.item.EnhancedItemsService;

@Component("bindSportToField")
public class BindSportToFieldHelper implements OperationHelper {

	private EnhancedItemsService itemsHandler;

	@Autowired
	public void setItemHandler(EnhancedItemsService itemsHandler) {
		this.itemsHandler = itemsHandler;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operation) {
		Map<String, Object> operationAttributes = operation.getOperationAttributes();
		String sportSpace = (String) operationAttributes.get("sportSpace");
		String sportId = (String) operationAttributes.get("sportId");
		ItemIdBoundary childItemId = new ItemIdBoundary(sportSpace, sportId);

		itemsHandler.bind(operation.getInvokedBy().getUserId().getSpace(),
				operation.getInvokedBy().getUserId().getEmail(), operation.getItem().getItemId().getSpace(),
				operation.getItem().getItemId().getId(), childItemId);

		return null;
	}
}
