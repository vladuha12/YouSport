package dts.boundaries;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationBoundary {

	private IdBoundary operationId;
	private String type;
	private ItemIdWrapperBoundary item;
	private Date createdTimestamp;
	private UserIdWrapperBoundary invokedBy;
	private Map<String, Object> operationAttributes;

	public OperationBoundary() {
		this.operationId = new IdBoundary();
		createdTimestamp = new Date();
		operationAttributes = new HashMap<>();
	}

	public OperationBoundary(IdBoundary operationId) {
		this();
		this.operationId = operationId;
	}

	public OperationBoundary(String space, String id) {
		this();
		this.operationId = new IdBoundary(space, id);
	}

	public IdBoundary getOperationId() {
		return operationId;
	}

	public void setOperationId(IdBoundary operationId) {
		this.operationId = operationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ItemIdWrapperBoundary getItem() {
		return item;
	}

	public void setItem(ItemIdWrapperBoundary item) {
		this.item = item;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public UserIdWrapperBoundary getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(UserIdWrapperBoundary invokedBy) {
		this.invokedBy = new UserIdWrapperBoundary(invokedBy.getUserId());
	}

	public Map<String, Object> getOperationAttributes() {
		return operationAttributes;
	}

	public void setOperationAttributes(Map<String, Object> operationAttributes) {
		this.operationAttributes = operationAttributes;
	}

	@Override
	public String toString() {
		return "OperationBoundary [operationId=" + operationId + ", type=" + type + ", item=" + item
				+ ", createdTimestamp=" + createdTimestamp + ", invokedBy=" + invokedBy + ", operationAttributes="
				+ operationAttributes + "]";
	}

}
