package dts.logic;

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
import dts.boundaries.ItemBoundary;
import dts.data.ItemEntity;

@Service
public class ItemsServiceImplementation implements ItemsService {
	private Map<String, ItemEntity> itemStore;
	private ItemConverter itemConverter;

	@Autowired
	public void setItemConverter(ItemConverter itemConverter) {
		this.itemConverter = itemConverter;
	}

	@PostConstruct
	public void init() {
		this.itemStore = Collections.synchronizedMap(new HashMap<>()); // thread safe map
	}

	@Override
	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem) throws Exception {
		try {
			ItemEntity entity = this.itemConverter.toEntity(newItem);
			IdBoundary id = new IdBoundary();
			entity.setItemId(id.toString());
			entity.setCreatedTimestamp(new Date());
			
			this.itemStore.put(entity.getItemId(), entity);

			return this.itemConverter.toBoundary(entity);
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	@Override
	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId,
			ItemBoundary update) throws Exception {
		ItemEntity old = this.itemStore.get(itemId);
		if (old != null) {
			ItemEntity entity = this.itemConverter.toEntity(update);
			entity.setItemId(old.getItemId());
			entity.setCreatedTimestamp(old.getCreatedTimestamp());
			entity.setCreatedBy(old.getCreatedBy());
			this.itemStore.put(itemId, entity);

			return this.itemConverter.toBoundary(entity);
		} else
			throw new RuntimeException();
	}

	@Override
	public List<ItemBoundary> getAll(String userSpace, String userEmail) {
		return this.itemStore.values().stream().map(entity -> itemConverter.toBoundary(entity))
				.collect(Collectors.toList());
	}

	@Override
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId)
			throws Exception {
		if (this.itemStore.get(itemId) != null)
			return itemConverter.toBoundary(this.itemStore.get(itemId));
		else
			throw new RuntimeException();
	}

	@Override
	public void deleteAll(String adminSpace, String adminEmail) {
		this.itemStore.clear();
	}

}
