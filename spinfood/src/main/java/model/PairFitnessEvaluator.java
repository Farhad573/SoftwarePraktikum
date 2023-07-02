package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PairFitnessEvaluator {

    public static double evaluateFitness(Pair pair, Map<Kitchen, List<Pair>> map,int[] numbers) {
        double foodPreferenceDeviationWeight = calculateWeight(numbers[0]);
        double ageDifferenceWeight = calculateWeight(numbers[1]);
        double genderDiversityWeight = calculateWeight(numbers[2]);
        double minimumSuccessor = calculateWeight(numbers[3]);
        double fitness = 0.0;

        fitness += calculateFoodPreferenceFitness(pair) ;
        fitness += calculateAgeDifferenceFitness(pair) * ageDifferenceWeight ;
        fitness += calculateGenderDiversityFitness(pair) * genderDiversityWeight ;
        fitness += calculateKitchenFitness(pair) ;
        fitness += calculatePreferenceDeviationFitness(pair) * foodPreferenceDeviationWeight;

        fitness += calculateWGCountFitness(pair,map);

        return fitness;
    }
    public static double calculateWeight(int num){
        switch (num){
            case 1: return 10;
            case 2: return 8;
            case 3: return 6;
            case 4: return 4;
            default:return 0;
        }
    }
    private static double calculateWGCountFitness(Pair pair,Map<Kitchen, List<Pair>> map){
        Kitchen kitchen = pair.getKitchen();
        int kitchenCount = map.getOrDefault(kitchen,new ArrayList<>()).size();
        if(kitchenCount >= 3){
            return - 1000.0;
        }else
            return 1.0;
    }

    private static double calculateFoodPreferenceFitness(Pair pair) {
        FoodPreference pref1 = pair.getPerson1().getFoodPreference();
        FoodPreference pref2 = pair.getPerson2().getFoodPreference();

        if (pref1 == pref2) {
            // Same food preference, assign higher fitness
            return 1.0;
        } else if ((pref1 == FoodPreference.meat && pref2 == FoodPreference.none) ||
                (pref2 == FoodPreference.meat && pref1 == FoodPreference.none)) {
            // meat && none
            return 1.0;
        } else if ((pref1 == FoodPreference.veggie && pref2 == FoodPreference.vegan) ||
                (pref2 == FoodPreference.veggie && pref1 == FoodPreference.vegan)) {
            // veggie & vegan
            return 1.0;
        } else if (((pref1 == FoodPreference.veggie ||
                pref1 == FoodPreference.vegan) &&
                (pref2 == FoodPreference.none)) ||
                ((pref2 == FoodPreference.veggie ||
                        pref2 == FoodPreference.vegan) && (pref1 == FoodPreference.none))) {
            // none & veggie | vegan
            return 0.8;
        } else {
            // meat & veggie | vegan
            return -1000.0;
        }
    }

    public static boolean checkFoodPreferenceFitness(Pair pair) {
        FoodPreference pref1 = pair.getPerson1().getFoodPreference();
        FoodPreference pref2 = pair.getPerson2().getFoodPreference();

        if (pref1 == pref2) {
            // Same food preference, assign higher fitness
            return true;
        } else if ((pref1 == FoodPreference.meat && pref2 == FoodPreference.none) ||
                (pref2 == FoodPreference.meat && pref1 == FoodPreference.none)) {
            // meat && none
            return true;
        } else if ((pref1 == FoodPreference.veggie && pref2 == FoodPreference.vegan) ||
                (pref2 == FoodPreference.veggie && pref1 == FoodPreference.vegan)) {
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

            return -1000.0;
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
            return -1000.0;
        }
    }


    public static boolean checkSexDifference(Pair pair){
        return pair.getSexDeviation() >= 0.5;
    }



}



