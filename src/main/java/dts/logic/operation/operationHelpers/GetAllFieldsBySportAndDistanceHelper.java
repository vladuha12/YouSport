package dts.logic.operation.operationHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dts.boundaries.ItemBoundary;
import dts.boundaries.OperationBoundary;
import dts.data.ItemEntity;
import dts.logic.item.ItemConverter;
import dts.logic.item.ItemsDao;

@Component("getAllFieldsBySportAndDistance")
public class GetAllFieldsBySportAndDistanceHelper implements OperationHelper {

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
	@Transactional(readOnly = true)
	public Object invokeOperation(OperationBoundary operation) {
		List<ItemBoundary> rv = new ArrayList<>();
		Map<String, Object> operationAttributes = operation.getOperationAttributes();
		String sportName = (String) operationAttributes.get("sport");
		float lat = Float.parseFloat((String) operationAttributes.get("lat"));
		float lng = Float.parseFloat((String) operationAttributes.get("lng"));
		float distance = Float.parseFloat((String) operationAttributes.get("distance"));

		List<ItemBoundary> fields = this.itemsDao
				.findAllByActiveAndTypeAndLatBetweenAndLngBetween(true, "sport_field", lat - distance, lat + distance,
						lng - distance, lng + distance,
						PageRequest.of(0, 100, Direction.DESC, "createdTimestamp", "itemId"))
				.stream().map(this.itemConverter::toBoundary).collect(Collectors.toList());

		if (sportName.toLowerCase().equals("any")) {
			rv = fields;
		} else {
			for (ItemBoundary field : fields) {
				List<ItemEntity> sports = this.itemsDao.findAllByActiveAndTypeAndNameAndParents_itemId(true,
						"sport_item", sportName, field.getItemId().toString(),
						PageRequest.of(0, 1, Direction.DESC, "createdTimestamp", "itemId"));

				if (!sports.isEmpty()) {
					rv.add(field);
				}
			}
		}

		return rv;
	}
}