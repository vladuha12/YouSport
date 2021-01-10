package dts.logic.item;

import java.util.List;

import dts.boundaries.ItemBoundary;

public interface ItemsService {

	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem) throws Exception;

	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,
			ItemBoundary update) throws Exception;

	public List<ItemBoundary> getAll(String userSpace, String userEmail);

	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId)
			throws Exception;

	public void deleteAll(String adminSpace, String adminEmail);

}
