package dts.logic.item;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dts.Application;
import dts.boundaries.IdBoundary;
import dts.boundaries.ItemBoundary;
import dts.boundaries.LocationBoundary;
import dts.boundaries.UserIdBoundary;
import dts.boundaries.UserIdWrapperBoundary;
import dts.data.ItemEntity;

@Component
public class ItemConverter {

	private ObjectMapper jackson;

	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
	}

	public ItemBoundary toBoundary(ItemEntity entity) {
		ItemBoundary boundary = new ItemBoundary();

		if (entity.getItemId() != null) {
			boundary.setItemId(fromStringToIdBoundary(entity.getItemId()));
		} else
			boundary.setItemId(new IdBoundary());

		if (entity.getCreatedBy() != null) {
			boundary.setCreatedBy(
					new UserIdWrapperBoundary(fromStringToUserIdBoundary(entity.getCreatedBy().toString())));
		}

		if (entity.getLat() != null && entity.getLng() != null) {
			boundary.setLocation(new LocationBoundary(entity.getLat(), entity.getLng()));
		}

		if (entity.getCreatedTimestamp() != null) {
			boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		}

		if (entity.getName() != null) {
			boundary.setName(entity.getName());
		}

		if (entity.getType() != null) {
			boundary.setType(entity.getType());
		}

		if (entity.getActive() != null) {
			boundary.setActive(entity.getActive());
		}

		if (entity.getItemAttributes() != null) {
			boundary.setItemAttributes(this.toBoundaryAsMap(entity.getItemAttributes()));
		}

		return boundary;
	}

	public ItemEntity toEntity(ItemBoundary boundary) {
		ItemEntity entity = new ItemEntity();

		if (boundary.getItemId() != null) {
			entity.setItemId(boundary.getItemId().toString());
		} else
			entity.setItemId(new IdBoundary().toString());

		if (boundary.getCreatedBy() != null) {
			entity.setCreatedBy(boundary.getCreatedBy().getUserId().toString());
		}

		if (boundary.getLocation() != null) {
			entity.setLat(boundary.getLocation().getLat());
			entity.setLng(boundary.getLocation().getLng());
		}

		if (boundary.getCreatedTimestamp() != null) {
			entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		}

		if (boundary.getName() != null) {
			entity.setName(boundary.getName());
		}

		if (boundary.getType() != null) {
			entity.setType(boundary.getType());
		}

		if (boundary.getActive() != null) {
			entity.setActive(boundary.getActive());
		}

		if (boundary.getItemAttributes() != null) {
			entity.setItemAttributes(this.toEntity(boundary.getItemAttributes()));
		}

		return entity;
	}

	private String toEntity(Map<String, Object> attributes) {
		// Object->JSON:
		if (attributes != null) {
			try {
				return this.jackson.writeValueAsString(attributes);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return "{}";
		}
	}

	private Map<String, Object> toBoundaryAsMap(String attributes) {
		// JSON > Object
		if (attributes != null) {
			try {
				return this.jackson.readValue(attributes, Map.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return new HashMap<>();
		}
	}

	private IdBoundary fromStringToIdBoundary(String id) {
		if (id != null) {
			String[] args = id.split(Application.ID_DELIMITER);
			return new IdBoundary(args[0], args[1]);
		} else {
			return null;
		}
	}

	private UserIdBoundary fromStringToUserIdBoundary(String id) {
		if (id != null) {
			String[] args = id.split(Application.ID_DELIMITER);
			return new UserIdBoundary(args[0], args[1]);
		} else
			return null;
	}

}
