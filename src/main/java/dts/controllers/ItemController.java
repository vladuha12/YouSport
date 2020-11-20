package dts.controllers;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dts.boundaries.IdBoundary;
import dts.boundaries.ItemBoundary;

@RestController
public class ItemController {

	@RequestMapping(method = RequestMethod.GET,
			// path = "items/{userSpace}/{userEmail}/{itemSpace}/{itemId}",
			path = "/dts/items/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary item() {
		return new ItemBoundary();

	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary item(@PathVariable("userSpace") String userSpace, @PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace, @PathVariable("itemId") String itemId) {
		return new ItemBoundary(itemId);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/dts/items/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<ItemBoundary> items(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) {

		ArrayList<ItemBoundary> itemsResault = new ArrayList<ItemBoundary>();

		for (int i = 0; i < 7; i++) {
			itemsResault.add(new ItemBoundary(String.valueOf(i)));
		}
		return itemsResault;

	}

	@RequestMapping(method = RequestMethod.PUT, path = "/dts/items/{magagerSpace}/{managerEmail}/{itemSpace}/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateExistingMessage(@PathVariable("magagerSpace") String magagerSpace,
			@PathVariable("managerEmail") String managerEmail, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId, @RequestBody ItemBoundary updatedItem) {
		System.err.println("updating item with itemId: " + itemId + " using update: " + updatedItem);
	}

	@RequestMapping(path = "/dts/items/{magagerSpace}/{managerEmail}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary storeMessage(@RequestBody ItemBoundary newItem) {
		newItem.setItemId(new IdBoundary());
		return newItem;
	}
}
