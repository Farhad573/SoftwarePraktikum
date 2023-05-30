package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PartyLocation {

    private double longitude;
    private double latitude;

    public void readCSVFilePartyLocation(String csvFileName) throws FileNotFoundException {
        File csvFile = new File(csvFileName);
        Scanner scanner = new Scanner(csvFile);
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // skip header row
        }
        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().split(",");
            String longitudeStr = fields[0];
            String latitudeStr = fields[1];

            // Convert longitude and latitude to double
            double longitudeValue = Double.parseDouble(longitudeStr);
            double latitudeValue = Double.parseDouble(latitudeStr);

            // Update instance variables
            this.longitude = longitudeValue;
            this.latitude = latitudeValue;
        }
        scanner.close();
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "PartyLocation{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
