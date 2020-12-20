package dts.boundaries;

public class ItemIdWrapperBoundary {

	private IdBoundary itemId;

	public ItemIdWrapperBoundary() {
		super();
		this.itemId = new IdBoundary();
	}

	public ItemIdWrapperBoundary(IdBoundary itemId) {
		super();
		this.itemId = itemId;
	}

	public IdBoundary getItemId() {
		return itemId;
	}

	public void setItemId(IdBoundary itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return this.itemId.toString();
	}

}
