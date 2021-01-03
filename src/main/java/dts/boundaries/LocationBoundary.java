package dts.boundaries;

public class LocationBoundary {
	private float lat;
	private float lng;

	public LocationBoundary() {
	}

	public LocationBoundary(float lat, float lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return this.lat + "@" + this.lng;
	}
}
