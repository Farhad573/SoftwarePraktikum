public class Kitchen {
    private double floor;
    private Location location;

    public Kitchen(int floor, Location location) {
        this.floor = floor;
        this.location = location;
    }

    public Kitchen() {
    }

    public double getFloor() {
        return floor;
    }

    public void setFloor(double floor) {
        this.floor = floor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Kitchen{" +
                "floor=" + floor +
                ", location=" + location +
                '}';
    }
}
