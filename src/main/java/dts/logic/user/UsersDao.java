package dts.logic.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import dts.data.UserEntity;

public interface UsersDao extends PagingAndSortingRepository<UserEntity, String>{

}
