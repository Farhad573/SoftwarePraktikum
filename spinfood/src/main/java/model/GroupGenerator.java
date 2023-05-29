package model;

import java.util.List;

public class GroupGenerator extends ParticipantManager {


    public List<Group> generateGroup(List<Pair> pairs){

        return null;
    }

    public boolean checkGroupFoodPreference(Pair pair1, Pair pair2, Pair pair3){
        FoodPreference pref1 = pair1.getMainFoodPreference();
        FoodPreference pref2 = pair2.getMainFoodPreference();
        FoodPreference pref3 = pair3.getMainFoodPreference();

        if (pref1 == pref2 && pref2 == pref3) {
            // Same food preference, assign higher fitness
            return true;
        } else if ((pref1 == FoodPreference.meat && pref2 == FoodPreference.none) || (pref2 == FoodPreference.meat && pref1 == FoodPreference.none)) {
            // meat && none
            return true;
        } else if ((pref1 == FoodPreference.veggie && pref2 == FoodPreference.vegan) || (pref2 == FoodPreference.veggie && pref1 == FoodPreference.vegan)) {
            // veggie & vegan
            return true;
        } else if (((pref1 == FoodPreference.veggie || pref1 == FoodPreference.vegan) && (pref2 == FoodPreference.none)) ||
                ((pref2 == FoodPreference.veggie || pref2 == FoodPreference.vegan) && (pref1 == FoodPreference.none))) {
            // none & veggie | vegan
            return true;
        } else {
            // meat & veggie | vegan
            return false;
        }
    }






}
