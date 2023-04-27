public class Participant {
    private String ID ;
    private String name;
    private int age;
    private Sex sex;
    private Has_Kitchen has_kitchen;
    private FoodPreference foodPreference;
    private Kitchen kitchen;
    private String ID_2;
    private String name_2;
    private double age_2;
    private Sex sex_2;
    private AgeRange ageRange;
    private int count_wg;

    public Participant(String ID, String name, int age, Sex sex, Has_Kitchen has_kitchen, FoodPreference foodPreference, Kitchen kitchen, String ID_2, String name_2, int age_2, Sex sex_2, AgeRange ageRange, int count_wg) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.has_kitchen = has_kitchen;
        this.foodPreference = foodPreference;
        this.kitchen = kitchen;
        this.ID_2 = ID_2;
        this.name_2 = name_2;
        this.age_2 = age_2;
        this.sex_2 = sex_2;
        this.ageRange = ageRange;
        this.count_wg = count_wg;
    }




    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Participant() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Has_Kitchen getHas_kitchen() {
        return has_kitchen;
    }

    public void setHas_kitchen(Has_Kitchen has_kitchen) {
        this.has_kitchen = has_kitchen;
    }

    public FoodPreference getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(FoodPreference foodPreference) {
        this.foodPreference = foodPreference;
    }

    public String getID_2() {
        return ID_2;
    }

    public void setID_2(String ID_2) {
        this.ID_2 = ID_2;
    }

    public String getName_2() {
        return name_2;
    }

    public void setName_2(String name_2) {
        this.name_2 = name_2;
    }

    public double getAge_2() {
        return age_2;
    }

    public void setAge_2(double age_2) {
        this.age_2 = age_2;
    }

    public Sex getSex_2() {
        return sex_2;
    }

    public void setSex_2(Sex sex_2) {
        this.sex_2 = sex_2;
    }

    public AgeRange getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(AgeRange ageRange) {
        this.ageRange = ageRange;
    }

    public int getCount_wg() {
        return count_wg;
    }

    public void setCount_wg(int count_wg) {
        this.count_wg = count_wg;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", has_kitchen=" + has_kitchen +
                ", foodPreference=" + foodPreference +
                ", kitchen=" + kitchen.toString() +
                ", ID_2='" + ID_2 + '\'' +
                ", name_2='" + name_2 + '\'' +
                ", age_2=" + age_2 +
                ", sex_2=" + sex_2 +
                ", ageRange=" + ageRange +
                ", count_wg=" + count_wg +
                '}';
    }
}
