package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    Pair pair1;
    Pair pair2;
    Pair pair3;


    private FoodPreference mainFoodPreference;
    private final int ageDifference;

    private final int preferenceDeviation;
    private double sexDeviation;

    public Group(Pair pair1, Pair pair2, Pair pair3) {
        this.pair1 = pair1;
        this.pair2 = pair2;
        this.pair3 = pair3;
        this.ageDifference = calculateAgeDifference();
        this.preferenceDeviation = calculatePreferenceDeviation();
        calculateMainFoodPreference(pair1, pair2, pair3);
        this.sexDeviation = calculateSexDeviation();
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
                        pair2.getMainFoodPreference() == FoodPreference.veggie)) {
            this.mainFoodPreference = FoodPreference.vegan;
        }
        // (1) meat|none & (2) Veggie|Veganer -> Veganer
        else if ((pair1.getMainFoodPreference() == FoodPreference.meat ||
                pair1.getMainFoodPreference() == FoodPreference.none)
                && (pair2.getMainFoodPreference() == FoodPreference.vegan ||
                pair2.getMainFoodPreference() == FoodPreference.veggie)) {
            this.mainFoodPreference = pair2.getMainFoodPreference();
        }
        // (2) meat|none && (1) vegan|vegan -> veggie | vwgan
        else if ((pair2.getMainFoodPreference() == FoodPreference.meat ||
                pair2.getMainFoodPreference() == FoodPreference.none)
                && (pair1.getMainFoodPreference() == FoodPreference.vegan ||
                pair1.getMainFoodPreference() == FoodPreference.veggie)) {

            this.mainFoodPreference = pair1.getMainFoodPreference();
        }
    }

    @Override
    public String toString() {
        return "Group {" +
                "did pair1 cooked " + pair1.isHaveCooked() + "did pair2 cooked " + pair2.isHaveCooked() + "did pair3 cooked " + pair3.isHaveCooked() +
                "pair1=" + "\n" + pair1 + "\n" + "pair2=" + "\n" + pair2 + "\n" + "pair3=" + "\n" + pair3 + "\n" + "#".repeat(10)
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return pair1.equals(group.pair1) && pair2.equals(group.pair2) && pair3.equals(group.pair3);
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
        return (pair1.getSexDeviation() + pair2.getSexDeviation() + pair3.getSexDeviation()) / 3;
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
}

