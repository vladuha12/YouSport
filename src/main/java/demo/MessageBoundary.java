package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// {"message":"hello", "creationDate":date}
public class MessageBoundary {
	private String message;
	private Date creationDate;
	private Boolean important;
	private Long number;
	private Double decimalValue;
	private NameBoundary name;
	private PriorityBoundary priority;
	private Map<String, Object> moreDetails;

	public MessageBoundary() {
//		super();
		// this.message = "default message";
		this.creationDate = new Date();
		this.important = false;
		this.number = 0L;
		this.decimalValue = 0.0;
		this.name = new NameBoundary("Jane", "Smith");
		this.priority = PriorityBoundary.MEDIUM;
		
		this.moreDetails = new HashMap<>();
		this.moreDetails.put("booleanAttribute", true);
		this.moreDetails.put("innerData", "hello");
		this.moreDetails.put("theValue", 98.12);
		this.moreDetails.put("innerName", new NameBoundary("anotherFirstName", "ofAnotherLastName"));
	}

	public MessageBoundary(String message) {
		this();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Boolean getImportant() {
		return important;
	}
	
	public void setImportant(Boolean important) {
		this.important = important;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Double getDecimalValue() {
		return decimalValue;
	}

	public void setDecimalValue(Double decimalValue) {
		this.decimalValue = decimalValue;
	}

	public NameBoundary getName() {
		return name;
	}
	
	public void setName(NameBoundary name) {
		this.name = name;
	}
	
	public PriorityBoundary getPriority() {
		return priority;
	}
	
	public void setPriority(PriorityBoundary priority) {
		this.priority = priority;
	}

	public Map<String, Object> getMoreDetails() {
		return moreDetails;
	}

	public void setMoreDetails(Map<String, Object> moreDetails) {
		this.moreDetails = moreDetails;
	}

	
}
