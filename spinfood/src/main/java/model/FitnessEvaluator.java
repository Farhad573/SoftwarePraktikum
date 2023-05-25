package model;

public class FitnessEvaluator {
    public static double evaluateFitness(Pair pair) {
        double fitness = 0.0;

        // Criterion 1: Food preferences
        FoodPreference pref1 = pair.getPerson1().getFoodPreference();
        FoodPreference pref2 = pair.getPerson2().getFoodPreference();
        HasKitchen kitchen1 = pair.getPerson1().getHasKitchen();
        HasKitchen kitchen2 = pair.getPerson2().getHasKitchen();
        int preferenceDeviation = pair.getPreferenceDeviation();

        if (pref1 == pref2) {
            // Same food preference, assign higher fitness
            fitness += 1.0;
        } else if ((pref1 == FoodPreference.meat && pref2 == FoodPreference.none ) || (pref2 == FoodPreference.meat && pref1 == FoodPreference.none)) {
            // meat && none
            fitness += 1.0;
        } else if ((pref1 == FoodPreference.veggie && pref2 == FoodPreference.vegan ) || (pref2 == FoodPreference.veggie && pref1 == FoodPreference.vegan)) {
            //veggie & vegan
            fitness += 1.0;
        } else if (((pref1 == FoodPreference.veggie || pref1 == FoodPreference.vegan) && (pref2 == FoodPreference.none)) ||
                ((pref2 == FoodPreference.veggie || pref2 == FoodPreference.vegan) && (pref1 == FoodPreference.none))) {
            // none & veggie | vegan
            fitness += 0.8;
        }else {
            // meat & veggie | vegan
            fitness -= 0.5;
        }

        // Criterion 2: Age difference
        double ageDiff = Math.abs(pair.getPerson1().getAgeRange() - pair.getPerson2().getAgeRange());
        fitness += 1.0 - ageDiff / 100.0;  // Normalize age difference to a value between 0 and 1

        // Criterion 3: Gender diversity
        if (pair.getPerson1().getSex() != pair.getPerson2().getSex()) {
            fitness += 1.0;
        }
        // criterion 4 : if one of them has kitchen
        if(((kitchen1 == HasKitchen.yes || kitchen1 == HasKitchen.maybe) && kitchen2 == HasKitchen.no) ||
                ((kitchen2 == HasKitchen.yes || kitchen2 == HasKitchen.maybe) && kitchen1 == HasKitchen.no)){
            fitness += 1.0;
        }
        else if ((kitchen1 == HasKitchen.yes || kitchen1 == HasKitchen.maybe) && (kitchen2 == HasKitchen.yes || kitchen2 == HasKitchen.maybe))
                {
            fitness += 0.5;
        } else if (kitchen1 == HasKitchen.no && kitchen2 == HasKitchen.no) {
            fitness -= 0.5;
        }
        // criterion 5 : Preference Deviation
        if(preferenceDeviation == 0){
            fitness += 1.0;
        } else if (preferenceDeviation == 1) {
            fitness += 0.5;
        } else if (preferenceDeviation == 2) {
            fitness -= 0.5;
        }

        // Criterion 6: Travel distance between courses (Not implemented here)

        return fitness;
    }
}
