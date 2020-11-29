package dts.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import dts.boundaries.UserBoundary;
import dts.data.UserEntity;
import dts.data.UserIdEntity;
import dts.data.UserRole;

@Service
public class UserServiceImplementation implements UsersService, CommandLineRunner {

	private String helperName;
	private Map<String, UserEntity> usersStore;
	private UserConverter userConverter;
	private UserIdConverter userIdConverter;
	private String delimiter = "$";

	@Value("${spring.application.name:demoSpace}")
	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}

	@PostConstruct
	public void init() {
		this.usersStore = Collections.synchronizedMap(new HashMap<>()); // thread safe map
	}

	// Initialize UserConverter
	@Autowired
	public void setUserConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}
	
	// Initialize UserIdConverter
	@Autowired
	public void setUserIdConverter(UserIdConverter userIdConverter) {
		this.userIdConverter = userIdConverter;
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println(this.helperName);
	}

	// Create user service API
	@Override
	public UserBoundary createUser(UserBoundary newUser) {
		newUser.setSpace(helperName);
		UserEntity userEntity = this.userConverter.toEntity(newUser);

		// MOCKUP database store of the entity
		String key = helperName + delimiter + userEntity.getUserId().getEmail();
		this.usersStore.put(key, userEntity);
		return this.userConverter.toBoundary(userEntity);
	}

	// Login user service API
	@Override
	public UserBoundary login(String userSpace, String userEmail) throws Exception {
		String key = userSpace + delimiter + userEmail;
		if (usersStore.containsKey(key))
			return this.userConverter.toBoundary(usersStore.get(key));
		else
			throw new RuntimeException();
	}

	// Update user service API
	@Override
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) throws Exception {
		String key = userSpace + delimiter + userEmail;
		if (usersStore.containsKey(key)) {
			UserEntity old = usersStore.get(key);
			UserIdEntity idOld = old.getUserId();
			update.setUserId(this.userIdConverter.toBoundary(idOld));
			usersStore.put(key, this.userConverter.toEntity(update));
			return this.userConverter.toBoundary(usersStore.get(key));
		} else
			throw new RuntimeException();
	}
	
	// Get all users service API
	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		if (validateAdmin(adminSpace, adminEmail))
			return this.usersStore.values().stream().map(entity -> userConverter.toBoundary(entity))
					.collect(Collectors.toList());
		else
			return null;
	}

	// Delete all users service API
	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		if (validateAdmin(adminSpace, adminEmail))
			usersStore.clear();
	}

	// Admin validation function
	public boolean validateAdmin(String adminSpace, String adminEmail) {
		String key = adminSpace + delimiter + adminEmail;
		UserEntity admin = usersStore.get(key);
		if (admin != null && admin.getRole() == UserRole.ADMIN)
			return true;
		else
			return false;
	}
}
