package model;

public class Pair {
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


    public Pair(String id, String name, int age, HasKitchen hasKitchen, FoodPreference foodPreference, Sex sex,int kitchen_Story,
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


    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Pair)){
            return false;
        }
        Pair pair = (Pair) o;
        return id == pair.id && name == pair.name;
    }


    public String toString() {
        return "pair : " +"\n" +
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
