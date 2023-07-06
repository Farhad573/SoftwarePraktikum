package model;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group implements Comparable<Group> {
    Pair pair1;
    Pair pair2;
    Pair pair3;
    Pair cookingPair;

    private double fitness;


    private FoodPreference mainFoodPreference;
    private final int ageDifference;

    private final int preferenceDeviation;
    private double sexDeviation;
    private Course course;
    private Kitchen kitchen;

    public Group(Pair pair1, Pair pair2, Pair pair3,Course course) {
        this.pair1 = pair1;
        this.pair2 = pair2;
        this.pair3 = pair3;
        this.ageDifference = calculateAgeDifference();
        this.preferenceDeviation = calculatePreferenceDeviation();
        calculateMainFoodPreference(pair1, pair2, pair3);
        this.sexDeviation = calculateSexDeviation();
        this.course = course;
    }

    public Group(Pair pair1, Pair pair2, Pair pair3) {
        this.pair1 = pair1;
        this.pair2 = pair2;
        this.pair3 = pair3;
        this.ageDifference = calculateAgeDifference();
        this.preferenceDeviation = calculatePreferenceDeviation();
        calculateMainFoodPreference(pair1, pair2, pair3);
        this.sexDeviation = calculateSexDeviation();
        this.course = course;
    }

    public JsonObject toJson(){
        JsonObject groupJson = new JsonObject();
        groupJson.put("course",course.toString());
        groupJson.put("foodType",mainFoodPreference.toString());
        JsonObject kitchen = this.kitchen.toJson();
        groupJson.put("kitchen",kitchen);
        JsonObject cookingPair = this.cookingPair.toJson();
        List<Pair> pairs = getPairsInGroup().stream().filter(x-> !x.equals(this.cookingPair)).toList();
        JsonObject secondPair = pairs.get(0).toJson();
        JsonObject thirdPair = pairs.get(1).toJson();
        groupJson.put("cookingPair",cookingPair);
        groupJson.put("secondPair",secondPair);
        groupJson.put("thirdPair",thirdPair);
        return groupJson;
    }

    private void calculateMainFoodPreference(Pair pair1, Pair pair2, Pair pair3) {
        // if they have the same Food preferences
        if (pair1.getMainFoodPreference() == pair2.getMainFoodPreference() && pair2.getMainFoodPreference() == pair3.getMainFoodPreference()) {
            this.mainFoodPreference = pair1.getMainFoodPreference() == FoodPreference.none ? FoodPreference.meat : pair1.getMainFoodPreference();

        }
        // (1, 2) meat & none -> meat
        else if ((pair1.getMainFoodPreference() == FoodPreference.none ||
                pair1.getMainFoodPreference() == FoodPreference.meat) &&
                (pair2.getMainFoodPreference() == FoodPreference.none ||
                        pair2.getMainFoodPreference() == FoodPreference.meat) && (pair3.getMainFoodPreference() == FoodPreference.none ||
                pair3.getMainFoodPreference() == FoodPreference.meat)) {
            this.mainFoodPreference = FoodPreference.meat;
        }
        // (1) veggie|vegan & (2) veggie|vegan -> vegan
        else if ((pair1.getMainFoodPreference() == FoodPreference.veggie ||
                pair1.getMainFoodPreference() == FoodPreference.vegan) &&
                (pair2.getMainFoodPreference() == FoodPreference.vegan ||
                        pair2.getMainFoodPreference() == FoodPreference.veggie)
                &&
                (pair3.getMainFoodPreference() == FoodPreference.vegan ||
                        pair3.getMainFoodPreference() == FoodPreference.veggie)) {
            this.mainFoodPreference = FoodPreference.vegan;
        }
        // (1) meat|none & (2) Veggie|Veganer -> Veganer
        else if ((pair1.getMainFoodPreference() == FoodPreference.meat ||
                pair1.getMainFoodPreference() == FoodPreference.none)
                && (pair2.getMainFoodPreference() == FoodPreference.vegan ||
                pair2.getMainFoodPreference() == FoodPreference.veggie)
                && (pair3.getMainFoodPreference() == FoodPreference.vegan ||
                pair3.getMainFoodPreference() == FoodPreference.veggie)) {
            this.mainFoodPreference = pair2.getMainFoodPreference();
        }
        // (2) meat|none && (1) vegan|vegan -> veggie | vwgan
        else if ((pair2.getMainFoodPreference() == FoodPreference.meat ||
                pair2.getMainFoodPreference() == FoodPreference.none)
                && (pair1.getMainFoodPreference() == FoodPreference.vegan ||
                pair1.getMainFoodPreference() == FoodPreference.veggie)
                && (pair3.getMainFoodPreference() == FoodPreference.vegan ||
                pair3.getMainFoodPreference() == FoodPreference.veggie)) {

            this.mainFoodPreference = pair1.getMainFoodPreference();
        } else if ((pair3.getMainFoodPreference() == FoodPreference.meat ||
                pair3.getMainFoodPreference() == FoodPreference.none)
                && (pair1.getMainFoodPreference() == FoodPreference.vegan ||
                pair1.getMainFoodPreference() == FoodPreference.veggie)
                && (pair2.getMainFoodPreference() == FoodPreference.vegan ||
                pair2.getMainFoodPreference() == FoodPreference.veggie)) {
            this.mainFoodPreference = pair1.getMainFoodPreference();
        }
    }

    @Override
    public String toString() {
        return "Group {" +
                "did pair1 cooked " + pair1.isHaveCooked() + "did pair2 cooked " + pair2.isHaveCooked() + "did pair3 cooked " + pair3.isHaveCooked() +
                "pair1=" + " Pair1_MainFoodPreferece " + pair1.getMainFoodPreference() +
                " Pair2_MainFoodPreferece " + pair2.getMainFoodPreference() +
                 " Pair3_MainFoodPreferece " + pair3.getMainFoodPreference() +
                "\n" + pair1 + "\n" + "pair2=" + "\n" + pair2 + "\n" + "pair3=" + "\n" + pair3 + "\n" + "#".repeat(10)
                ;
    }

    @Override
    public boolean equals(Object o) {
        boolean pairing1 = false;
        boolean pairing2 = false;
        boolean pairing3 = false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        pairing1 = pair1.equals(group.pair1) || pair1.equals(group.pair2) || pair1.equals(group.pair3);
        pairing2 = pair2.equals(group.pair1) || pair2.equals(group.pair2) || pair2.equals(group.pair3);
        pairing3 = pair3.equals(group.pair1) || pair3.equals(group.pair2) || pair3.equals(group.pair3);
        return pairing1 & pairing2 & pairing3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair1, pair2, pair3);
    }

    public Pair getPair1() {
        return pair1;
    }

    public Pair getPair2() {
        return pair2;
    }

    public Pair getPair3() {
        return pair3;
    }

    public double getSexDeviation() {
        return sexDeviation;
    }

    public FoodPreference getMainFoodPreference() {
        return mainFoodPreference;
    }

    public int getAgeDifference() {
        return ageDifference;
    }

    public int getPreferenceDeviation() {
        return preferenceDeviation;
    }

    private int calculateAgeDifference() {
        return (this.pair1.getAgeDifference() + this.pair2.getAgeDifference() + this.pair3.getAgeDifference()) / 3;
    }

    private int calculatePreferenceDeviation() {
        return (this.pair1.getPreferenceDeviation() + this.pair2.getPreferenceDeviation() + this.pair3.getPreferenceDeviation()) / 3;
    }
    private double calculateSexDeviation(){
        return Math.abs(((pair1.getSexDeviation() + pair2.getSexDeviation() + pair3.getSexDeviation()) / 3) - 0.5);
    }

    public Course getCourse() {
        return course;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }


    public void setPairsWhoMetInStarter(Group group) {
        Pair pair1 = group.pair1;
        Pair pair2 = group.pair2;
        Pair pair3 = group.pair3;
            pair1.getMetPairsInStarter().add(pair2);
            pair1.getMetPairsInStarter().add(pair3);
            pair2.getMetPairsInStarter().add(pair1);
            pair2.getMetPairsInStarter().add(pair3);
            pair3.getMetPairsInStarter().add(pair1);
            pair3.getMetPairsInStarter().add(pair2);
    }
    public void setPairsWhoMetInMainDish(Group group) {
        Pair pair1 = group.pair1;
        Pair pair2 = group.pair2;
        Pair pair3 = group.pair3;
        pair1.getMetPairsInMainDish().add(pair2);
        pair1.getMetPairsInMainDish().add(pair3);
        pair2.getMetPairsInMainDish().add(pair1);
        pair2.getMetPairsInMainDish().add(pair3);
        pair3.getMetPairsInMainDish().add(pair1);
        pair3.getMetPairsInMainDish().add(pair2);
    }
    public void setPairsWhoMetInDessert(Group group) {
        Pair pair1 = group.pair1;
        Pair pair2 = group.pair2;
        Pair pair3 = group.pair3;
        pair1.getMetPairsInDessert().add(pair2);
        pair1.getMetPairsInDessert().add(pair3);
        pair2.getMetPairsInDessert().add(pair1);
        pair2.getMetPairsInDessert().add(pair3);
        pair3.getMetPairsInDessert().add(pair1);
        pair3.getMetPairsInDessert().add(pair2);
    }

    public List<Pair> getPairsInGroup() {
        List<Pair> list = new ArrayList<>();
        list.add(pair1);
        list.add(pair2);
        list.add(pair3);
        return list;
    }

    public void printPairsWhoCooked() {
        StringBuilder builder = new StringBuilder();
        Course course1 = pair1.getCourse() != null ? pair1.getCourse() : null;
        Course course2 = pair2.getCourse() != null ? pair2.getCourse() : null;
        Course course3 = pair3.getCourse() != null ? pair3.getCourse() : null;
        builder.append("Pair1: " + pair1.isHaveCooked() + " , when?: " + course1 + ", Pair2: " + pair2.isHaveCooked() + " , when?: " + course2 + ", Pair3: " + pair3.isHaveCooked() + " , when?: " + course3);
        System.out.println(builder);
    }

    public static void main(String[] args) {
        List<Participant> temp = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            temp.add(new Participant(Integer.toString(i), "peter", 2, HasKitchen.no, FoodPreference.vegan, Sex.female, null ));
        }
        Pair pair1 = new Pair(temp.get(0), temp.get(1), true);
        Pair pair2 = new Pair(temp.get(2), temp.get(3), true);
        Pair pair3 = new Pair(temp.get(4), temp.get(5), true);
        Pair pair4 = new Pair(temp.get(1), temp.get(5), true);
        Group group1 = new Group(pair1, pair2, pair3, Course.main);
        Group group2 = new Group(pair2, pair1, pair3, Course.main);

        Group group3 = new Group(pair2, pair1, pair4, Course.main);
        Integer temp1 = (Integer) group1.hashCode();
        Integer temp2 = (Integer) group2.hashCode();
        System.out.println(temp1.equals(temp2));


        //System.out.println(group1.equals(group2));
        //System.out.println(group1.equals(group3));
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness(){
        return this.fitness;
    }

    @Override
    public int compareTo(Group o) {
        if (this.getFitness() < o.getFitness()) {
            return  1;
        } else if (this.getFitness() > o.getFitness()) {
            return -1;
        }
        return 0;
    }

    public void setCookingPair(Pair cookingPair) {
        this.cookingPair = cookingPair;
    }

    public Pair getCookingPair() {
        return cookingPair;
    }
}

