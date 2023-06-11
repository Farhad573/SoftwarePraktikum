package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The PartyLocation class represents the location of the party.
 */
public class PartyLocation {

    private double longitude;
    private double latitude;

    /**
     * Reads the party location data from a CSV file.
     *
     * @param csvFileName The name of the CSV file.
     * @throws FileNotFoundException if the specified file is not found.
     */
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

    /**
     * Gets the longitude of the party location.
     *
     * @return The longitude value.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the latitude of the party location.
     *
     * @return The latitude value.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns a string representation of the PartyLocation object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "PartyLocation{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
