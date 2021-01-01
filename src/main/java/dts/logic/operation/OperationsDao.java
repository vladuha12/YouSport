package dts.logic.operation;

import org.springframework.data.repository.CrudRepository;

import dts.data.OperationEntity;

public interface OperationsDao extends CrudRepository<OperationEntity, String> {

}
