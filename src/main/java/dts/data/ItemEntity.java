package dts.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ITEMS")
public class ItemEntity {
	private String itemId;
	private String name;
	private String type;
	private Date createdTimestamp;
	private Boolean active;
	private String createdBy;
	private float lat;
	private float lng;
	private String itemAttributes;
	private Set<ItemEntity> children;
	private Set<ItemEntity> parents;

	public ItemEntity() {
	}

	public ItemEntity(String itemId, String name, String type, Date createdTimestamp, Boolean active, String createdBy,
			float lat, float lng, String itemAttributes) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.type = type;
		this.createdTimestamp = createdTimestamp;
		this.active = active;
		this.createdBy = createdBy;
		this.lat = lat;
		this.lng = lng;
		this.itemAttributes = itemAttributes;
	}

	@Id
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

	@Temporal(TemporalType.TIMESTAMP)
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

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	@Lob
	public String getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(String itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "children")
	public Set<ItemEntity> getParents() {
		return parents;
	}

	public void setParents(Set<ItemEntity> parents) {
		this.parents = parents;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<ItemEntity> getChildren() {
		return children;
	}

	public void setChildren(Set<ItemEntity> children) {
		this.children = children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemEntity other = (ItemEntity) obj;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		return true;
	}

	public void addChild(ItemEntity child) {
		if (this.children == null) {
			this.children = new HashSet<ItemEntity>();
		}
		this.children.add(child);
	}

	public void addParent(ItemEntity parent) {
		if (this.parents == null) {
			this.parents = new HashSet<ItemEntity>();
		}
		this.parents.add(parent);
	}

}
