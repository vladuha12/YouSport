package dts.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dts.boundaries.ItemBoundary;
import dts.data.ItemEntity;

@Service
public class ItemsServiceImplementation implements ItemsService{
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
	public ItemBoundary Create(String managerSpace, String managerEmail, ItemBoundary newItem) {			
		if (this.itemStore.get(newItem.getItemId().getId()) == null) {
			ItemEntity entity = this.itemConverter.toEntity(newItem);
			this.itemStore.put(newItem.getItemId().getId(), entity);
		
			return this.itemConverter.toBoundary(entity);
		}
		else
			return null;
	}

	@Override
	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId, ItemBoundary update) {
		if (this.itemStore.get(itemId) != null) {
			ItemEntity entity = this.itemConverter.toEntity(update);
			this.itemStore.put(itemId, entity);
		
			return this.itemConverter.toBoundary(entity);
		}
		else
			return null;
	}

	@Override
	public List<ItemBoundary> getAll(String userSpace, String userEmail) {
		return this.itemStore.values().stream().map(entity -> itemConverter.toBoundary(entity)).collect(Collectors.toList());
	}

	@Override
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {	
		if (this.itemStore.get(itemId) != null)
		 return itemConverter.toBoundary(this.itemStore.get(itemId));
		else
			return null;
	}

	@Override
	public void deleteAll(String adminSpace, String adminEmail) {
		this.itemStore.clear();		
	}

}
