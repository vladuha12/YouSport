package dts.boundaries;

public class LocationBoundary {
	private String lat;
	private String lag;

	public LocationBoundary() {
		this.lat = "32.115139";
		this.lag = "34.817804";
	}

	public LocationBoundary(String lat, String lag) {
		super();
		this.lat = lat;
		this.lag = lag;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLag() {
		return lag;
	}

	public void setLag(String lag) {
		this.lag = lag;
	}
	
	@Override
	public String toString() {
		return this.lat + "@" + this.lag;
	}
}
