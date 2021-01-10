package dts.logic.operation;

import org.springframework.data.repository.PagingAndSortingRepository;

import dts.data.OperationEntity;

public interface OperationsDao extends PagingAndSortingRepository<OperationEntity, String> {

}
