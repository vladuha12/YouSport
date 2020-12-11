package dts.logic.user;

import org.springframework.stereotype.Component;

import dts.boundaries.UserBoundary;
import dts.boundaries.UserIdBoundary;
import dts.data.UserEntity;

@Component
public class UserConverter {

	// Converts from UserBoundary to UserEntity
	public UserEntity toEntity(UserBoundary newUser) {
		UserEntity entity = new UserEntity();

		if (newUser.getUserId() != null) {
			entity.setUserId(newUser.getUserId().toString());
		} else
			entity.setUserId(new UserIdBoundary().toString());
		
		if (newUser.getRole() != null)
			entity.setRole(newUser.getRole());
		if (newUser.getUsername() != null)
			entity.setUsername(newUser.getUsername());
		if (newUser.getAvatar() != null)
			entity.setAvatar(newUser.getAvatar());

		return entity;
	}

	// Converts from UserEntity to UserBoundary
	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();

		if (entity.getUserId() != null) {
			boundary.setUserId(fromStringToUserIdBoundary(entity.getUserId()));
		} else
			boundary.setUserId(new UserIdBoundary());
		
		if (entity.getRole() != null)
			boundary.setRole(entity.getRole());
		if (entity.getUsername() != null)
			boundary.setUsername(entity.getUsername());
		if (entity.getAvatar() != null)
			boundary.setAvatar(entity.getAvatar());

		return boundary;
	}

	private UserIdBoundary fromStringToUserIdBoundary(String userId) {
		if (userId != null) {
			String[] args = userId.split("\\$");
			return new UserIdBoundary(args[0], args[1]);
		} else {
			return null;
		}
	}
}
