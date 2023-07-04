package model;

import controller.Distance;

import java.util.List;
import java.util.Map;

/**
 * The GroupFitnessEvaluator class provides methods to evaluate the fitness of groups for main dishes and desserts.
 */
public class GroupFitnessEvaluator {

    public static double evaluateFitnessForStarter(Group group, int[] numbers) {
        // Calculate fitness based on different criteria
        double foodPreferenceDeviationWeight = calculateWeight(numbers[0]);
        double ageDifferenceWeight = calculateWeight(numbers[1]);
        double genderDiversityWeight = calculateWeight(numbers[2]);
        double shortestPathWeight = calculateWeight(numbers[3]);
        double minimumSuccessor = calculateWeight(numbers[4]);
        double fitness = 0.0;
        fitness += calculateAgeDifferenceFitness(group) * ageDifferenceWeight ;
        fitness += calculatePreferenceDeviationFitness(group) * foodPreferenceDeviationWeight ;
        fitness += calculateGenderDiversityFitness(group) * genderDiversityWeight;
        return fitness;
    }
    public static double calculateGenderDiversityFitness(Group group){
        double genderDiversity = group.getSexDeviation();
        if(genderDiversity == 0){
            return 1.0;
        } else {
            return 0.5;
        }
    }

    /**
     * Evaluates the fitness of a group for the main dish.
     *
     * @param group    The group to evaluate.
     * @param numbers
     * @param location
     * @return The fitness score for the group.
     */
    public static double evaluateFitnessForMainDish(Group group, int[] numbers, Map<Pair,Location> map, List<Pair> pairs, Location location) {
        // Calculate fitness based on different criteria
        double foodPreferenceDeviationWeight = calculateWeight(numbers[0]);
        double ageDifferenceWeight = calculateWeight(numbers[1]);
        double genderDiversityWeight = calculateWeight(numbers[2]);
        double shortestPathWeight = calculateWeight(numbers[3]);
        double minimumSuccessor = calculateWeight(numbers[4]);
        double fitness = 0.0;
        fitness += didPairsMeet(group.pair1, group.pair2, group.pair3);
        fitness += checkIfAllPairsDidntCook(group.pair1, group.pair2, group.pair3);
        fitness += checkIfOneOfPairsHaveCooked(group.pair1, group.pair2, group.pair3);
        fitness += calculateAgeDifferenceFitness(group) * ageDifferenceWeight ;
        fitness += calculateGenderDiversityFitness(group) * genderDiversityWeight ;
        fitness += calculatePreferenceDeviationFitness(group) * foodPreferenceDeviationWeight ;
        fitness += calculateLengthPathFitnessForMainDish(group,map,pairs,location) * shortestPathWeight ;
        return fitness;
    }
    public static double calculateLengthPathFitnessForMainDish(Group group, Map<Pair,Location> map, List<Pair> pairs, Location partyLocation){
        Pair pair1 = group.pair1;
        Pair pair2 = group.pair2;
        Pair pair3 = group.pair3;

        Location location1 = map.get(pair1);
        Location location2 = map.get(pair2);
        Location location3 = map.get(pair3);
        List<Pair> pairsWhoDidntCook = group.getPairsInGroup().stream().filter(x -> !x.isHaveCooked()).toList();
        Pair pairWhoDidntCook1 = pairsWhoDidntCook.get(0);
        Pair pairWhoDidntCook2 = pairsWhoDidntCook.get(1);
        Location newKitchen1 = pairWhoDidntCook1.getKitchen().getKitchen_location();
        Location newKitchen2 = pairWhoDidntCook2.getKitchen().getKitchen_location();

        double distance1 = Distance.calculateDistance(location1.getLatitude(),location1.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());
        double distance2 = Distance.calculateDistance(location2.getLatitude(),location2.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());
        double distance3 = Distance.calculateDistance(location3.getLatitude(),location3.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());
        double distance4 = Distance.calculateDistance(newKitchen1.getLatitude(),newKitchen1.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());
        double distance5 = Distance.calculateDistance(newKitchen2.getLatitude(),newKitchen2.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());

        boolean isKitchen1Nearer = (distance4 < distance1) && (distance4 < distance2) && (distance4 < distance3);
        boolean isKitchen2Nearer = (distance5 < distance1) && (distance5 < distance2) && (distance5 < distance3);
        if(isKitchen1Nearer && isKitchen2Nearer) {
            return 1.0;
        }
        return 0.5;
    }


