package dts.logic.operation;

import java.util.List;

import dts.boundaries.OperationBoundary;

public interface EnhancedOperationsService extends OperationsService {

	List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail, int size, int page) throws Exception;

}
