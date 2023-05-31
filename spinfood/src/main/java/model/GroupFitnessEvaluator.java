package model;

public class GroupFitnessEvaluator {



    public static double evaluateFitnessForMainDish(Group group) {
        double fitness = 0.0;

        fitness +=  didPairsMeet(group.pair1,group.pair2,group.pair3);
        fitness +=  checkIfAllPairsDidntCook(group.pair1,group.pair2,group.pair3);
        fitness +=  checkIfOneOfPairsHaveCooked(group.pair1,group.pair2,group.pair3);

        return fitness;
    }

    public static double evaluateFitnessForDessert(Group group) {
        double fitness = 0.0;

        fitness +=  didPairsMeet(group.pair1,group.pair2,group.pair3);
        fitness +=  checkIfAllPairsDidntCook(group.pair1,group.pair2,group.pair3);
        fitness +=  checkIfTwoOfPairsHaveCooked(group.pair1,group.pair2,group.pair3);


        return fitness;
    }


    private static double checkIfOneOfPairsHaveCooked(Pair pair1, Pair pair2, Pair pair3) {
        if ((pair1.isHaveCooked() && !pair2.isHaveCooked() && !pair3.isHaveCooked()) ||
                (!pair1.isHaveCooked() && pair3.isHaveCooked() && !pair2.isHaveCooked()) ||
                (pair2.isHaveCooked() && !pair3.isHaveCooked() && !pair1.isHaveCooked())) {
            return 1.0;
        }
        return -8.0;
    }





    private static double checkIfTwoOfPairsHaveCooked(Pair pair1,Pair pair2,Pair pair3){
        if ((pair1.isHaveCooked() && pair2.isHaveCooked() && !pair3.isHaveCooked()) || (pair1.isHaveCooked() && pair3.isHaveCooked() && !pair2.isHaveCooked()) || (pair2.isHaveCooked() && pair3.isHaveCooked() && !pair1.isHaveCooked())){
            return 1.0;
        }else
            return - 8.0;
    }

    private static double didPairsMeet(Pair pair1,Pair pair2,Pair pair3){
        if (!pair1.getMetPairs().contains(pair2) && !pair1.getMetPairs().contains(pair3)
                && (!pair2.getMetPairs().contains(pair1) && !pair2.getMetPairs().contains(pair3))
                && (!pair3.getMetPairs().contains(pair1) && !pair3.getMetPairs().contains(pair2))){
            return 1.0;
        }else
            return - 8.0;
    }

    private static double checkIfAllPairsDidntCook(Pair pair1,Pair pair2,Pair pair3){
        if (!pair1.isHaveCooked() && (!pair2.isHaveCooked()) && (!pair3.isHaveCooked())){
          return 1.0;
        }else
            return - 8.0;
    }



    private static double calculateAgeDifferenceFitness(Group pair) {
        double ageDiff = (pair.pair1.getAgeDifference() + pair.pair2.getAgeDifference() + pair.pair3.getAgeDifference()) / 3;
        return 1.0 - ageDiff / 100.0; // Normalize age difference to a value between 0 and 1
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

            return -0.5;
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
