package dts.boundaries;

import java.util.UUID;

import dts.Application;

public class IdBoundary {
	private String space;
	private String id;

	public IdBoundary() {
		this.space = Application.APPLICATION_NAME;
		this.id = UUID.randomUUID().toString();
	}

	public IdBoundary(String space, String id) {
		super();
		this.space = space;
		this.id = id;
	}

	public IdBoundary(String id) {
		this.space = Application.APPLICATION_NAME;
		this.id = id;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.space + Application.ID_DELIMITER + this.id;
	}
}
