package demo;

import java.util.HashMap;
import java.util.Map;

public class UserBoundary {
	private Map<String, Object> userId;
	private UserRoles role;
	private String username;
	private String avatar;
	
	public UserBoundary() {
		this.userId = new HashMap<>(); // TODO initialize id when storing messages in the database
		this.userId.put("space", "2021a.demo");
		this.userId.put("email", "demo@maildomain.com");
		this.role = UserRoles.PLAYER;
		this.username = "Demo User";
		this.avatar = "ooOO_()OOoo";
	}
	
	public Map<String, Object> getUserId() {
		return userId;
	}

	public void setUserId(Map<String, Object> userId) {
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

	@Override
	public String toString() {
		return "User [userId=" + userId + ", role=" + role + ", username=" + username + ", avatar=" + avatar + "]";
	}
}
