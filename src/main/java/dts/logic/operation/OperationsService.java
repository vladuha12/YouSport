package dts.logic.operation;

import java.util.List;

import dts.boundaries.OperationBoundary;

public interface OperationsService {

	public Object invokeOperation(OperationBoundary operation) throws Exception;

	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) throws Exception;

	public void deleteAllActions(String adminSpace, String adminEmail) throws Exception;

}
