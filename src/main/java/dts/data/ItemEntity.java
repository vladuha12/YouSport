package dts.data;

import java.util.Date;
import java.util.Map;

public class ItemEntity {
	private String itemId;
	private String name;
	private String type;
	private Date createdTimestamp;
	private Boolean active;
	private String createdBy;
	private String location;
	private Map<String, Object> itemAttributes;

	public ItemEntity() {
	}

	public ItemEntity(String itemId, String name, String type, Date createdTimestamp, Boolean active,
			String createdBy, String location, Map<String, Object> itemAttributes) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.type = type;
		this.createdTimestamp = createdTimestamp;
		this.active = active;
		this.createdBy = createdBy;
		this.location = location;
		this.itemAttributes = itemAttributes;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Map<String, Object> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, Object> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

}
