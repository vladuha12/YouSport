package dts.logic;

import org.springframework.stereotype.Component;

import dts.boundaries.IdBoundary;
import dts.boundaries.OperationBoundary;
import dts.data.OperationEntity;

@Component
public class OperationsConverter {

	public OperationEntity toEntity(OperationBoundary newOperation) {
		OperationEntity entity = new OperationEntity();

		if (newOperation.getOperationId() != null)
			entity.setOperationId(newOperation.toString());

		if (newOperation.getType() != null)
			entity.setType(newOperation.getType());

		if (newOperation.getItem() != null)
			entity.setItem(newOperation.getItem());

		if (newOperation.getInvokedBy() != null)
			entity.setInvokedBy(newOperation.getInvokedBy());

		entity.setCreatedTimestamp(newOperation.getCreatedTimestamp());
		entity.setOperationAttributes(newOperation.getOperationAttributes());

		return entity;
	}

	public OperationBoundary toBoundary(OperationEntity entity) {
		OperationBoundary boundary = new OperationBoundary();

		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		boundary.setOperationId(this.fromStringToBoundary(entity.getOperationId()));
		boundary.setInvokedBy(entity.getInvokedBy());
		boundary.setItem(entity.getItem());
		boundary.setType(entity.getType());
		boundary.setOperationAttributes(entity.getOperationAttributes());

		return boundary;
	}

	private IdBoundary fromStringToBoundary(String id) {
		if (id != null) {
			String[] args = id.split("@");
			return new IdBoundary(args[0], args[1]);
		} else
			return null;
	}
}
