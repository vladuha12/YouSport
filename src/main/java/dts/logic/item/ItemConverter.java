package dts.logic.item;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dts.Application;
import dts.boundaries.IdBoundary;
import dts.boundaries.ItemBoundary;
import dts.boundaries.LocationBoundary;
import dts.boundaries.UserIdWrapperBoundary;
import dts.data.ItemEntity;
import dts.logic.user.UserConverter;

@Component
public class ItemConverter {

	private ObjectMapper jackson;
	private UserConverter userConverter;

	@Autowired
	public void setUserConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

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
					new UserIdWrapperBoundary(this.userConverter.toBoundary((entity.getCreatedBy())).getUserId()));
		}

		if (entity.getLocation() != null) {
			boundary.setLocation(fromStringToLocationBoundary(entity.getLocation()));
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
			entity.setCreatedBy(this.userConverter.toEntity(boundary.getCreatedBy().getUserId()));
		}

		if (boundary.getLocation() != null) {
			entity.setLocation(boundary.getLocation().toString());
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
			// String[] args = id.split(Application.ID_DELIMITER);
			return new IdBoundary(args[0], args[1]);
		} else {
			return null;
		}
	}

	/*
	 * private UserBoundary fromStringToUserBoundary(String name) { if (name !=
	 * null) { String[] args = name.split("&"); return new
	 * UserBoundary(fromStringToUserIdBoundary(args[0]), UserRole.valueOf(args[1]),
	 * args[2], args[3]); } else { return null; } }
	 */

	private LocationBoundary fromStringToLocationBoundary(String location) {
		if (location != null) {
			String[] args = location.split("@");
			return new LocationBoundary(args[0], args[1]);
		} else {
			return null;
		}
	}

	/*
	 * private UserIdBoundary fromStringToUserIdBoundary(String id) { if (id !=
	 * null) { String[] args = id.split(Application.ID_DELIMITER); return new
	 * UserIdBoundary(args[0], args[1]); } else return null; }
	 */
}
