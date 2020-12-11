package dts.dal.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import dts.boundaries.NewUserBoundary;
import dts.boundaries.UserBoundary;
import dts.boundaries.UserIdBoundary;
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.logic.user.EnhancedUsersService;
import dts.logic.user.UserConverter;

public class RdbUsersService implements EnhancedUsersService {
	private UsersDao usersDao;
	private UserConverter userConverter;
	private String helperName;

	@Autowired
	public RdbUsersService(UsersDao usersDao, UserConverter userConverter) {
		super();
		this.usersDao = usersDao;
		this.userConverter = userConverter;
	}

	@Value("${spring.application.name:demoSpace}")
	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}

	@Override
	@Transactional
	public UserBoundary createUser(NewUserBoundary newUser) throws Exception {
		try {
			UserIdBoundary userId = new UserIdBoundary(this.helperName, newUser.getEmail());
			UserBoundary user = new UserBoundary(userId, newUser.getRole(), newUser.getUsername(), newUser.getAvatar());
			UserEntity userEntity = this.userConverter.toEntity(user);
			return this.userConverter.toBoundary(this.usersDao.save(userEntity));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional
	public UserBoundary login(String userSpace, String userEmail) throws Exception {
		UserIdBoundary userIdBoundary = new UserIdBoundary(userSpace, userEmail);
		String id = userIdBoundary.toString();
		Optional<UserEntity> exiting = this.usersDao.findById(id);
		if (exiting.isPresent())
			return this.userConverter.toBoundary(exiting.get());
		else
			throw new RuntimeException();
	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) throws Exception {
		UserIdBoundary userIdBoundary = new UserIdBoundary(userSpace, userEmail);
		String id = userIdBoundary.toString();
		Optional<UserEntity> exiting = this.usersDao.findById(id);
		if (exiting.isPresent()) {
			UserEntity existingEntity = exiting.get();
			UserEntity updateEntity = this.userConverter.toEntity(update);
			updateEntity.setUserId(existingEntity.getUserId());
			return this.userConverter.toBoundary(this.usersDao.save(updateEntity));
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		if (validateAdmin(adminSpace, adminEmail))
			return StreamSupport.stream(this.usersDao.findAll().spliterator(), false)
				.map(entity -> this.userConverter.toBoundary(entity)).collect(Collectors.toList());
		else
			return null;
	}

	@Override
	@Transactional(readOnly = true)
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		if (validateAdmin(adminSpace, adminEmail))
			this.usersDao.deleteAll();

	}

	public boolean validateAdmin(String adminSpace, String adminEmail) {
		
		UserIdBoundary userIdBoundary = new UserIdBoundary(adminSpace, adminEmail);
		String id = userIdBoundary.toString();
		Optional<UserEntity> exiting = this.usersDao.findById(id);
		if (exiting.isPresent() && exiting.get().getRole() == UserRole.ADMIN) {
			return true;
		}
		else
			return false;
	}
}
