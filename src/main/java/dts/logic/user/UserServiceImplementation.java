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
import dts.boundaries.UserBoundary;
import dts.data.UserEntity;
import dts.data.UserRole;

//@Service
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
	public UserBoundary createUser(UserBoundary newUser) {
		UserEntity userEntity = this.userConverter.toEntity(newUser);

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
		UserEntity old = usersStore.get(key);

		if (old != null) {
			UserEntity entity = this.userConverter.toEntity(update);
			entity.setUserId(old.getUserId());
			usersStore.put(key, entity);
			return this.userConverter.toBoundary(entity);
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
