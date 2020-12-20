package dts.data;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class IdGeneratorEntity {
	private UUID id;

	public IdGeneratorEntity() {
	}

	@Id
	@GeneratedValue
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
