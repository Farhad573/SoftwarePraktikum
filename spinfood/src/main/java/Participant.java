public class Participant {

    private String id;
    private String name;
    private FoodPreference foodPreference;
    private int age;
    private Sex sex;
    private HasKitchen hasKitchen;
    private int kitchenStory;
    private float kitchenLongitude;
    private float kitchenLatitude;
    private String id2;
    private String name2;
    private int age2;
    private Sex sex2;

    public Participant(String id, String name, FoodPreference foodPreference, int age, Sex sex, HasKitchen hasKitchen, int kitchenStory,
                       float kitchenLatitude, float kitchenLongitude, String id2, String name2, int age2, Sex sex2){
        this.id= id;
        this.name = name;
        this.foodPreference = foodPreference;
        this.age = age;
        this.sex = sex;
        this.hasKitchen = hasKitchen;
        this.kitchenStory = kitchenStory;
        this.kitchenLatitude = kitchenLatitude;
        this.kitchenLongitude = kitchenLongitude;
        this.id2 = id2;
        this.name2 = name2;
        this.age2 = age2;
        this.sex2 = sex2;
    }



    // getter and setter
    public void setId(String id){
        this.id= id;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public FoodPreference getFoodPreference(){
        return foodPreference;
    }
    public void setFoodPreference(FoodPreference foodPreference){
        this.foodPreference = foodPreference;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public Sex getSex(){
        return sex;
    }
    public void setSex(Sex sex){
        this.sex = sex;
    }
    public HasKitchen getHasKitchen(){
        return hasKitchen;
    }
    public void setHasKitchen(HasKitchen hasKitchen){
        this.hasKitchen = hasKitchen;
    }
    public int getKitchenStory(){
        return kitchenStory;
    }
    public void setKitchenStory(int kitchenStory){
        this.kitchenStory = kitchenStory;
    }
    public float getKitchenLatitude(){
        return kitchenLatitude;
    }
    public void setKitchenLatitude(float kitchenLatitude){
        this.kitchenLatitude = kitchenLatitude;
    }
    public float getKitchenLongitude(){
        return kitchenLongitude;
    }
    public void setKitchenLongitude(float kitchenLongitude){
        this.kitchenLongitude = kitchenLongitude;
    }
    public String getId2(){
        return id2;
    }
    public void setId2(String id2){
        this.id2 = id2;
    }
    public String getName2(){
        return name2;
    }
    public void setName2(String name2){
        this.name2 = name2;
    }
    public int getAge2(){
        return age2;
    }
    public void setAge2(int age2){
        this.age2 = age2;
    }
    public Sex getSex2(){
        return sex2;
    }
    public void setSex2(Sex sex2){
        this.sex2 = sex2;
    }


}
