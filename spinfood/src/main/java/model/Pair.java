package model;

import com.github.cliftonlabs.json_simple.JsonObject;
import controller.Distance;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * The Pair class represents a pair of participants.
 */
public class Pair {

    private  Participant person1;
    private  Participant person2;
    private FoodPreference mainFoodPreference;
    private  int ageDifference;

    private boolean pairSignUp;

    private  int preferenceDeviation;
    private boolean haveCooked ;
    private List<Pair> metPairsInStarter;
    private List<Pair> metPairsInMainDish;
    private List<Pair> metPairsInDessert;
    private Course course ;

    double sexDeviation;

    public double pathLength;


    /**
     * Constructs a Pair object with the given participants.
     *
     * @param person1 the first participant
     * @param person2 the second participant
     */
    public Pair(Participant person1, Participant person2,boolean pairSignUp) {
        this.person1 = person1;
        this.person2 = person2;
        this.pairSignUp = pairSignUp;
        // calculate Main Food Preference of the pair
        calculateMainFoodPreference(person1, person2);
        // calculate the Age difference of the pair
        this.ageDifference = calculateAgeDifference(person1,person2);
        this.preferenceDeviation = calculatePreferenceDeviation();
        this.haveCooked = false;
        this.metPairsInStarter = new ArrayList<>();
        this.metPairsInMainDish = new ArrayList<>();
        this.metPairsInDessert = new ArrayList<>();
        this.sexDeviation = calculateSexDifference();
    }
    public Pair(Boolean haveCooked){
        this.haveCooked = haveCooked;
    }


    /**
     *
     * @param person1
     * @param person2
     */
    private void calculateMainFoodPreference(Participant person1, Participant person2) {
        // if they have the same Food preferences
        if(person1.getFoodPreference() == person2.getFoodPreference()){
            this.mainFoodPreference = person1.getFoodPreference();
        }
        // (1, 2) meat & none -> meat
        else if ((person1.getFoodPreference() == FoodPreference.none ||
                person1.getFoodPreference() == FoodPreference.meat) &&
                (person2.getFoodPreference() == FoodPreference.none ||
                        person2.getFoodPreference() == FoodPreference.meat)) {
                this.mainFoodPreference = FoodPreference.meat;
        }
        // (1) veggie|vegan & (2) veggie|vegan -> vegan
         else if ((person1.getFoodPreference() == FoodPreference.veggie ||
                person1.getFoodPreference() == FoodPreference.vegan) &&
                (person2.getFoodPreference() == FoodPreference.vegan ||
                        person2.getFoodPreference() == FoodPreference.veggie)) {
            this.mainFoodPreference = FoodPreference.vegan;
        }
         // (1) meat|none & (2) Veggie|Veganer -> Veganer
        else if((person1.getFoodPreference() == FoodPreference.meat ||
                person1.getFoodPreference() == FoodPreference.none)
                && (person2.getFoodPreference() == FoodPreference.vegan ||
                person2.getFoodPreference() == FoodPreference.veggie)) {
            this.mainFoodPreference = person2.getFoodPreference();
        }
        // (2) meat|none && (1) vegan|vegan -> veggie | vegan
        else if((person2.getFoodPreference()==FoodPreference.meat ||
                person2.getFoodPreference() == FoodPreference.none)
                &&(person1.getFoodPreference() == FoodPreference.vegan ||
                person1.getFoodPreference() == FoodPreference.veggie)) {

            this.mainFoodPreference = person1.getFoodPreference();
        }
    }

    /**
     * Age difference is Absolute value of difference of two persons' Age Range
     * @param person1
     * @param person2
     * @return Age difference of two given participants.
     */
    private int calculateAgeDifference(Participant person1, Participant person2){
        return Math.abs(person1.getAgeRange() - person2.getAgeRange());
    }

    /**
     * Preference Deviation of a pair is absolute value of difference of values declared to Food preference of two person in the pair
     * @return Preference deviation of a pair
     */
    private int calculatePreferenceDeviation(){
        return Math.abs(this.person1.getFoodPreference().getValue() - this.person2.getFoodPreference().getValue());
    }

    private double calculateSexDifference(){
        int sexOfPerson1 = this.person1.getSex() == Sex.female? 1 : 0;
        int sexOfPerson2 = this.person2.getSex() == Sex.female? 1 : 0;
        return Math.abs(((sexOfPerson1 + sexOfPerson2) / 2.0) - 0.5);
    }

    public double getSexDeviation() {
        return sexDeviation;
    }

    public JsonObject toJson(){
        JsonObject pairJson = new JsonObject();
        pairJson.put("premade",pairSignUp);
        pairJson.put("foodPreference",mainFoodPreference.toString());
        JsonObject participant1 = person1.toJson();
        JsonObject participant2 = person2.toJson();
        pairJson.put("firstParticipant",participant1);
        pairJson.put("secondParticipant",participant2);
        return pairJson;
    }


