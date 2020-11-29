package dts.data;

public class UserIdEntity {
	private String space;
	private String email;

	public UserIdEntity() {
		this.space = "2021a.demo";
		this.email = "demo@maildomain.com";
	}

	public UserIdEntity(String space, String email) {
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
