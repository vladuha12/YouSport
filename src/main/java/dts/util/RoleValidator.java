package dts.util;

import java.util.Optional;

import dts.boundaries.UserIdBoundary;
import dts.boundaries.UserIdWrapperBoundary;
import dts.data.UserEntity;
import dts.data.UserRole;
import dts.logic.user.UsersDao;

public abstract class RoleValidator {

	public static boolean canUserPerformOperation(UserIdWrapperBoundary userId, UserRole requiredRole, UsersDao usersDao) {

		String id = userId.getUserId().toString();

		Optional<UserEntity> existing = usersDao.findById(id);
		if (existing.isPresent()) {
			if (existing.get().getRole().toString() == requiredRole.toString())
				return true;

		}
		return false;
	}

	public static boolean canUserPerformOperation(UserIdBoundary userIdBoundary, UserRole requiredRole,
			UsersDao usersDao) {
		return canUserPerformOperation(new UserIdWrapperBoundary(userIdBoundary), requiredRole, usersDao);
	}
}
