package dts.logic.operation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dts.Application;
import dts.boundaries.IdBoundary;
import dts.boundaries.ItemIdWrapperBoundary;
import dts.boundaries.OperationBoundary;
import dts.boundaries.UserIdBoundary;
import dts.boundaries.UserIdWrapperBoundary;
import dts.data.OperationEntity;

@Component
public class OperationsConverter {

	private ObjectMapper jackson;

	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
	}

	public OperationEntity toEntity(OperationBoundary newOperation) {
		OperationEntity entity = new OperationEntity();

		if (newOperation.getOperationId() != null)
			entity.setOperationId(newOperation.getOperationId().toString());
		else
			entity.setOperationId(new IdBoundary().toString());

		if (newOperation.getType() != null)
			entity.setType(newOperation.getType());

		if (newOperation.getItem() != null) {
			entity.setItem(newOperation.getItem().toString());
		}

		if (newOperation.getInvokedBy() != null)
			entity.setInvokedBy(newOperation.getInvokedBy().toString());

		entity.setCreatedTimestamp(newOperation.getCreatedTimestamp());
		entity.setOperationAttributes(this.toEntity(newOperation.getOperationAttributes()));

		return entity;
	}

	public OperationBoundary toBoundary(OperationEntity entity) {
		OperationBoundary boundary = new OperationBoundary();

		if (entity.getOperationId() != null)
			boundary.setOperationId(this.fromStringToIdBoundary(entity.getOperationId().toString()));
		else
			boundary.setOperationId(new IdBoundary());

		if (entity.getCreatedTimestamp() != null)
			boundary.setCreatedTimestamp(entity.getCreatedTimestamp());

		if (entity.getInvokedBy() != null) {
			boundary.setInvokedBy(new UserIdWrapperBoundary(fromStringToUserIdBoundary(entity.getInvokedBy())));
		}

		if (entity.getItem() != null) {
			boundary.setItem(new ItemIdWrapperBoundary(fromStringToIdBoundary(entity.getItem())));
		}

		if (entity.getType() != null)
			boundary.setType(entity.getType());

		if (entity.getOperationAttributes() != null)
			boundary.setOperationAttributes(this.toBoundaryAsMap(entity.getOperationAttributes()));

		return boundary;
	}

	private IdBoundary fromStringToIdBoundary(String id) {
		if (id != null) {
			String[] args = id.split(Application.ID_DELIMITER);
			return new IdBoundary(args[0], args[1]);
		} else {
			return null;
		}
	}

	private UserIdBoundary fromStringToUserIdBoundary(String id) {
		if (id != null) {
			String[] args = id.split(Application.ID_DELIMITER);
			return new UserIdBoundary(args[0], args[1]);
		} else
			return null;
	}

	private String toEntity(Map<String, Object> attributes) {
		// Object->JSON:
		if (attributes != null) {
			try {
				return this.jackson.writeValueAsString(attributes);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return "{}";
		}
	}

	private Map<String, Object> toBoundaryAsMap(String attributes) {
		// JSON > Object
		if (attributes != null) {
			try {
				return this.jackson.readValue(attributes, Map.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return new HashMap<>();
		}
	}
}
