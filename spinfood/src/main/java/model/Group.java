package model;

import java.util.Objects;

public class Group {
    Pair pair1;
    Pair pair2;
    Pair pair3;

    private FoodPreference mainFoodPreference;
    private final int ageDifference;

    private final int preferenceDeviation;

    public Group(Pair pair1, Pair pair2, Pair pair3) {
        this.pair1 = pair1;
        this.pair2 = pair2;
        this.pair3 = pair3;
        this.ageDifference = calculateAgeDifference();
        this.preferenceDeviation = calculatePreferenceDeviation();
        calculateMainFoodPreference(pair1,pair2,pair3);
    }

    private void calculateMainFoodPreference(Pair pair1, Pair pair2,Pair pair3) {
        // if they have the same Food preferences
        if(pair1.getMainFoodPreference() == pair2.getMainFoodPreference() && pair2.getMainFoodPreference() == pair3.getMainFoodPreference()){
            this.mainFoodPreference = pair1.getMainFoodPreference() == FoodPreference.none? FoodPreference.meat : pair1.getMainFoodPreference();

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
        else if((pair1.getMainFoodPreference() == FoodPreference.meat ||
                pair1.getMainFoodPreference() == FoodPreference.none)
                && (pair2.getMainFoodPreference() == FoodPreference.vegan ||
                pair2.getMainFoodPreference() == FoodPreference.veggie)) {
            this.mainFoodPreference = pair2.getMainFoodPreference();
        }
        // (2) meat|none && (1) vegan|vegan -> veggie | vwgan
        else if((pair2.getMainFoodPreference()==FoodPreference.meat ||
                pair2.getMainFoodPreference() == FoodPreference.none)
                &&(pair1.getMainFoodPreference() == FoodPreference.vegan ||
                pair1.getMainFoodPreference() == FoodPreference.veggie)) {

            this.mainFoodPreference = pair1.getMainFoodPreference();
        }
    }
    @Override
    public String toString() {
        return "Group {" +
                "did pair1 cooked " + pair1.isHaveCooked() + "did pair2 cooked "  + pair2.isHaveCooked() + "did pair3 cooked " + pair3.isHaveCooked() +
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

    public FoodPreference getMainFoodPreference() {
        return mainFoodPreference;
    }

    public int getAgeDifference() {
        return ageDifference;
    }

    public int getPreferenceDeviation() {
        return preferenceDeviation;
    }

    private int calculateAgeDifference(){
        return (this.pair1.getAgeDifference() + this.pair2.getAgeDifference() + this.pair3.getAgeDifference()) / 3;
    }

    private int calculatePreferenceDeviation(){
        return (this.pair1.getPreferenceDeviation() + this.pair2.getPreferenceDeviation() + this.pair3.getPreferenceDeviation()) / 3;
    }

}
