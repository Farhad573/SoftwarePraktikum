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

    /**
     * for all participant
     * @param id
     * @param name
     * @param age
     * @param hasKitchen
     * @param foodPreference
     * @param sex
     */
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

    /**
     * to show the partner participant
     * @param id_2
     * @param name_2
     * @param age_2
     * @param sex_2
     */
    public Participant(String id_2, String name_2, int age_2, Sex sex_2) {
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
        return "Participant{" +"\n" +
                "id='" + id +"\n" +
                ", name='" + name +"\n" +
                ", age='" + age +"\n" +
                ", has this person kitchen= " + hasKitchen +"\n" +
                ", Food preference= " + foodPreference +"\n" +
                ", Sex= " + sex +"\n" +" }"+"\n" +
                "******************";
    }


    public String toString_2() {
        return "Person : " +"\n" +
                "id='" + id  +"\n" +
                ", name='" + name  +"\n" +
                ", age=" + age +"\n" +
                ", ageRange=" + ageRange +"\n" +
                ", hasKitchen=" + hasKitchen +"\n" +
                ", foodPreference=" + foodPreference +"\n" +
                ", sex=" + sex +"\n" +
                ", kitchen_Story=" + kitchen_Story +"\n" +
                ", kitchen_Location=" + kitchen_Location +"\n" +
                ", id_2='" + id_2  +"\n" +
                ", name_2='" + name_2  +"\n" +
                ", age_2=" + age_2 +"\n" +
                ", sex_2=" + sex_2 +" }"+"\n" +
                "******************";
    }
}
