package dts.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dts.boundaries.ItemBoundary;
import dts.logic.ItemsService;

@RestController
public class ItemController {
	private ItemsService itemsHandler;
		
	@Autowired
	public void setItemHandler(ItemsService itemsHandler) {
		this.itemsHandler = itemsHandler;
	}	
	
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary item(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace, @PathVariable("itemId") String itemId) {
		return itemsHandler.getSpecificItem(userSpace, userEmail, itemSpace, itemId);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<ItemBoundary> items(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) {
		return (ArrayList<ItemBoundary>) itemsHandler.getAll(userSpace, userEmail);
	}	
		
	@RequestMapping(method = RequestMethod.PUT, path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary updateExistingItem(@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId, @RequestBody ItemBoundary updatedItem) {	
		return itemsHandler.update(managerSpace, managerEmail, itemSpace, itemId, updatedItem);
	}

	@RequestMapping(path = "/dts/items/{managerSpace}/{managerEmail}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary storeItem(@PathVariable("managerSpace") String managerSpace,
			@PathVariable("managerEmail") String managerEmail, @RequestBody ItemBoundary newItem) {
		return itemsHandler.Create(managerSpace, managerEmail, newItem);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/items/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void clear(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		itemsHandler.deleteAll(adminSpace, adminEmail);
	}
}
