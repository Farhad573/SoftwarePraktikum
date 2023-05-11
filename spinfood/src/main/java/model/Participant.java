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

    /**
     * for all participant
     * @param id
     * @param name
     * @param age
     * @param hasKitchen
     * @param foodPreference
     * @param sex
     */
    public Participant(String id, String name, int age, HasKitchen hasKitchen, FoodPreference foodPreference, Sex sex, int kitchen_Story, Location kitchen_Location) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hasKitchen = hasKitchen;
        this.foodPreference = foodPreference ;
        this.sex = sex ;
        this.kitchen_Story = kitchen_Story;
        this.kitchen_Location = kitchen_Location;
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

    public String toString() {
        return "Person" +"\n" +
                "id='" + id  +"\n" +
                ", name='" + name  +"\n" +
                ", age=" + age +"\n" +
                ", ageRange=" + ageRange +"\n" +
                ", hasKitchen=" + hasKitchen +"\n" +
                ", foodPreference=" + foodPreference +"\n" +
                ", sex=" + sex +"\n" +
                ", kitchen_Story=" + kitchen_Story +"\n" +
                ", kitchen_Location=" + kitchen_Location +"\n" +
                "******************";
    }


}