    /**
     * Checks if the pair is valid.
     *
     * @return true if the pair is valid, false otherwise
     */
     public boolean validPair(){
        return true;
     }

    public boolean isHaveCooked() {
        return haveCooked;
    }

    public List<Pair> getMetPairsInStarter() {
        return metPairsInStarter;
    }

    public void setHaveCooked(boolean haveCooked) {
        this.haveCooked = haveCooked;
    }

    public void setMetPairsInStarter(List<Pair> metPairsInStarter) {
        this.metPairsInStarter = metPairsInStarter;
    }

    public void setMetPairsInMainDish(List<Pair> metPairsInMainDish) {
        this.metPairsInMainDish = metPairsInMainDish;
    }

    public void setMetPairsInDessert(List<Pair> metPairsInDessert) {
        this.metPairsInDessert = metPairsInDessert;
    }

    /**
     *
     * @return Age difference of the pair
     */
    public int getAgeDifference() {
        return ageDifference;
    }

    /**
     * @return Main Food Preference of this Pair
     */
    public FoodPreference getMainFoodPreference() {
        return mainFoodPreference;
    }

    public int getPreferenceDeviation() {
        return preferenceDeviation;
    }

    public Participant getPerson1() {
        return person1;
    }

    public Participant getPerson2() {
        return person2;
    }

    public Kitchen getKitchen(){return person1.getKitchen() != null? person1.getKitchen() : person2.getKitchen(); }

    /**
     * Returns a string representation of the Pair object.
     *
     * @return a string representation of the Pair object
     */
    @Override
    public String toString() {
        return "Pair {"  + "have cooked: " + this.haveCooked + ", " +
                "person1=" + "Id : " + person1.getId() + ", Name : " + person1.getName() + ", Age : " + person1.getAge() + ", AgeRange : "+ person1.getAgeRange() +
                ", Food Preference : " + person1.getFoodPreference() + ", Sex : " + person1.getSex() +
                ", has kitchen : " + person1.getHasKitchen() +
                ", kitchen : " + person1.getKitchen() +
                ", kitchenCount=" + person1.getKitchenCount() +
                ", person2=" + "Id : " + person2.getId() + ", Name : " + person2.getName() + ", Age : " + person2.getAge() + ", AgeRange : "+ person2.getAgeRange() +
                ", Food Preference : " + person2.getFoodPreference() + ", Sex : " + person2.getSex() +
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

    @Override
    public int hashCode() {

        return Objects.hash(person1, person2);

    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Pair> getMetPairsInMainDish() {
        return metPairsInMainDish;
    }

    public List<Pair> getMetPairsInDessert() {
        return metPairsInDessert;
    }


    public void printPairsWhoCooked(){
        StringBuilder builder = new StringBuilder();
        builder.append("have Pair cooked: " + this.isHaveCooked() + ", when : " + this.getCourse());
        System.out.println(builder);
    }

    public  void calculatePathLength(Pair pair) throws FileNotFoundException {
        Location location1 = GroupGenerator.kitchenLocationsInStarter.get(pair);
        Location location2 = GroupGenerator.kitchenLocationsInMainDish.get(pair);
        Location location3 = GroupGenerator.kitchenLocationsInDessert.get(pair);
        PartyLocation partyLocation = new PartyLocation();
        partyLocation.readCSVFilePartyLocation("src/main/resources/partylocation.csv");
        double partyLat = partyLocation.getLatitude();
        double partyLon = partyLocation.getLongitude();
        double distance1 = Distance.newCalculateDistance(location1.getLatitude(),location1.getLongitude(),location2.getLatitude(),location2.getLongitude());
        double distance2 = Distance.newCalculateDistance(location2.getLatitude(),location2.getLongitude(),location3.getLatitude(),location3.getLongitude());
        double distance3 = Distance.newCalculateDistance(location3.getLatitude(),location3.getLongitude(),partyLat,partyLon);
        this.pathLength = distance1 + distance2 + distance3;
    }
    public void printPairsWhoMet(){
        StringBuilder builder = new StringBuilder();
        builder.append("Course : Starter" + "\n");
        for(int i = 0; i< metPairsInStarter.size();i++){
            builder.append("pair"+i+" have cooked: " + metPairsInStarter.get(i).haveCooked + ", when?: " + metPairsInStarter.get(i).getCourse() + "\n" );
        }
        builder.append("Course : MainDish" + "\n");
        for(int i = 0; i< metPairsInMainDish.size();i++){
            builder.append("pair"+i+" have cooked: " + metPairsInMainDish.get(i).haveCooked + ", when?: " + metPairsInMainDish.get(i).getCourse() + "\n" );
        }
        builder.append("Course : Dessert" + "\n");
        for(int i = 0; i< metPairsInDessert.size();i++){
            builder.append("pair"+i+" have cooked: " + metPairsInDessert.get(i).haveCooked + ", when?: " + metPairsInDessert.get(i).getCourse() + "\n" );
        }
        System.out.println(builder);

    }


}
