package dts.logic.user;

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

import dts.boundaries.NewUserBoundary;
import dts.boundaries.UserBoundary;
import dts.boundaries.UserIdBoundary;
import dts.data.UserEntity;
import dts.data.UserRole;

@Service
public class UserServiceImplementation implements UsersService, CommandLineRunner {

	private String helperName;
	private Map<String, UserEntity> usersStore;
	private UserConverter userConverter;
	private String delimiter = "$";

	@Value("${spring.application.name:demoSpace}")
	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}

	@PostConstruct
	public void init() {
		this.usersStore = Collections.synchronizedMap(new HashMap<>()); // thread safe map
	}

	@Autowired
	public void setUserConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println(this.helperName);
	}

	@Override
	public UserBoundary createUser(NewUserBoundary newUser) {
		UserIdBoundary userId = new UserIdBoundary(this.helperName,newUser.getEmail());
		UserBoundary user = new UserBoundary(userId,newUser.getRole(),newUser.getUsername(),newUser.getAvatar());
		UserEntity userEntity = this.userConverter.toEntity(user);
	
		// MOCKUP database store of the entity
		this.usersStore.put(userEntity.getUserId(), userEntity);
		return this.userConverter.toBoundary(userEntity);
	}

	@Override
	public UserBoundary login(String userSpace, String userEmail) throws Exception {
		String key = userSpace + delimiter + userEmail;
		if (usersStore.containsKey(key))
			return this.userConverter.toBoundary(usersStore.get(key));
		else
			throw new RuntimeException();
	}

	@Override
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) throws Exception {
		String key = userSpace + delimiter + userEmail;
		if (usersStore.containsKey(key) && update.getUserId().getEmail() == userEmail && update.getUserId().getSpace() == userSpace) {
			usersStore.put(key, this.userConverter.toEntity(update));
			return this.userConverter.toBoundary(usersStore.get(key));
		} else
			throw new RuntimeException();
	}

	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		if (validateAdmin(adminSpace, adminEmail))
			return this.usersStore.values().stream().map(entity -> userConverter.toBoundary(entity))
					.collect(Collectors.toList());
		else
			return null;
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		if (validateAdmin(adminSpace, adminEmail))
			usersStore.clear();
	}

	public boolean validateAdmin(String adminSpace, String adminEmail) {
		String key = adminSpace + delimiter + adminEmail;
		UserEntity admin = usersStore.get(key);
		if (admin != null && admin.getRole() == UserRole.ADMIN)
			return true;
		else
			return false;
	}
}
