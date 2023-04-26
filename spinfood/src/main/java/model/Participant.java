package model;

public class Participant {

    private String id ;
    private String name ;
    private int age ;
    private AgeRange ageRange;
    private HasKitchen hasKitchen;
    private FoodPreference foodPreference;
    private Sex sex;

    // for test
    public Participant(String id, String name, int age, HasKitchen hasKitchen, FoodPreference foodPreference, Sex sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hasKitchen = hasKitchen;
        this.foodPreference = foodPreference ;
        this.sex = sex ;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", has this person kitchen= " + hasKitchen + '\'' +
                ", Food preference= " + foodPreference + '\'' +
                ", Sex= " + sex ;
    }
}
