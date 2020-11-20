package dts.boundaries;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationBoundary {

	private IdBoundary operationId;
	private String type;
	private ItemBoundary item;
	private Date createdTimestamp;
	private UserBoundary invokedBy;
	private Map<String, Object> operationAttributes;

	public OperationBoundary() {
		this.operationId = new IdBoundary();
		this.type = "operationType";
		item = new ItemBoundary();
		createdTimestamp = new Date();
		invokedBy = new UserBoundary();

		operationAttributes = new HashMap<>();
		operationAttributes.put("key1", "Amazing operation");
		operationAttributes.put("key2", true);
		operationAttributes.put("key3", 88.88);
		operationAttributes.put("key4", new NameBoundary("First", "Last"));

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
