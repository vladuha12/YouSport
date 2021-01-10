package dts.logic.operation.operationHelpers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dts.boundaries.OperationBoundary;
import dts.boundaries.UserIdBoundary;
import dts.data.UserEntity;
import dts.logic.user.UserConverter;
import dts.logic.user.UsersDao;
import dts.util.ObjNotFoundException;

@Component("updateUserData")
public class UpdateUserDataHelper implements OperationHelper {

	private UsersDao usersDao;
	private UserConverter userConverter;

	@Autowired
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Autowired
	public void setUserConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	@Override
	@Transactional
	public Object invokeOperation(OperationBoundary operation) {

		UserIdBoundary userIdBoundary = new UserIdBoundary(operation.getInvokedBy().getUserId().getSpace(),
				operation.getInvokedBy().getUserId().getEmail());
		String id = userIdBoundary.toString();
		Optional<UserEntity> user = this.usersDao.findById(id);
		String avatar = (String) operation.getOperationAttributes().get("avatar");
		String username = (String) operation.getOperationAttributes().get("username");

		if (user.isPresent() && !avatar.isEmpty() && !username.isEmpty()) {
			user.get().setAvatar(avatar);
			user.get().setUsername(username);
			return this.userConverter.toBoundary(this.usersDao.save(user.get()));
		} else {
			throw new ObjNotFoundException("User can't be updated!");
		}

	}
}