package dts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dts.boundaries.OperationBoundary;
import dts.boundaries.UserBoundary;
import dts.logic.item.EnhancedItemsService;
import dts.logic.operation.EnhancedOperationsService;
import dts.logic.user.EnhancedUsersService;

@RestController
public class AdminController {
	private EnhancedUsersService userHandler;
	private EnhancedItemsService itemsHandler;
	private EnhancedOperationsService operationsHandler;

	// Initialize UserService handler
	@Autowired
	public void setUserHandler(EnhancedUsersService userHandler) {
		this.userHandler = userHandler;
	}

	@Autowired
	public void setItemsService(EnhancedItemsService itemsHandler) {
		this.itemsHandler = itemsHandler;
	}

	@Autowired
	public void setOperationsService(EnhancedOperationsService operationsHandler) {
		this.operationsHandler = operationsHandler;
	}

	// Delete All Users API
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/users/{adminSpace}/{adminEmail}")
	public void deleteAllUsers(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		// System.err.println("deleted All Users By: " + adminEmail);
		userHandler.deleteAllUsers(adminSpace, adminEmail);
	}

	// Delete All Items API
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/items/{adminSpace}/{adminEmail}")
	public void deleteAllItems(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		itemsHandler.deleteAll(adminSpace, adminEmail);
	}

	// Delete All Operations API
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/operations/{adminSpace}/{adminEmail}")
	public void deleteAllOperations(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		System.err.println("deleted All Operations By: " + adminEmail);
	}

	// Export All Users API
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/admin/users/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(
			@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail,
			@RequestParam(name = "size", required = false, defaultValue = "15") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page){
		System.err.println("new");
		return userHandler.getAllUsers(adminSpace, adminEmail,size,page).toArray(new UserBoundary[0]);
	}

	// Export All Operations API
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/admin/operations/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationBoundary[] exportAllOperations(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) throws Exception {

		return operationsHandler.getAllOperations(adminSpace, adminEmail).toArray(new OperationBoundary[0]);
	}
}
