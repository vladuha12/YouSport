package dts.boundaries;

public class LocationBoundary {
	private String lat;
	private String lan;

	public LocationBoundary() {
		this.lat = "32.115139";
		this.lan = "34.817804";
	}

	public LocationBoundary(String lat, String lan) {
		super();
		this.lat = lat;
		this.lan = lan;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLan() {
		return lan;
	}

	public void setLan(String lan) {
		this.lan = lan;
	}
	
	@Override
	public String toString() {
		return this.lat + "@" + this.lan;
	}
}
