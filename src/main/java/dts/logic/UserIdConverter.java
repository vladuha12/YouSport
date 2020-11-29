package dts.logic;

import org.springframework.stereotype.Component;

import dts.boundaries.UserIdBoundary;
import dts.data.UserIdEntity;

@Component
public class UserIdConverter {
	// Converts from UserIdBoundary to UserIdEntity
	public UserIdEntity toEntity(UserIdBoundary newId) {
		return new UserIdEntity(newId.getSpace(),newId.getEmail());
	}
	
	// Converts from UserIdEntity to UserIdBoundary
	public UserIdBoundary toBoundary(UserIdEntity entity) {
		return new UserIdBoundary(entity.getSpace(),entity.getEmail());
	}
}
