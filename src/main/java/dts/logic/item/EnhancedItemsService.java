package dts.logic.item;

import java.util.List;

import dts.boundaries.ItemBoundary;
import dts.boundaries.ItemIdBoundary;

public interface EnhancedItemsService extends ItemsService {

	public void bind(String managerSpace, String managerEmail, String itemSpace, String itemId, ItemIdBoundary child);

	public List<ItemBoundary> getChildren(String userSpace, String userEmail, String itemSpace, String itemId);

	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId);

}
