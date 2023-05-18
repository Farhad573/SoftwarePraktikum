package model;
/**
 * The Location class represents a geographical location with longitude and latitude coordinates.
 */
public class Location {

    private double longitude;
    private double latitude;


    /**
     * Constructs a Location object with the specified longitude and latitude.
     *
     * @param longitude the longitude coordinate of the location
     * @param latitude  the latitude coordinate of the location
     */
    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Returns the longitude coordinate of the location.
     *
     * @return the longitude coordinate
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the latitude coordinate of the location.
     *
     * @return the latitude coordinate
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the longitude coordinate of the location.
     *
     * @param longitude the longitude coordinate to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Sets the latitude coordinate of the location.
     *
     * @param latitude the latitude coordinate to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        Location otherLocation = (Location) o;
        return this.latitude == otherLocation.latitude && this.longitude == otherLocation.longitude;
    }

    /**
     * Returns a string representation of the Location object.
     *
     * @return a string representation of the Location object
     */
    @Override
    public String toString() {
        return "Location{ " +
                "longitude= " + longitude +
                ", latitude= " + latitude +
                '}';
    }
}
