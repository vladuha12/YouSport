package dts.logic;

import org.springframework.stereotype.Component;

import dts.boundaries.UserBoundary;
import dts.data.UserEntity;

@Component
public class UserConverter {

	// Converts from UserBoundary to UserEntity
	public UserEntity toEntity(UserBoundary newUser) {
		UserIdConverter userIdConverter = new UserIdConverter();
		UserEntity entity = new UserEntity();

		if (newUser.getUserId() != null)
			entity.setUserId(userIdConverter.toEntity(newUser.getUserId()));
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
		UserIdConverter userIdConverter = new UserIdConverter();
		UserBoundary boundary = new UserBoundary();

		if (entity.getUserId() != null)
			boundary.setUserId(userIdConverter.toBoundary(entity.getUserId()));
		if (entity.getRole() != null)
			boundary.setRole(entity.getRole());
		if (entity.getUsername() != null)
			boundary.setUsername(entity.getUsername());
		if (entity.getAvatar() != null)
			boundary.setAvatar(entity.getAvatar());

		return boundary;
	}
}
