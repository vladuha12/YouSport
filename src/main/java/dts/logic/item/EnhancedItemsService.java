package dts.logic.item;

import java.util.List;

import dts.boundaries.ItemBoundary;
import dts.boundaries.ItemIdBoundary;

public interface EnhancedItemsService extends ItemsService {

	public void bind(String managerSpace, String managerEmail, String itemSpace, String itemId, ItemIdBoundary child);

	public List<ItemBoundary> getAll(String userSpace, String userEmail, int size, int page);

	public List<ItemBoundary> getChildren(String userSpace, String userEmail, String itemSpace, String itemId);

	public List<ItemBoundary> getChildren(String userSpace, String userEmail, String itemSpace, String itemId, int size,
			int page);

	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId);

	public List<ItemBoundary> getParents(String userSpace, String userEmail, String itemSpace, String itemId, int size,
			int page);

	public List<ItemBoundary> getAllItemsByNamePattern(String userSpace, String userEmail, String namePattern, int size,
			int page);

	public List<ItemBoundary> getAllItemsByType(String userSpace, String userEmail, String type, int size, int page);

	public List<ItemBoundary> getAllItemsWithinRange(String userSpace, String userEmail, float lat, float lng,
			float distance, int size, int page);

}
