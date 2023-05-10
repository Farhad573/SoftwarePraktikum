package model;

public class Location {

    private double longitude;
    private double latitude;


    /**
     * get longitude and latitude as kitchen Location
     * @param longitude
     * @param latitude
     */
    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }



    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * return detail of Location
     * @return
     */
    @Override
    public String toString() {
        return "Location{ " +
                "longitude= " + longitude +
                ", latitude= " + latitude +
                '}';
    }
}
