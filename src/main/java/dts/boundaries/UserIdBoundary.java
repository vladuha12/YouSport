package dts.boundaries;

import dts.Application;

public class UserIdBoundary {
	private String space;
	private String email;

	public UserIdBoundary() {
		this.space = Application.APPLICATION_NAME;
		this.email = "demo@maildomain.com";
	}

	public UserIdBoundary(String space, String email) {
		super();
		this.space = Application.APPLICATION_NAME;
		this.email = email;
	}

	public UserIdBoundary(String email) {
		super();
		this.space = Application.APPLICATION_NAME;
		this.email = email;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return this.space + Application.ID_DELIMITER + this.email;
	}
}
