package dts.logic.item;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import dts.data.ItemEntity;

public interface ItemsDao extends PagingAndSortingRepository<ItemEntity, String> {

	public List<ItemEntity> findAllByParents_itemId(@Param("parentsId") String parentsId, Pageable pageable);

	public List<ItemEntity> findAllByChildren_itemId(@Param("childrenId") String childrenId, Pageable pageable);

	public List<ItemEntity> findAllByNameLike(@Param("pattern") String pattern, Pageable pageable);

	public List<ItemEntity> findAllByActiveAndNameLike(@Param("active") boolean active,
			@Param("pattern") String pattern, Pageable pageable);

	public List<ItemEntity> findAllByType(@Param("type") String type, Pageable pageable);

	public List<ItemEntity> findAllByLatBetweenAndLngBetween(@Param("minLat") float minLat,
			@Param("maxLat") float maxLat, @Param("minLng") float minLng, @Param("maxLng") float maxLng,
			Pageable pageable);

	public ItemEntity findByActiveAndItemId(@Param("active") boolean active, @Param("itemId") String itemId);
}
