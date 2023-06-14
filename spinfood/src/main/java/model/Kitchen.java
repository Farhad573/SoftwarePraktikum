package model;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.Objects;

/**
 * The Kitchen class represents a kitchen with its associated story and location.
 */
public class Kitchen {

    private double kitchen_story;
    private Location kitchen_location;
    private boolean emergencyKitcehn ;


    /**
     * Constructs a Kitchen object with the specified kitchen story and location.
     *
     * @param kitchen_story    the story of the kitchen
     * @param kitchen_location the location of the kitchen
     */
    public Kitchen(double kitchen_story, Location kitchen_location,Boolean emergencyKitcehn) {
        this.kitchen_story = kitchen_story;
        this.kitchen_location = kitchen_location;
        this.emergencyKitcehn = emergencyKitcehn;
    }

    /**
     * Constructs a Kitchen object with the specified kitchen location.
     *
     * @param kitchen_location the location of the kitchen
     */
    public Kitchen(Location kitchen_location) {
        this.kitchen_location = kitchen_location;
    }

    /**
     * Constructs a Kitchen object with the specified kitchen story.
     *
     * @param kitchen_story the story of the kitchen
     */
    public Kitchen(double kitchen_story) {
        this.kitchen_story = kitchen_story;
    }

    /**
     * Constructs an empty Kitchen object.
     */
    public Kitchen() {
    }



    /**
     * Returns the story of the kitchen.
     *
     * @return the kitchen story
     */
    public double getKitchen_story() {
        return kitchen_story;
    }

    /**
     * Returns the location of the kitchen.
     *
     * @return the kitchen location
     */
    public Location getKitchen_location() {
        return kitchen_location;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Kitchen)) {
            return false;
        }
        Kitchen otherKitchen = (Kitchen) o;
        return kitchen_story == otherKitchen.kitchen_story && kitchen_location.equals(otherKitchen.kitchen_location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kitchen_story, kitchen_location);
    }

    public boolean isEmergencyKitcehn() {
        return emergencyKitcehn;
    }

    /**
     * Returns a string representation of the Kitchen object.
     *
     * @return a string representation of the Kitchen object
     */
    @Override
    public String toString() {

        return "Kitchen{" +
                "kitchen_story = " + kitchen_story +
                ", kitchen_location= " + kitchen_location +
                '}';
    }
    public JsonObject toJson(){
        JsonObject kitchenJson = new JsonObject();
        kitchenJson.put("emergencyKitchen",emergencyKitcehn);
        kitchenJson.put("story",kitchen_story);
        kitchenJson.put("longitude",kitchen_location.getLongitude());
        kitchenJson.put("latitude",kitchen_location.getLatitude());
        return  kitchenJson;
    }

}
