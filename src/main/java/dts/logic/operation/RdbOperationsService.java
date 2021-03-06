package dts.logic.operation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import dts.boundaries.IdBoundary;
import dts.boundaries.OperationBoundary;
import dts.data.IdGeneratorEntity;
import dts.data.ItemEntity;
import dts.data.OperationEntity;
import dts.data.UserRole;
import dts.logic.IdGeneratorEntityDao;
import dts.logic.item.ItemsDao;
import dts.logic.operation.operationHelpers.OperationHelper;
import dts.logic.user.UsersDao;
import dts.util.BadRequestException;
import dts.util.ForbiddenException;
import dts.util.ObjNotFoundException;
import dts.util.RoleValidator;

@Service
public class RdbOperationsService implements EnhancedOperationsService {

	private OperationsDao operationsDao;
	private OperationsConverter operationConverter;
	private IdGeneratorEntityDao IdGeneratorEntityDao;

	private ConfigurableApplicationContext appContext;

	private UsersDao usersDao;
	private ItemsDao itemsDao;

	@Autowired
	public void setAppContext(ConfigurableApplicationContext appContext) {
		this.appContext = appContext;
	}

	@Autowired
	public RdbOperationsService(OperationsDao operationsDao, OperationsConverter operationConverter,
			IdGeneratorEntityDao IdGeneratorEntityDao, UsersDao usersDao, ItemsDao itemsDao) {
		super();
		this.operationsDao = operationsDao;
		this.operationConverter = operationConverter;
		this.IdGeneratorEntityDao = IdGeneratorEntityDao;
		this.usersDao = usersDao;
		this.itemsDao = itemsDao;
	}

	@Override
	public Object invokeOperation(OperationBoundary operation) throws Exception {

		if (isValidInput(operation)) {
			String type = operation.getType();
			// Check invokedBy permissions - Only a "PLAYER" can perform an operation
			// If the initiator is not a "PLAYER" - throw code 403 - Forbidden
			if (!RoleValidator.canUserPerformOperation(operation.getInvokedBy(), UserRole.PLAYER, usersDao)) {
				System.err.println(operation.getInvokedBy().toString() + " cant perform operation");
				throw new ForbiddenException("invokeOperation - Can only be performed by a Player");
			}

			// Ensure the item exists and is active in db else - throw ObjNotFound
			if (!type.equals("getAllFieldsBySportAndDistance") && !type.equals("updateUserData")) {
				ItemEntity targetItem = itemsDao.findByActiveAndItemId(true, operation.getItem().toString());
				if (targetItem == null) {
					throw new ObjNotFoundException("No such item");
				}
			}

			// Act according to operation type (getAllFieldsBySportAndDistance,
			// updateFieldData, bindSportToField)
			Object rv;
			OperationHelper helper = null;
			try {
				helper = this.appContext.getBean(type, OperationHelper.class);
				rv = helper.invokeOperation(operation);
			} catch (BeansException e) {
				throw new RuntimeException("invalid operation type");
			}

			OperationEntity entity = this.operationConverter.toEntity(operation);

			IdGeneratorEntity idGeneratorEntity = new IdGeneratorEntity();
			idGeneratorEntity = this.IdGeneratorEntityDao.save(idGeneratorEntity);
			Long newId = idGeneratorEntity.getId();
			this.IdGeneratorEntityDao.deleteById(newId);

			IdBoundary id = new IdBoundary(newId.toString());
			entity.setOperationId(id.toString());
			entity.setCreatedTimestamp(new Date());

			return rv;
			// return this.operationConverter.toBoundary(this.operationsDao.save(entity));
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
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail, int size, int page)
			throws Exception {
		if (adminSpace != null && adminEmail != null) {
			return this.operationsDao
					.findAll(PageRequest.of(page, size, Direction.DESC, "createdTimestamp", "operationId")).getContent()
					.stream().map(this.operationConverter::toBoundary).collect(Collectors.toList());
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

	/*
	 * private boolean canUserPerformOperation(UserIdWrapperBoundary userId) {
	 * UserIdBoundary userIdBoundary = userId.getUserId(); String id =
	 * userIdBoundary.toString(); Optional<UserEntity> existing =
	 * this.usersDao.findById(id); if (existing.isPresent()) { if
	 * (existing.get().getRole() == UserRole.PLAYER) return true; }
	 * 
	 * return false; }
	 */

}
