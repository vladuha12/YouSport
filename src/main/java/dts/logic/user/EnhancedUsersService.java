package dts.logic.user;

import java.util.List;

import dts.boundaries.UserBoundary;

public interface EnhancedUsersService extends UsersService{

	List<UserBoundary> getAllUsers(String adminSpace, String adminEmail, int size, int page);
}
