package dts.boundaries;

public class UserIdBoundary {
	private String space;
	private String email;

	public UserIdBoundary() {
		this.space = "2021a.demo";
		this.email = "demo@maildomain.com";
	}

	public UserIdBoundary(String space, String email) {
		super();
		this.space = space;
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
}
