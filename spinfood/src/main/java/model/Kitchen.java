package model;

public class Kitchen {

    private int kitchen_story;
    private Location kitchen_location;


    // because of data in csvData, we should have different constructor
    public Kitchen(int kitchen_story, Location kitchen_location) {
        this.kitchen_story = kitchen_story;
        this.kitchen_location = kitchen_location;
    }

    public Kitchen(Location kitchen_location) {
        this.kitchen_location = kitchen_location;
    }

    public Kitchen(int kitchen_story) {
        this.kitchen_story = kitchen_story;
    }


    public Kitchen() {
    }

    public int getKitchen_story() {
        return kitchen_story;
    }

    public Location getKitchen_location() {
        return kitchen_location;
    }
}
