package model;

public class Participant {

    private String id ;
    private String name ;
    private String age ;
    private AgeRange ageRange;
    private HasKitchen hasKitchen;
    private FoodPreference foodPreference;
    private Sex sex;

    public Participant(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.ageRange = ageRange;
        this.hasKitchen = hasKitchen;
        this.foodPreference = foodPreference;
        this.sex = sex;
    }
}
