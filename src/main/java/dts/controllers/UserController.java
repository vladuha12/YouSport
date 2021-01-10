package dts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dts.boundaries.NewUserBoundary;
import dts.boundaries.UserBoundary;
import dts.boundaries.UserIdBoundary;
import dts.logic.user.EnhancedUsersService;

@RestController
public class UserController {
	private EnhancedUsersService userHandler;

	@Autowired
	public void setUserHandler(EnhancedUsersService userHandler) {
		this.userHandler = userHandler;
	}

	// Create a new user
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(path = "/dts/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary storeNewUser(@RequestBody NewUserBoundary newUser) throws Exception {
		return userHandler.createUser(new UserBoundary(new UserIdBoundary(null,newUser.getEmail()),newUser.getRole(),newUser.getUsername(), newUser.getAvatar()));
	}

	// Login valid user and retrieve user details
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, path = "/dts/users/login/{userSpace}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary loginUser(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail) throws Exception {
		return userHandler.login(userSpace, userEmail);
	}

	// Update user details
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, path = "/dts/users/{userSpace}/{userEmail}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary updateExistingUser(@PathVariable("userSpace") String userSpace,
			@PathVariable("userEmail") String userEmail, @RequestBody UserBoundary update) throws Exception {
		return userHandler.updateUser(userSpace, userEmail, update);
	}
}
