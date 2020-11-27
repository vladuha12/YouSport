package dts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import dts.boundaries.UserBoundary;
import dts.logic.UsersService;

@RestController
public class UserController {
	private UsersService userHandler;

	@Autowired
	public void setUserHandler(UsersService userHandler) {
		this.userHandler = userHandler;
	}

	// Create a new user
	@RequestMapping(path = "/dts/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary storeNewUser(@RequestBody UserBoundary newUser) {
		return userHandler.createUser(newUser);
	}

	// Login valid user and retrieve user details
	@RequestMapping(method = RequestMethod.GET, path = "/dts/users/login/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginUser(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) throws Exception {
		// stub implementation - in the future we will authenticate the user
		return userHandler.login(userSpace, userEmail);
	}

	// Update user details
	@RequestMapping(method = RequestMethod.PUT, path = "/dts/users/{userSpace}/{userEmail}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary updateExistingUser(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @RequestBody UserBoundary update) throws Exception {
		return userHandler.updateUser(userSpace, userEmail, update);
	}
}
