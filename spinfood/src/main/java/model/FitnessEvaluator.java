package model;

public class FitnessEvaluator {

    public static double evaluateFitness(Pair pair) {
        double fitness = 0.0;

        fitness += calculateFoodPreferenceFitness(pair);
        fitness += calculateAgeDifferenceFitness(pair);
        fitness += calculateGenderDiversityFitness(pair);
        fitness += calculateKitchenFitness(pair);
        fitness += calculatePreferenceDeviationFitness(pair);

        return fitness;
    }

    private static double calculateFoodPreferenceFitness(Pair pair) {
        FoodPreference pref1 = pair.getPerson1().getFoodPreference();
        FoodPreference pref2 = pair.getPerson2().getFoodPreference();

        if (pref1 == pref2) {
            // Same food preference, assign higher fitness
            return 1.0;
        } else if ((pref1 == FoodPreference.meat && pref2 == FoodPreference.none) || (pref2 == FoodPreference.meat && pref1 == FoodPreference.none)) {
            // meat && none
            return 1.0;
        } else if ((pref1 == FoodPreference.veggie && pref2 == FoodPreference.vegan) || (pref2 == FoodPreference.veggie && pref1 == FoodPreference.vegan)) {
            // veggie & vegan
            return 1.0;
        } else if (((pref1 == FoodPreference.veggie || pref1 == FoodPreference.vegan) && (pref2 == FoodPreference.none)) ||
                ((pref2 == FoodPreference.veggie || pref2 == FoodPreference.vegan) && (pref1 == FoodPreference.none))) {
            // none & veggie | vegan
            return 0.8;
        } else {
            // meat & veggie | vegan
            return -0.5;
        }
    }
    public static boolean checkFoodPreferenceFitness(Pair pair) {
        FoodPreference pref1 = pair.getPerson1().getFoodPreference();
        FoodPreference pref2 = pair.getPerson2().getFoodPreference();

        if (pref1 == pref2) {
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

    private static double calculateAgeDifferenceFitness(Pair pair) {
        double ageDiff = Math.abs(pair.getPerson1().getAgeRange() - pair.getPerson2().getAgeRange());
        return 1.0 - ageDiff / 100.0; // Normalize age difference to a value between 0 and 1
    }

    private static double calculateGenderDiversityFitness(Pair pair) {
        if (pair.getPerson1().getSex() != pair.getPerson2().getSex()) {
            return 1.0;
        }
        return 0.0;
    }

    private static double calculateKitchenFitness(Pair pair) {
        HasKitchen kitchen1 = pair.getPerson1().getHasKitchen();
        HasKitchen kitchen2 = pair.getPerson2().getHasKitchen();

        if (((kitchen1 == HasKitchen.yes || kitchen1 == HasKitchen.maybe) && kitchen2 == HasKitchen.no) ||
                ((kitchen2 == HasKitchen.yes || kitchen2 == HasKitchen.maybe) && kitchen1 == HasKitchen.no)) {
            return 1.0;
        } else if ((kitchen1 == HasKitchen.yes || kitchen1 == HasKitchen.maybe) && (kitchen2 == HasKitchen.yes || kitchen2 == HasKitchen.maybe)) {
            return 0.5;
        } else {
            return -2.0;
        }
    }
    public static boolean checkKitchenFitness(Pair pair) {
        HasKitchen kitchen1 = pair.getPerson1().getHasKitchen();
        HasKitchen kitchen2 = pair.getPerson2().getHasKitchen();

        if (((kitchen1 == HasKitchen.yes || kitchen1 == HasKitchen.maybe) && kitchen2 == HasKitchen.no) ||
                ((kitchen2 == HasKitchen.yes || kitchen2 == HasKitchen.maybe) && kitchen1 == HasKitchen.no)) {
            return true;
        } else if ((kitchen1 == HasKitchen.yes || kitchen1 == HasKitchen.maybe) && (kitchen2 == HasKitchen.yes || kitchen2 == HasKitchen.maybe)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkKitchenCount(Pair pair){
        return pair.getPerson1().getKitchenCount() < 3 && pair.getPerson2().getKitchenCount() < 3;
    }

    private static double calculatePreferenceDeviationFitness(Pair pair) {
        int preferenceDeviation = pair.getPreferenceDeviation();

        if (preferenceDeviation == 0) {
            return 1.0;
        } else if (preferenceDeviation == 1) {
            return 0.5;
        } else {
            return -0.5;
        }
    }


}