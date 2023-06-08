package model;

/**
 * The GroupFitnessEvaluator class provides methods to evaluate the fitness of groups for main dishes and desserts.
 */
public class GroupFitnessEvaluator {

    /**
     * Evaluates the fitness of a group for the main dish.
     *
     * @param group The group to evaluate.
     * @return The fitness score for the group.
     */
    public static double evaluateFitnessForMainDish(Group group) {
        // Calculate fitness based on different criteria
        double fitness = 0.0;
        fitness += didPairsMeet(group.pair1, group.pair2, group.pair3);
        fitness += checkIfAllPairsDidntCook(group.pair1, group.pair2, group.pair3);
        fitness += checkIfOneOfPairsHaveCooked(group.pair1, group.pair2, group.pair3);
        return fitness;
    }

    /**
     * Evaluates the fitness of a group for the dessert.
     *
     * @param group The group to evaluate.
     * @return The fitness score for the group.
     */
    public static double evaluateFitnessForDessert(Group group) {
        // Calculate fitness based on different criteria
        double fitness = 0.0;
        fitness += didPairsMeet(group.pair1, group.pair2, group.pair3);
        fitness += checkIfAllPairsDidntCook(group.pair1, group.pair2, group.pair3);
        fitness += checkIfTwoOfPairsHaveCooked(group.pair1, group.pair2, group.pair3);
        return fitness;
    }

    /**
     * Checks if at least one pair in the group has cooked.
     *
     * @param pair1 The first pair.
     * @param pair2 The second pair.
     * @param pair3 The third pair.
     * @return 1.0 if at least one pair has cooked, -8.0 otherwise.
     */
    private static double checkIfOneOfPairsHaveCooked(Pair pair1, Pair pair2, Pair pair3) {
        if ((pair1.isHaveCooked() && !pair2.isHaveCooked() && !pair3.isHaveCooked()) ||
                (!pair1.isHaveCooked() && pair3.isHaveCooked() && !pair2.isHaveCooked()) ||
                (pair2.isHaveCooked() && !pair3.isHaveCooked() && !pair1.isHaveCooked())) {
            return 1.0;
        }
        return -8.0;
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
                (pair2.isHaveCooked() && pair3.isHaveCooked() && !pair1.isHaveCooked())) {
            return 1.0;
        } else {
            return -8.0;
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
    private static double checkIfAllPairsDidntCook(Pair pair1, Pair pair2, Pair pair3) {
        if (!pair1.isHaveCooked() && !pair2.isHaveCooked() && !pair3.isHaveCooked()) {
            return -8.0;
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
    private static double didPairsMeet(Pair pair1, Pair pair2, Pair pair3) {
        if (!pair1.getMetPairs().contains(pair2) && !pair1.getMetPairs().contains(pair3) &&
                !pair2.getMetPairs().contains(pair1) && !pair2.getMetPairs().contains(pair3) &&
                !pair3.getMetPairs().contains(pair1) && !pair3.getMetPairs().contains(pair2)) {
            return 1.0;
        } else {
            return -8.0;
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
     * @param pair The pair to evaluate.
     * @return The fitness score based on the preference deviation.
     */
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
