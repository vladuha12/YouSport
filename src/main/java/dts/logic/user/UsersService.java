package dts.logic.user;

import java.util.List;
import dts.boundaries.UserBoundary;

public interface UsersService {
	public UserBoundary createUser(UserBoundary newUser) throws Exception;
	public UserBoundary login(String userSpace, String userEmail) throws Exception;
	public UserBoundary updateUser(String userSpace, String userEmail, UserBoundary update) throws Exception;
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail);
	public void deleteAllUsers(String adminSpace, String adminEmail);
}
