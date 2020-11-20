package dts.boundaries;

import dts.UserRoles;

public class UserBoundary {
	private IdBoundary userId;
	private UserRoles role;
	private String username;
	private String avatar;
	private String email;
	
	public UserBoundary() {
		this.userId =  new IdBoundary();
		this.role = UserRoles.PLAYER;
		this.username = "Demo User";
		this.avatar = "ooOO_()OOoo";
		this.email = "test@test.com";
	}
	
	public IdBoundary getUserId() {
		return userId;
	}

	public void setUserId(IdBoundary userId) {
		this.userId = userId;
	}

	public UserRoles getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserBoundary [userId=" + userId + ", role=" + role + ", username=" + username + ", avatar=" + avatar
				+ ", email=" + email + "]";
	}
}
