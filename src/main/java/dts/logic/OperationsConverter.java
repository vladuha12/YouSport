package dts.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dts.boundaries.IdBoundary;
import dts.boundaries.ItemBoundary;
import dts.boundaries.OperationBoundary;
import dts.boundaries.UserBoundary;
import dts.boundaries.UserIdBoundary;
import dts.data.ItemEntity;
import dts.data.OperationEntity;
import dts.data.UserRole;

@Component
public class OperationsConverter {

	private String spaceHelper;

	@Value("${spring.application.name:Space should be here}")
	public void setSpace(String space) {
		this.spaceHelper = space;
	}

	public OperationEntity toEntity(OperationBoundary newOperation) {
		OperationEntity entity = new OperationEntity();

		if (newOperation.getOperationId() != null)
			entity.setOperationId(newOperation.getOperationId().toString());
		else
			entity.setOperationId(new IdBoundary().toString());

		if (newOperation.getType() != null)
			entity.setType(newOperation.getType());

		// Converter
		if (newOperation.getItem() != null) {
			ItemConverter itemConverter = new ItemConverter();
			ItemEntity itemEntity = itemConverter.toEntity(newOperation.getItem());
			entity.setItem(itemEntity);
		}

		if (newOperation.getInvokedBy() != null)
			entity.setInvokedBy(newOperation.getInvokedBy().toString());

		entity.setCreatedTimestamp(newOperation.getCreatedTimestamp());
		entity.setOperationAttributes(newOperation.getOperationAttributes());

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

		if (entity.getInvokedBy() != null)
			boundary.setInvokedBy(fromStringToUserBoundary(entity.getInvokedBy()));

		// Converter
		if (entity.getItem() != null) {
			ItemConverter itemConverter = new ItemConverter();
			ItemBoundary itemBoundary = itemConverter.toBoundary(entity.getItem());
			boundary.setItem(itemBoundary);
		}

		if (entity.getType() != null)
			boundary.setType(entity.getType());

		if (entity.getOperationAttributes() != null)
			boundary.setOperationAttributes(entity.getOperationAttributes());

		return boundary;
	}

	private IdBoundary fromStringToIdBoundary(String id) {
		if (id != null) {
			String[] args = id.split("@");
			return new IdBoundary(args[0], args[1]);
		} else
			return null;
	}

	private UserBoundary fromStringToUserBoundary(String name) {
		if (name != null) {
			String[] args = name.split("&");
			return new UserBoundary(fromStringToUserIdBoundary(args[0]), UserRole.valueOf(args[1]), args[2], args[3],
					args[4]);
		} else {
			return null;
		}
	}

	private UserIdBoundary fromStringToUserIdBoundary(String id) {
		if (id != null) {
			String[] args = id.split("#");
			return new UserIdBoundary(args[0], args[1]);
		} else
			return null;
	}
}
