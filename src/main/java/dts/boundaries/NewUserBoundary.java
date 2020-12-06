package dts.boundaries;

import dts.data.UserRole;

public class NewUserBoundary {
	private String email;
	private UserRole role;
	private String username;
	private String avatar;
	
	public NewUserBoundary() {
		this.role = UserRole.PLAYER;
		this.username = "Demo User";
		this.avatar = "ooOO_()OOoo";
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

	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	
}
