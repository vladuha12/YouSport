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
		ItemEntity entity = this.itemConverter.toEntity(newItem);	
		
		this.itemStore.put(newItem.getItemId().toString(), entity);
		
		return this.itemConverter.toBoundary(entity);
	}

	@Override
	public ItemBoundary update(String managerSpace, String managerEmail, String itemSpace, String itemId, ItemBoundary update) {
		ItemEntity entity = this.itemConverter.toEntity(update);	
		
		this.itemStore.put(update.getItemId().toString(), entity);
		
		return this.itemConverter.toBoundary(entity);
	}

	@Override
	public List<ItemBoundary> getAll(String userSpace, String userEmail) {
		return this.itemStore.values().stream().map(entity -> itemConverter.toBoundary(entity)).collect(Collectors.toList());
	}

	@Override
	public ItemBoundary getSpecificItem(String userSpace, String userEmail, String itemSpace, String itemId) {		 
		 return itemConverter.toBoundary(this.itemStore.get(itemId));			 
	}

	@Override
	public void deleteAll(String adminSpace, String adminEmail) {
		this.itemStore.clear();		
	}

}