    /**
     * Evaluates the fitness of a group for the dessert.
     *
     * @param group    The group to evaluate.
     * @param numbers
     * @param location
     * @return The fitness score for the group.
     */
    public static double evaluateFitnessForDessert(Group group, int[] numbers,Map<Pair,Location> map, List<Pair> pairs, Location location) {
        // Calculate fitness based on different criteria
        double foodPreferenceDeviationWeight = calculateWeight(numbers[0]);
        double ageDifferenceWeight = calculateWeight(numbers[1]);
        double genderDiversityWeight = calculateWeight(numbers[2]);
        double shortestPathWeight = calculateWeight(numbers[3]);
        double minimumSuccessor = calculateWeight(numbers[4]);
        double fitness = 0.0;
        fitness += didPairsMeetInMainDish(group.pair1, group.pair2, group.pair3);
        fitness += checkIfAllPairsDidntCook(group.pair1, group.pair2, group.pair3);
        fitness += checkIfTwoOfPairsHaveCooked(group.pair1, group.pair2, group.pair3);
        fitness += calculateAgeDifferenceFitness(group) * ageDifferenceWeight ;
        fitness += calculatePreferenceDeviationFitness(group) * foodPreferenceDeviationWeight ;
        fitness += calculateGenderDiversityFitness(group) * genderDiversityWeight ;
        fitness += calculateLengthPathFitnessForDessert(group,map,pairs,location) * shortestPathWeight ;
        return fitness;
    }
    public static double calculateLengthPathFitnessForDessert(Group group, Map<Pair,Location> map, List<Pair> pairs, Location partyLocation){
        Pair pair1 = group.pair1;
        Pair pair2 = group.pair2;
        Pair pair3 = group.pair3;

        Location location1 = map.get(pair1);
        Location location2 = map.get(pair2);
        Location location3 = map.get(pair3);
        if(location1 == null || location2 == null || location3 == null){
            return - 1000.0;
        }
        List<Pair> pairsWhoDidntCook = group.getPairsInGroup().stream().filter(x -> !x.isHaveCooked()).toList();
        Pair pairWhoDidntCook;
        if(pairsWhoDidntCook.size() == 1){
            pairWhoDidntCook = pairsWhoDidntCook.get(0);
        }else {
            return -1000.0;
        }
        Location newKitchen = pairWhoDidntCook.getKitchen().getKitchen_location();

        double distance1 = Distance.calculateDistance(location1.getLatitude(),location1.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());
        double distance2 = Distance.calculateDistance(location2.getLatitude(),location2.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());
        double distance3 = Distance.calculateDistance(location3.getLatitude(),location3.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());
        double distance4 = Distance.calculateDistance(newKitchen.getLatitude(),newKitchen.getLongitude(),partyLocation.getLatitude(),partyLocation.getLongitude());

        boolean isKitchen1Nearer = (distance4 < distance1) && (distance4 < distance2) && (distance4 < distance3);
        if(isKitchen1Nearer) {
            return 1.0;
        }
        return 0.5;
    }
    public static double calculateWeight(int num){
        switch (num){
            case 1: return 10;
            case 2: return 8;
            case 3: return 6;
            case 4: return 4;
            default:return 2;
        }
    }

    /**
     * Checks if at least one pair in the group has cooked.
     *
     * @param pair1 The first pair.
     * @param pair2 The second pair.
     * @param pair3 The third pair.
     * @return 1.0 if at least one pair has cooked, -8.0 otherwise.
     */
    public static double checkIfOneOfPairsHaveCooked(Pair pair1, Pair pair2, Pair pair3) {
        if ((pair1.isHaveCooked() && !pair2.isHaveCooked() && !pair3.isHaveCooked()) ||
                (!pair1.isHaveCooked() && pair3.isHaveCooked() && !pair2.isHaveCooked()) ||
                (pair2.isHaveCooked() && !pair3.isHaveCooked() && !pair1.isHaveCooked())) {
            return 1.0;
        }
        return -1000.0;
    }

    /**
     * Checks if two pairs in the group have cooked.
     *
     * @param pair1 The first pair.
     * @param pair2 The second pair.
     * @param pair3 The third pair.
     * @return 1.0 if two pairs have cooked, -8.0 otherwise.
     */
    private static double checkIfTwoOfPairsHaveCooked(Pair pair1, Pair pair2, Pair pair3) {
        if ((pair1.isHaveCooked() && pair2.isHaveCooked() && !pair3.isHaveCooked()) ||
                (pair1.isHaveCooked() && pair3.isHaveCooked() && !pair2.isHaveCooked()) ||
                (pair2.isHaveCooked() && pair3.isHaveCooked() && !pair1.isHaveCooked() )) {
            return 1.0;
        } else {
            return -1000.0;
        }
    }

    /**
     * Checks if all pairs in the group have not cooked.
     *
     * @param pair1 The first pair.
     * @param pair2 The second pair.
     * @param pair3 The third pair.
     * @return -8.0 if all pairs haven't cooked, 1.0 otherwise.
     */
    public static double checkIfAllPairsDidntCook(Pair pair1, Pair pair2, Pair pair3) {
        if (!pair1.isHaveCooked() && !pair2.isHaveCooked() && !pair3.isHaveCooked()) {
            return -1000.0;
        } else {
            return 1.0;
        }
    }

