package dts.data;

import java.util.Date;
import java.util.Map;

public class OperationEntity {

	private String operationId;
	private String type;
	private ItemEntity item;
	private Date createdTimestamp;
	private String invokedBy;
	private Map<String, Object> operationAttributes;

	public OperationEntity() {
	}

	public OperationEntity(String operationId, String type, ItemEntity item, Date createdTimestamp, String invokedBy,
			Map<String, Object> operationAttributes) {
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

	public ItemEntity getItem() {
		return item;
	}

	public void setItem(ItemEntity item) {
		this.item = item;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(String invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getOperationAttributes() {
		return operationAttributes;
	}

	public void setOperationAttributes(Map<String, Object> operationAttributes) {
		this.operationAttributes = operationAttributes;
	}

}
