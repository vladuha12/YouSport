package dts.boundaries;

public class UserIdWrapperBoundary {
	private UserIdBoundary userId;

	public UserIdWrapperBoundary() {
		super();
		this.userId = new UserIdBoundary();
	}

	public UserIdWrapperBoundary(UserIdBoundary userId) {
		super();
		this.userId = userId;
	}

	public UserIdBoundary getUserId() {
		return userId;
	}

	public void setUserId(UserIdBoundary userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return this.userId.toString();
	}

}
