package dts.logic.operation;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dts.boundaries.IdBoundary;
import dts.boundaries.OperationBoundary;
import dts.data.OperationEntity;

@Service
public class OperationsServiceImplementation implements OperationsService {

	private Map<String, OperationEntity> operationsStorage;
	private OperationsConverter operationsConverter;

	@Autowired
	public void setOperationsConverter(OperationsConverter operationsConverter) {
		this.operationsConverter = operationsConverter;
	}

	@PostConstruct
	public void init() {
		this.operationsStorage = Collections.synchronizedMap(new HashMap<>());
	}

	@Override
	public Object invokeOperation(OperationBoundary operation) {

		operation.setOperationId(new IdBoundary());
		operation.setCreatedTimestamp(new Date());

		OperationEntity entity = this.operationsConverter.toEntity(operation);

		// MOCKUP of database storage
		this.operationsStorage.put(operation.getOperationId().getId(), entity);

		return this.operationsConverter.toBoundary(entity);
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) throws Exception {

		if (adminSpace != null && adminEmail != null) {
			return this.operationsStorage.values().stream().map(entity -> operationsConverter.toBoundary(entity))
					.collect(Collectors.toList());
		} else
			throw new Exception("Bad Credentials");
	}

	@Override
	public void deleteAllActions(String adminSpace, String adminEmail) throws Exception {
		if (adminSpace != null && adminEmail != null)
			this.operationsStorage.clear();
		else
			throw new Exception("Bad Credentials");
	}

}
