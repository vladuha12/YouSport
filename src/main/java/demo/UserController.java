package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class UserController {
/////////////////////////////////  User related API  ///////////////////////////////////////////////////

	/*
	 * Create a new user
	 */
	@RequestMapping(path = "/dts/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UsersBoundary storeNewUser(@RequestBody UsersBoundary newUser) {
// stub implementation - in the future we will create a new user and store in the DB
		return new UsersBoundary();
	}

	/*
	 * Login valid user and retrieve user details
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/dts/users/login/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UsersBoundary loginUser(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) {
// stub implementation - in the future we will authenticate the user
		return new UsersBoundary();
	}

	/*
	 * Update user details
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/dts/users/{userSpace}/{userEmail}")
	public void updateExistingUser(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) {
// stub implementation - in the future we will update user details
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////
}
