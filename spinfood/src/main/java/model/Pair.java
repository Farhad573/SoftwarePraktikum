package model;

/**
 * The Pair class represents a pair of participants.
 */
public class Pair {

    private Participant person1;
    private Participant person2;

    /**
     * Constructs a Pair object with the given participants.
     *
     * @param person1 the first participant
     * @param person2 the second participant
     */
    public Pair(Participant person1, Participant person2) {
        this.person1 = person1;
        this.person2 = person2;
    }

    /**
     * Checks if the pair is valid.
     *
     * @return true if the pair is valid, false otherwise
     */
     public boolean validPair(){
        return true;
     }

    /**
     * Returns a string representation of the Pair object.
     *
     * @return a string representation of the Pair object
     */
    @Override
    public String toString() {
        return "Pair{" +
                "person1=" + "Id : " + person1.getId() + ", Name : " + person1.getName() +
                ", Food Preference : " + person1.getFoodPreference() + ", Sex" + person1.getSex() +
                ", has kitchen : " + person1.getHasKitchen() +
                ", person2=" + "Id : " + person2.getId() + ", Name : " + person2.getName() +
                ", Food Preference : " + person2.getFoodPreference() + ", Sex" + person2.getSex() +
                ", has kitchen : " + person2.getHasKitchen() +
                '}';
    }

    /**
     * Checks if this Pair object is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
            //they are considered equal.
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
            //classes are different, they are not considered equal.
        }
        Pair otherPair = (Pair) obj;
        return person1.equals(otherPair.person1) && person2.equals(otherPair.person2);

    }

}
