package dts.boundaries;

import java.util.UUID;

public class IdBoundary {
	private String space;
	private String id;

	public IdBoundary() {
		this.space = "tempSpace";
		this.id = UUID.randomUUID().toString();
	}

	public IdBoundary(String space, String id) {
		super();
		this.space = space;
		this.id = id;
	}

	public IdBoundary(String id) {
		this.space = "tempSpace";
		this.id = id;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	/*
	 * @Value("${spring.application.name:Space should be here}") public void
	 * setSpace(String space) { this.space = space; }
	 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.space + "@" + this.id;
	}
}
