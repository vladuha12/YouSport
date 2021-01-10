package dts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary item(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace, @PathVariable("itemId") String itemId) throws Exception {
		return itemsHandler.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] items(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return itemsHandler.getAll(userSpace, userEmail, size, page).toArray(new ItemBoundary[0]);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] children(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return itemsHandler.getChildren(userSpace, userEmail, itemSpace, itemId, size, page)
				.toArray(new ItemBoundary[0]);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/parents", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] parents(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return itemsHandler.getParents(userSpace, userEmail, itemSpace, itemId, size, page)
				.toArray(new ItemBoundary[0]);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/search/byNamePattern/{namePattern}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] searchByNamePattern(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @PathVariable("namePattern") String namePattern,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return itemsHandler.getAllItemsByNamePattern(userSpace, userEmail, namePattern, size, page)
				.toArray(new ItemBoundary[0]);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/search/byType/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] searchByType(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @PathVariable("type") String type,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return itemsHandler.getAllItemsByType(userSpace, userEmail, type, size, page).toArray(new ItemBoundary[0]);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/search/near/{lat}/{lng}/{distance}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] searchByNamePattern(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @PathVariable("lat") float lat, @PathVariable("lng") float lng,
			@PathVariable("distance") float distance,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return itemsHandler.getAllItemsWithinRange(userSpace, userEmail, lat, lng, distance, size, page)
				.toArray(new ItemBoundary[0]);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary updateExistingItem(@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId, @RequestBody ItemBoundary updatedItem) throws Exception {
		return itemsHandler.update(managerSpace, managerEmail, itemSpace, itemId, updatedItem);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}/children", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void bindChild(@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId, @RequestBody ItemIdBoundary childItem) throws Exception {
		itemsHandler.bind(managerSpace, managerEmail, itemSpace, itemId, childItem, false);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(path = "/dts/items/{managerSpace}/{managerEmail}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary storeItem(@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, @RequestBody ItemBoundary newItem) throws Exception {
		return itemsHandler.create(managerSpace, managerEmail, newItem);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/items/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void clear(@PathVariable("adminSpace") String adminSpace, @PathVariable("adminEmail") String adminEmail) {
		itemsHandler.deleteAll(adminSpace, adminEmail);
	}
}
