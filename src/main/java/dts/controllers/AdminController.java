package dts.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import dts.boundaries.OperationBoundary;
import dts.boundaries.UserBoundary;
import dts.logic.ItemsService;
import dts.logic.UsersService;

@RestController
public class AdminController {
	private UsersService userHandler;
	private ItemsService itemsHandler;
	
	// Initialize UserService handler
	@Autowired
	public void setUserHandler(UsersService userHandler) {
		this.userHandler = userHandler;
	}
	
	@Autowired
	public void setItemsService(ItemsService itemsHandler) {
		this.itemsHandler = itemsHandler;
	}

	// Delete All Users API
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/users/{adminSpace}/{adminEmail}")
	public void deleteAllUsers(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		//System.err.println("deleted All Users By: " + adminEmail);
		userHandler.deleteAllUsers(adminSpace, adminEmail);
	}

	// Delete All Items API
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/items/{adminSpace}/{adminEmail}")
	public void deleteAllItems(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		itemsHandler.deleteAll(adminSpace, adminEmail);
	}

	// Delete All Operations API
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/operations/{adminSpace}/{adminEmail}")
	public void deleteAllOperations(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		System.err.println("deleted All Operations By: " + adminEmail);
	}

	// Export All Users API (Example Create 7 users and return)
	@RequestMapping(method = RequestMethod.GET, path = "/dts/admin/users/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		
		return userHandler.getAllUsers(adminSpace, adminEmail).toArray(new UserBoundary[0]);
	}
	
	// Export All Operations API (Example Create 7 operations and return)
	@RequestMapping(method = RequestMethod.GET, path = "/dts/admin/operations/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<OperationBoundary> exportAllOperations(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {

		ArrayList<OperationBoundary> OperationResault = new ArrayList<OperationBoundary>();
		for (int i = 0; i < 7; i++) {
			OperationResault.add(new OperationBoundary());

		}
		return OperationResault;
	}
}
