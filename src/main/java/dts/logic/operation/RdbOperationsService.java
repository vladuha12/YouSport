package dts.logic.operation;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dts.boundaries.IdBoundary;
import dts.boundaries.OperationBoundary;
import dts.data.IdGeneratorEntity;
import dts.data.OperationEntity;
import dts.logic.IdGeneratorEntityDao;
import dts.util.BadRequestException;

@Service
public class RdbOperationsService implements EnhancedOperationsService {

	private OperationsDao operationsDao;
	private OperationsConverter operationConverter;
	private IdGeneratorEntityDao IdGeneratorEntityDao;

	@Autowired
	public RdbOperationsService(OperationsDao operationsDao, OperationsConverter operationConverter,
			IdGeneratorEntityDao IdGeneratorEntityDao) {
		super();
		this.operationsDao = operationsDao;
		this.operationConverter = operationConverter;
		this.IdGeneratorEntityDao = IdGeneratorEntityDao;
	}

	@Override
	public Object invokeOperation(OperationBoundary operation) throws Exception {

		if (isValidInput(operation)) {
			OperationEntity entity = this.operationConverter.toEntity(operation);

			IdGeneratorEntity idGeneratorEntity = new IdGeneratorEntity();
			idGeneratorEntity = this.IdGeneratorEntityDao.save(idGeneratorEntity);
			UUID newId = idGeneratorEntity.getId();
			this.IdGeneratorEntityDao.deleteById(newId);

			IdBoundary id = new IdBoundary(newId.toString());
			entity.setOperationId(id.toString());
			entity.setCreatedTimestamp(new Date());

			return this.operationConverter.toBoundary(this.operationsDao.save(entity));
		} else {
			throw new BadRequestException("Cant proccess request due to invalid request message framing");
		}

	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) throws Exception {

		if (adminSpace != null && adminEmail != null) {
			return StreamSupport.stream(this.operationsDao.findAll().spliterator(), false)
					.map(entity -> this.operationConverter.toBoundary(entity)).collect(Collectors.toList());
		} else
			throw new Exception("Bad Credentials");

	}

	@Override
	public void deleteAllActions(String adminSpace, String adminEmail) throws Exception {
		if (adminSpace != null && adminEmail != null)
			this.operationsDao.deleteAll();
		else
			throw new Exception("Bad Credentials");

	}

	private boolean isValidInput(OperationBoundary boundary) {
		boolean valid = true;

		if (boundary.getType() == null || boundary.getType().isEmpty() || boundary.getInvokedBy() == null
				|| boundary.getItem() == null)
			valid = false;

		return valid;
	}

}
