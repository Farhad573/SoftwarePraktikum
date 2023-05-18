package model;

/**
 * The Participant class represents a participant with registered person attributes.
 * It stores information such as participant's ID, name, age, kitchen details, and preferences.
 */
public class Participant {

    private String id ;
    private String name ;
    private int age ;
    private int ageRange;
    private HasKitchen hasKitchen;
    private FoodPreference foodPreference;
    private Sex sex;
    private Kitchen kitchen;

    /**
     * Constructs a Participant object with the specified attributes.
     *
     * @param id             the participant's ID
     * @param name           the participant's name
     * @param age            the participant's age
     * @param hasKitchen     the participant's kitchen availability
     * @param foodPreference the participant's food preference
     * @param sex            the participant's sex
     * @param kitchen  the participant's kitchen
     */
    public Participant(String id, String name, int age,HasKitchen hasKitchen, FoodPreference foodPreference, Sex sex, Kitchen kitchen) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.ageRange = ageRange;
        this.hasKitchen = hasKitchen;
        this.foodPreference = foodPreference ;
        this.sex = sex ;
        this.kitchen = kitchen;
        this.ageRange = calculateAgeRange(age);
    }

    public Participant(FoodPreference foodPreference){
        this.foodPreference = foodPreference;
    }

    public Participant(int age){
        this.age = age;
        this.ageRange = calculateAgeRange(age);
    }

    /**
     * Returns the participant's ID.
     *
     * @return the participant's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the participant's name.
     *
     * @return the participant's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the participant's age.
     *
     * @return the participant's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the participant's age range.
     *
     * @return the participant's age range
     */
    public int getAgeRange() {
        return ageRange;
    }

    /**
     * Returns the participant's kitchen availability.
     *
     * @return the participant's kitchen availability
     */
    public HasKitchen getHasKitchen() {
        return hasKitchen;
    }

    /**
     * Returns the participant's food preference.
     *
     * @return the participant's food preference
     */
    public FoodPreference getFoodPreference() {
        return foodPreference;
    }

    /**
     * Returns the participant's sex.
     *
     * @return the participant's sex
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Returns the participant's kitchen story.
     *
     * @return the participant's kitchen story
     */
    public Kitchen getKitchen() {
        return kitchen;
    }

    private int calculateAgeRange(int age) {
        if (age <= 17) {
            return 0;
        } else if (age >= 18 && age <= 23) {
            return 1;
        } else if (age >= 24 && age <= 27) {
            return 2;
        } else if (age >= 28 && age <= 30) {
            return 3;
        } else if (age >= 31 && age <= 35) {
            return 4;
        } else if (age >= 36 && age <= 41) {
            return 5;
        } else if (age >= 42 && age <= 46) {
            return 6;
        } else if (age >= 47 && age <= 56) {
            return 7;
        } else if (age >= 57) {
            return 8;
        }
        return -1;
    }



    /**
     * Compares this Participant object with another object for equality.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Participant)){
            return false;
        }
        Participant participant = (Participant) o;
        return id == participant.id && name == participant.name;
    }

    /**
     * Returns a string representation of the Participant object.
     *
     * @return a string representation of the Participant object
     */
    public String toString() {
        return "Person : " +"\n" +
                "id=" + id  +"\n" +
                ", name=" + name  +"\n" +
                ", age=" + age +"\n" +
                ", ageRange=" + ageRange +"\n" +
                ", hasKitchen=" + hasKitchen +"\n" +
                ", foodPreference=" + foodPreference +"\n" +
                ", sex=" + sex +"\n" +
                kitchen.toString() +"\n" +
                "******************";
    }


}
