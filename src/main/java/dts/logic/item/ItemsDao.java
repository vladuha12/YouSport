package dts.logic.item;

import org.springframework.data.repository.CrudRepository;

import dts.data.ItemEntity;

public interface ItemsDao extends CrudRepository<ItemEntity, String> {

}
