package controller;

/**
 * The Distance class provides methods for calculating the distance between two positions using latitude and longitude.
 * It uses the Haversine formula to compute the distance.
 */
public class Distance {

    private static final double EARTH_RADIUS = 6371.0; // in kilometers

    /**
     * Calculates the distance between two positions using latitude and longitude with the Haversine formula.
     *
     * @param lat1 the latitude of the first position
     * @param lon1 the longitude of the first position
     * @param lat2 the latitude of the second position
     * @param lon2 the longitude of the second position
     * @return the distance between the two positions in kilometers
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        return distance;
    }

    public static double newCalculateDistance(double lat1, double lon1, double lat2, double lon2) {

        return Math.sqrt(Math.pow(lat1 - lat2, 2.0) + Math.pow(lon1 - lon2, 2.0));
    }
}
