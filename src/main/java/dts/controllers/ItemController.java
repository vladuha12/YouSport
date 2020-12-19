package dts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dts.boundaries.ItemBoundary;
import dts.boundaries.ItemIdBoundary;
import dts.logic.item.EnhancedItemsService;

@RestController
public class ItemController {
	private EnhancedItemsService itemsHandler;

	@Autowired
	public void setItemHandler(EnhancedItemsService itemsHandler) {
		this.itemsHandler = itemsHandler;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary item(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace, @PathVariable("itemId") String itemId) throws Exception {
		return itemsHandler.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] items(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) {
		return itemsHandler.getAll(userSpace, userEmail).toArray(new ItemBoundary[0]);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] children(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {
		return itemsHandler.getChildren(userSpace, userEmail, itemSpace, itemId).toArray(new ItemBoundary[0]);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/parents", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] parents(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId) {
		return itemsHandler.getParents(userSpace, userEmail, itemSpace, itemId).toArray(new ItemBoundary[0]);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary updateExistingItem(@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId, @RequestBody ItemBoundary updatedItem) throws Exception {
		return itemsHandler.update(managerSpace, managerEmail, itemSpace, itemId, updatedItem);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}/children", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void bindChild(@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId, @RequestBody ItemIdBoundary childItem) throws Exception {
		itemsHandler.bind(managerSpace, managerEmail, itemSpace, itemId, childItem);
	}

	@RequestMapping(path = "/dts/items/{managerSpace}/{managerEmail}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary storeItem(@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, @RequestBody ItemBoundary newItem) throws Exception {
		return itemsHandler.create(managerSpace, managerEmail, newItem);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/items/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void clear(@PathVariable("adminSpace") String adminSpace, @PathVariable("adminEmail") String adminEmail) {
		itemsHandler.deleteAll(adminSpace, adminEmail);
	}
}
