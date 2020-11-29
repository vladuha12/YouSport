package dts.data;

import java.util.Date;
import java.util.Map;

import dts.boundaries.ItemBoundary;
import dts.boundaries.UserBoundary;

public class OperationEntity {

	private String operationId;
	private String type;
	private ItemBoundary item;
	private Date createdTimestamp;
	private UserBoundary invokedBy;
	private Map<String, Object> operationAttributes;

	public OperationEntity() {

	}

	public OperationEntity(String operationId, String type, ItemBoundary item, Date createdTimestamp,
			UserBoundary invokedBy, Map<String, Object> operationAttributes) {
		super();
		this.operationId = operationId;
		this.type = type;
		this.item = item;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.operationAttributes = operationAttributes;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ItemBoundary getItem() {
		return item;
	}

	public void setItem(ItemBoundary item) {
		this.item = item;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public UserBoundary getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(UserBoundary invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getOperationAttributes() {
		return operationAttributes;
	}

	public void setOperationAttributes(Map<String, Object> operationAttributes) {
		this.operationAttributes = operationAttributes;
	}

}