    /**
     * Checks if the pairs in the group have met each other before.
     *
     * @param pair1 The first pair.
     * @param pair2 The second pair.
     * @param pair3 The third pair.
     * @return 1.0 if pairs haven't met, -8.0 otherwise.
     */
    public static double didPairsMeet(Pair pair1, Pair pair2, Pair pair3) {
        if (!pair1.getMetPairsInStarter().contains(pair2) && !pair1.getMetPairsInStarter().contains(pair3) &&
                !pair2.getMetPairsInStarter().contains(pair1) && !pair2.getMetPairsInStarter().contains(pair3) &&
                !pair3.getMetPairsInStarter().contains(pair1) && !pair3.getMetPairsInStarter().contains(pair2)) {
            return 1.0;
        } else {
            return -1000.0;
        }
    }
    public static double didPairsMeetInMainDish(Pair pair1, Pair pair2, Pair pair3) {
        if (!pair1.getMetPairsInStarter().contains(pair2) && !pair1.getMetPairsInStarter().contains(pair3) &&
                !pair1.getMetPairsInMainDish().contains(pair2) && !pair1.getMetPairsInMainDish().contains(pair3) &&
                !pair2.getMetPairsInStarter().contains(pair1) && !pair2.getMetPairsInStarter().contains(pair3) &&
                !pair2.getMetPairsInMainDish().contains(pair1) && !pair2.getMetPairsInMainDish().contains(pair3) &&
                !pair3.getMetPairsInStarter().contains(pair1) && !pair3.getMetPairsInStarter().contains(pair2) &&
                !pair3.getMetPairsInMainDish().contains(pair1) && !pair3.getMetPairsInMainDish().contains(pair2) ) {
            return 1.0;
        } else {
            return -1000.0;
        }
    }

    /**
     * Calculates the fitness based on the age difference within the group.
     *
     * @param pair The group pair.
     * @return The fitness score based on the age difference.
     */
    private static double calculateAgeDifferenceFitness(Group pair) {
        double ageDiff = (pair.pair1.getAgeDifference() + pair.pair2.getAgeDifference() + pair.pair3.getAgeDifference()) / 3;
        return 1.0 - ageDiff / 100.0; // Normalize age difference to a value between 0 and 1
    }

    /**
     * Calculates the fitness based on the kitchen availability within a pair.
     *
     * @param pair The pair to evaluate.
     * @return The fitness score based on the kitchen availability.
     */
    private static double calculateKitchenFitness(Pair pair) {
        HasKitchen kitchen1 = pair.getPerson1().getHasKitchen();
        HasKitchen kitchen2 = pair.getPerson2().getHasKitchen();

        if (((kitchen1 == HasKitchen.yes || kitchen1 == HasKitchen.maybe) && kitchen2 == HasKitchen.no) ||
                ((kitchen2 == HasKitchen.yes || kitchen2 == HasKitchen.maybe) && kitchen1 == HasKitchen.no)) {
            return 1.0;
        } else if ((kitchen1 == HasKitchen.yes || kitchen1 == HasKitchen.maybe) && (kitchen2 == HasKitchen.yes || kitchen2 == HasKitchen.maybe)) {
            return 0.5;
        } else {
            return -0.5;
        }
    }

    /**
     * Checks if the kitchen fitness condition is satisfied for a pair.
     *
     * @param pair The pair to evaluate.
     * @return True if the kitchen fitness condition is satisfied, false otherwise.
     */
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

    /**
     * Checks if the kitchen count condition is satisfied for a pair.
     *
     * @param pair The pair to evaluate.
     * @return True if the kitchen count condition is satisfied, false otherwise.
     */
    public static boolean checkKitchenCount(Pair pair) {
        return pair.getPerson1().getKitchenCount() < 3 && pair.getPerson2().getKitchenCount() < 3;
    }

    /**
     * Calculates the fitness based on the preference deviation within a pair.
     *
     * @param group The pair to evaluate.
     * @return The fitness score based on the preference deviation.
     */
    private static double calculatePreferenceDeviationFitness(Group group) {
        int preferenceDeviation = (group.pair1.getMainFoodPreference().getValue() + group.pair2.getMainFoodPreference().getValue() + group.pair3.getMainFoodPreference().getValue()) / 3;

        if (preferenceDeviation == 2.0) {
            return 2.0;
        } else if (preferenceDeviation == 4.0/3.0) {
            return 4.0/3.0;
        } else if (preferenceDeviation == 1) {
            return 1.0;
        } else {
            return 2.0/3.0;
        }
    }
}
