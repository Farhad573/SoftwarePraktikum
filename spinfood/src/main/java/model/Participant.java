package model;

public class Participant {

    private String id ;
    private String name ;
    private int age ;
    private AgeRange ageRange;
    private HasKitchen hasKitchen;
    private FoodPreference foodPreference;
    private Sex sex;
    private int kitchen_Story;
    private Location kitchen_Location;
    private String id_2;
    private String name_2;
    private int age_2;
    private Sex sex_2;

    // for test
    public Participant(String id, String name, int age, HasKitchen hasKitchen, FoodPreference foodPreference, Sex sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hasKitchen = hasKitchen;
        this.foodPreference = foodPreference ;
        this.sex = sex ;
        this.kitchen_Story = kitchen_Story;
    }

    public Participant(String id, String name, int age, HasKitchen hasKitchen, FoodPreference foodPreference, Sex sex,int kitchen_Story,
                       Location kitchen_Location, String id_2, String name_2, int age_2, Sex sex_2) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hasKitchen = hasKitchen;
        this.foodPreference = foodPreference ;
        this.sex = sex ;
        this.kitchen_Story = kitchen_Story;
        this.kitchen_Location = kitchen_Location;
        this.id_2 = id_2;
        this.name_2 = name_2;
        this.age_2 = age_2;
        this.sex_2 = sex_2;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public AgeRange getAgeRange() {
        return ageRange;
    }

    public HasKitchen getHasKitchen() {
        return hasKitchen;
    }

    public FoodPreference getFoodPreference() {
        return foodPreference;
    }

    public Sex getSex() {
        return sex;
    }

    public int getKitchen_Story() {
        return kitchen_Story;
    }

    public Location getKitchen_Location() {
        return kitchen_Location;
    }

    public String getId_2() {
        return id_2;
    }

    public String getName_2() {
        return name_2;
    }

    public int getAge_2() {
        return age_2;
    }

    public Sex getSex_2() {
        return sex_2;
    }

    public String toString_1() {
        return "Participant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", has this person kitchen= " + hasKitchen + '\'' +
                ", Food preference= " + foodPreference + '\'' +
                ", Sex= " + sex ;
    }


    public String toString_2() {
        return "Participant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", ageRange=" + ageRange +
                ", hasKitchen=" + hasKitchen +
                ", foodPreference=" + foodPreference +
                ", sex=" + sex +
                ", kitchen_Story=" + kitchen_Story +
                ", kitchen_Location=" + kitchen_Location +
                ", id_2='" + id_2 + '\'' +
                ", name_2='" + name_2 + '\'' +
                ", age_2=" + age_2 +
                ", sex_2=" + sex_2 +
                '}';
    }
}
