package dts.logic;

import org.springframework.stereotype.Component;

import dts.boundaries.IdBoundary;
import dts.boundaries.ItemBoundary;
import dts.boundaries.LocationBoundary;
import dts.boundaries.UserBoundary;
import dts.boundaries.UserIdBoundary;
import dts.data.ItemEntity;
import dts.data.UserRole;

@Component
public class ItemConverter {

	public ItemBoundary toBoundary(ItemEntity entity) {
		ItemBoundary boundary = new ItemBoundary();

		if (entity.getItemId() != null) {
			boundary.setItemId(fromStringToIdBoundary(entity.getItemId()));
		} else
			boundary.setItemId(new IdBoundary());

		if (entity.getCreatedBy() != null) {
			boundary.setCreatedBy(fromStringToUserBoundary(entity.getCreatedBy()));
		}

		if (entity.getLocation() != null) {
			boundary.setLocation(fromStringToLocationBoundary(entity.getLocation()));
		}

		if (entity.getCreationDate() != null) {
			boundary.setCreationDate(entity.getCreationDate());
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
			boundary.setItemAttributes(entity.getItemAttributes());
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
			entity.setCreatedBy(boundary.getCreatedBy().toString());
		}

		if (boundary.getLocation() != null) {
			entity.setLocation(boundary.getLocation().toString());
		}

		if (boundary.getCreationDate() != null) {
			entity.setCreationDate(boundary.getCreationDate());
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
			entity.setItemAttributes(boundary.getItemAttributes());
		}

		return entity;
	}

	private IdBoundary fromStringToIdBoundary(String id) {
		if (id != null) {
			String[] args = id.split("@");
			return new IdBoundary(args[0], args[1]);
		} else {
			return null;
		}
	}

	private UserBoundary fromStringToUserBoundary(String name) {
		if (name != null) {
			String[] args = name.split("&");
			return new UserBoundary(fromStringToUserIdBoundary(args[0]), UserRole.valueOf(args[1]), args[2], args[3],
					args[4]);
		} else {
			return null;
		}
	}

	private LocationBoundary fromStringToLocationBoundary(String location) {
		if (location != null) {
			String[] args = location.split("@");
			return new LocationBoundary(args[0], args[1]);
		} else {
			return null;
		}
	}

	private UserIdBoundary fromStringToUserIdBoundary(String id) {
		if (id != null) {
			String[] args = id.split("#");
			return new UserIdBoundary(args[0], args[1]);
		} else
			return null;
	}
}
