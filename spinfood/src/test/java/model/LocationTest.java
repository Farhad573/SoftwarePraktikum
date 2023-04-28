package model;

import controller.Distance;

import static org.junit.jupiter.api.Assertions.*;

class DistanceTest {

    @org.junit.jupiter.api.Test
    void calculateDistance() {
        double lat1 = 40.748817;
        double lon1 = -73.985428;
        double lat2 = 51.507351;
        double lon2 = -0.127758;
        double expectedDistance = 5566.367194009563; // in kilometers
        double actualDistance = Distance.calculateDistance(lat1, lon1, lat2, lon2);
        double delta = 0.1; // acceptable difference between expected and actual values
        assertEquals(expectedDistance, actualDistance, delta);
    }
}