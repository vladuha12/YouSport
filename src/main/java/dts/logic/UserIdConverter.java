package dts.logic;

import org.springframework.stereotype.Component;

import dts.boundaries.UserIdBoundary;
import dts.data.UserIdEntity;

@Component
public class UserIdConverter {
	public UserIdEntity toEntity(UserIdBoundary newId) {
		return new UserIdEntity(newId.getSpace(),newId.getEmail());
	}
	
	public UserIdBoundary toBoundary(UserIdEntity entity) {
		return new UserIdBoundary(entity.getSpace(),entity.getEmail());
	}
}
