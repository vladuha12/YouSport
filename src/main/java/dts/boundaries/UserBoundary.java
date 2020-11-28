package dts.boundaries;

import dts.data.UserRole;

public class UserBoundary {
	private UserIdBoundary userId;
	private String email;
	private UserRole role;
	private String username;
	private String avatar;
	
	public UserBoundary() {
		this.userId =  new UserIdBoundary();
		this.role = UserRole.PLAYER;
		this.username = "Demo User";
		this.avatar = "ooOO_()OOoo";
	}
	
	public UserIdBoundary getUserId() {
		return userId;
	}

	public void setUserId(UserIdBoundary userId) {
		this.userId = userId;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setSpace(String space) {
		this.userId.setSpace(space);		
	}

	public void setEmail(String email) {
		this.email = email;
		this.userId.setEmail(email);
	}

}
