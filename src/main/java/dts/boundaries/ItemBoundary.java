package dts.boundaries;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ItemBoundary {
	private IdBoundary itemId;
	private String name;
	private String type;
	private Date createdTimestamp;
	private Boolean active;
	private UserBoundary createdBy;
	private LocationBoundary location;
	private Map<String, Object> itemAttributes;

	public ItemBoundary() {

		this.itemId = new IdBoundary();
		this.type = "TEST TYPE";
		this.name = "TEST ITEM NAME";
		this.active = true;
		this.createdTimestamp = new Date();
		this.createdBy = new UserBoundary();
		this.location = new LocationBoundary();
		this.itemAttributes = new HashMap<>();
		this.itemAttributes.put("key1", "key 1 test");
		this.itemAttributes.put("key2", 123456);

	}

	public ItemBoundary(String itemId) {
		this();
		this.itemId = new IdBoundary(itemId);
	}

	public IdBoundary getItemId() {
		return itemId;
	}

	public void setItemId(IdBoundary itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public UserBoundary getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserBoundary createdBy) {
		this.createdBy = createdBy;
	}

	public LocationBoundary getLocation() {
		return location;
	}

	public void setLocation(LocationBoundary location) {
		this.location = location;
	}

	public Map<String, Object> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, Object> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	@Override
	public String toString() {
		return "ItemBoundary [itemId=" + itemId + ", name=" + name + ", type=" + type + ", createdTimestamp=" + createdTimestamp
				+ ", active=" + active + ", createdBy=" + createdBy + ", location=" + location + ", itemAttributes="
				+ itemAttributes + "]";
	}

}
