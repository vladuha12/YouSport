package dts.dal.user;

import org.springframework.data.repository.CrudRepository;
import dts.data.UserEntity;

public interface UsersDao extends CrudRepository<UserEntity, String>{

}
