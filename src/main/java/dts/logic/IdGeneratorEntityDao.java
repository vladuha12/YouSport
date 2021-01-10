package dts.logic;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import dts.data.IdGeneratorEntity;

public interface IdGeneratorEntityDao extends CrudRepository<IdGeneratorEntity, Long> {

}
