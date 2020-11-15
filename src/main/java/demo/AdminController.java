package demo;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	// Delete All Users API
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/users/{adminSpace}/{adminEmail}")
	public void deleteAllUsers(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		System.err.println("deleted All Users By: " + adminEmail);
	}

	// Delete All Items API
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/items/{adminSpace}/{adminEmail}")
	public void deleteAllItems(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		System.err.println("deleted All Items By: " + adminEmail);
	}

	// Delete All Operations API
	@RequestMapping(method = RequestMethod.DELETE, path = "/dts/admin/operations/{adminSpace}/{adminEmail}")
	public void deleteAllOperations(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		System.err.println("deleted All Operations By: " + adminEmail);
	}

	// Export All Users API (Example Create 7 users and return)
	@RequestMapping(method = RequestMethod.GET, path = "/dts/admin/users/{adminSpace}/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<UserBoundary> exportAllUsers(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {

		ArrayList<UserBoundary> UsersResault = new ArrayList<UserBoundary>();
		for (int i = 0; i < 7; i++) {
			UsersResault.add(new UserBoundary());

		}
		return UsersResault;
	}
	/*
	 * // Export All Operations API (Example Create 7 users and return)
	 * 
	 * @RequestMapping(method = RequestMethod.GET, path =
	 * "/admin/operations/{adminSpace}/{adminEmail}", produces =
	 * MediaType.APPLICATION_JSON_VALUE) public ArrayList<OperationBoundary>
	 * exportAllOperation(@PathVariable("adminSpace") String adminSpace,
	 * 
	 * @PathVariable("adminEmail") String adminEmail){
	 * 
	 * ArrayList<OperationBoundary> OperationResault = new
	 * ArrayList<OperationBoundary>(); for (int i = 0; i < 7; i++) {
	 * OperationResault.add(new OperationBoundary(String.valueOf(i)));
	 * 
	 * return OperationResault; } return OperationResault; }
	 */
}
