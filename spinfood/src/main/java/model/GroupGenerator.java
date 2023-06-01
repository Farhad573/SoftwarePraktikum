package model;

import controller.Distance;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupGenerator extends ParticipantManager {
    Set<Pair> pairsWhoCooked = new HashSet<>();
    List<Group> groups = new ArrayList<>();
    List<Group> starter = new ArrayList<>();
    List<Group> mainDish = new ArrayList<>();
    List<Group> dessert = new ArrayList<>();
    PartyLocation partyLocation = new PartyLocation();



    /**
     * Combines two lists of pairs into a single list of pairs.
     *
     * @param l1   The first list of pairs.
     * @param l2   The second list of pairs.
     * @return     The combined list of pairs.
     */
    public List<Pair> makeAllPairsTogether(List<Pair> l1, List<Pair> l2) {
        List<Pair> pairs = Stream.concat(l1.stream(), l2.stream())
                .collect(Collectors.toList());
        this.pairs = pairs;
        return pairs;
    }


    /**
     * Generates groups by calling specific methods for generating starter, main dish, and dessert groups.
     *
     * @param pairs   The list of pairs to generate groups from.
     * @param radius  The radius within which the pairs' kitchens should be located.
     */
    public void callGroupsGenerator(List<Pair> pairs, double radius){
        makeStarterGroups(pairs, radius);
        makeMainDishGroups(pairs,radius);
        makeDessertGroups(pairs);
    }



    /**
     * Generates groups for the starter course based on the given pairs and radius.
     *
     * @param pairs   The list of pairs to generate groups from.
     * @param radius  The radius within which the pairs' kitchens should be located.
     * @return        The list of generated groups for the starter course.
     */
    public List<Group> makeStarterGroups(List<Pair> pairs, double radius) {
        Course course = Course.starter;

        Map<Group,Double> map = new HashMap<>();
        for (int i = 0; i < pairs.size(); i++) {
            Pair pair1 = pairs.get(i);
            for (int j = i + 1; j < pairs.size(); j++) {
                Pair pair2 = pairs.get(j);
                for (int k = j + 1; k < pairs.size(); k++) {
                    Pair pair3 = pairs.get(k);
                    if (checkGroupFoodPreference(pair1, pair2, pair3) &&
                    checkTwoKitchenWithin(pair1.getKitchen(), pair2.getKitchen(), pair3.getKitchen(),radius)){
                        Group group = new Group(pair1,pair2,pair3);
                        map.put(group, 1.0);
                    }
                }
            }
        }
        List<Group> list = map.entrySet().stream().filter(x -> x.getValue() > 0).map(Map.Entry::getKey).toList();
        Set<Pair> hashSet = new HashSet<>();

        List<Group> resGroup = new ArrayList<>();
        for(Group group : list){
            if(!hashSet.contains(group.pair1) && !hashSet.contains(group.pair2) && !hashSet.contains(group.pair3) ){
                findWhichPairToCook(group,pairs);
                group.setPairsWhoMet(group);
                resGroup.add(group);
                hashSet.add(group.pair1);
                hashSet.add(group.pair2);
                hashSet.add(group.pair3);
            }
        }
        //138 / 6 = 46
        System.out.println("number starter of validPairs is " + hashSet.size());
        return resGroup;
    }




    /**
     * Generates groups for the main dish course based on the given pairs and radius.
     *
     * @param pairs   The list of pairs to generate groups from.
     * @param radius  The radius within which the pairs' kitchens should be located.
     * @return        The list of generated groups for the main dish course.
     */
    public List<Group> makeMainDishGroups(List<Pair> pairs, double radius) {
        Course course = Course.maincourse;
        List<Group> resGroup = new ArrayList<>();
        Set<Pair> usedPairs = new HashSet<>();

        for (int i = 0; i < pairs.size() - 2; i++) {
            Pair pair1 = pairs.get(i);
            for (int j = i + 1; j < pairs.size() - 1; j++) {
                Pair pair2 = pairs.get(j);
                for (int k = j + 1; k < pairs.size(); k++) {
                    Pair pair3 = pairs.get(k);
                    if (checkGroupFoodPreference(pair1, pair2, pair3)
                            && checkIfOneOfPairsHaveCooked(pair1, pair2, pair3)
                            && !usedPairs.contains(pair1)
                            && !usedPairs.contains(pair2)
                            && !usedPairs.contains(pair3)) { // kitchen check to after party
                        Group group = new Group(pair1, pair2, pair3);
                        double fitness = GroupFitnessEvaluator.evaluateFitnessForMainDish(group);
                        if (fitness > 0) {
                            findWhichPairToCook(group, pairs);
                            group.setPairsWhoMet(group);
                            resGroup.add(group);
                            usedPairs.add(pair1);
                            usedPairs.add(pair2);
                            usedPairs.add(pair3);
                        }
                    }
                }
            }
        }

        System.out.println("Number of valid pairs for main dish: " + usedPairs.size());
        return resGroup;
    }



    /**
     * Generates groups for the dessert course based on the given pairs.
     *
     * @param pairs   The list of pairs to generate groups from.
     * @return        The list of generated groups for the dessert course.
     */
    public List<Group> makeDessertGroups(List<Pair> pairs) {
        Set<Pair> usedPairs = new HashSet<>();
        Course course = Course.dessert;

        Map<Group,Double> map = new HashMap<>();
        for (int i = 0; i < pairs.size(); i++) {
            Pair pair1 = pairs.get(i);
            for (int j = i + 1; j < pairs.size(); j++) {
                Pair pair2 = pairs.get(j);
                for (int k = j + 1; k < pairs.size(); k++) {
                    Pair pair3 = pairs.get(k);
                    if (checkGroupFoodPreference(pair1, pair2, pair3)
                    &&  checkIfTwoOfPairsHaveCooked(pair1, pair2, pair3)){
                        Group group = new Group(pair1,pair2,pair3);
                        map.put(group,GroupFitnessEvaluator.evaluateFitnessForDessert(group));
                    }
                }
            }
        }

        List<Group> list = map.entrySet().stream().filter(x -> x.getValue() > 0).map(Map.Entry::getKey).toList();

        Set<Pair> hashSet = new HashSet<>();

        List<Group> resGroup = new ArrayList<>();
        for(Group group : list){
            if(!hashSet.contains(group.pair1) && !hashSet.contains(group.pair2) && !hashSet.contains(group.pair3) ){
                resGroup.add(group);
                hashSet.add(group.pair1);
                hashSet.add(group.pair2);
                hashSet.add(group.pair3);
            }
        }

        System.out.println("number of validPairs in desert Hashset is " + hashSet.size());

        return resGroup;
    }

    /**
     * Checks if one of the pairs in a group has cooked.
     *
     * @param pair1   The first pair.
     * @param pair2   The second pair.
     * @param pair3   The third pair.
     * @return        True if one of the pairs has cooked, false otherwise.
     */
    private boolean checkIfOneOfPairsHaveCooked(Pair pair1,Pair pair2,Pair pair3){
        return (pair1.isHaveCooked() && !pair2.isHaveCooked() && !pair3.isHaveCooked()) ||
                (!pair1.isHaveCooked() && pair3.isHaveCooked() && !pair2.isHaveCooked()) ||
                (pair2.isHaveCooked() && !pair3.isHaveCooked() && !pair1.isHaveCooked());
    }

    /**
     * Checks if none of the pairs in a group have cooked.
     *
     * @param pair1   The first pair.
     * @param pair2   The second pair.
     * @param pair3   The third pair.
     * @return        True if none of the pairs have cooked, false otherwise.
     */
    private boolean checkIfAllPairsDidntCook(Pair pair1,Pair pair2,Pair pair3){
        return (!pair1.isHaveCooked()) && (!pair2.isHaveCooked()) && (!pair3.isHaveCooked());
    }

    /**
     * Checks if two of the pairs in a group have cooked.
     *
     * @param pair1   The first pair.
     * @param pair2   The second pair.
     * @param pair3   The third pair.
     * @return        True if two of the pairs have cooked, false otherwise.
     */
    private boolean checkIfTwoOfPairsHaveCooked(Pair pair1,Pair pair2,Pair pair3){
        return (pair1.isHaveCooked() && pair2.isHaveCooked() && !pair3.isHaveCooked()) || (pair1.isHaveCooked() && pair3.isHaveCooked() && !pair2.isHaveCooked()) || (pair2.isHaveCooked() && pair3.isHaveCooked() && !pair1.isHaveCooked());
    }

    /**
     * Checks if all the pairs in a group have cooked.
     *
     * @param pair1   The first pair.
     * @param pair2   The second pair.
     * @param pair3   The third pair.
     * @return        True if two of the pairs have cooked, false otherwise.
     */
    private boolean checkIfAllOfPairsHaveCooked(Pair pair1,Pair pair2,Pair pair3){
        return pair1.isHaveCooked() && pair2.isHaveCooked() && pair3.isHaveCooked() ;
    }





    public List<Pair> pairsSortedBasedOnDistance(List<Pair> pairs) throws FileNotFoundException {
        this.partyLocation.readCSVFilePartyLocation("src/main/resources/partylocation.csv");
        double partyLongitude = this.partyLocation.getLongitude();
        double partyLatitude = this.partyLocation.getLatitude();
        Collections.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair pair1, Pair pair2) {
                double distance1 = Distance.calculateDistance(pair1.getKitchen().getKitchen_location().getLatitude(),
                        pair1.getKitchen().getKitchen_location().getLongitude(), partyLatitude, partyLongitude);
                double distance2 = Distance.calculateDistance(pair2.getKitchen().getKitchen_location().getLatitude(),
                        pair2.getKitchen().getKitchen_location().getLongitude(), partyLatitude, partyLongitude);
                return Double.compare(distance2, distance1);  // Reverse the order of comparison
            }
        });
        return pairs;

    }





    private boolean didPairsMeet(Pair pair1,Pair pair2,Pair pair3){
        return (!pair1.getMetPairs().contains(pair2) && !pair1.getMetPairs().contains(pair3))
                && (!pair2.getMetPairs().contains(pair1) && !pair2.getMetPairs().contains(pair3))
                && (!pair3.getMetPairs().contains(pair1) && !pair3.getMetPairs().contains(pair2));
    }

    /**
     * Checks if the food preferences of three pairs in a group match.
     *
     * @param pair1   The first pair.
     * @param pair2   The second pair.
     * @param pair3   The third pair.
     * @return        True if the food preferences match, false otherwise.
     */
    public boolean checkGroupFoodPreference(Pair pair1,Pair pair2,Pair pair3){
        FoodPreference pref1 = pair1.getMainFoodPreference();
        FoodPreference pref2 = pair2.getMainFoodPreference();
        FoodPreference pref3 = pair3.getMainFoodPreference();


        boolean hasMeat = false;
        boolean hasVeggieOrVegan = false;

        if (pref1 == FoodPreference.meat
                || pref2 == FoodPreference.meat
                || pref3 == FoodPreference.meat) {
            hasMeat = true;
        }

        if (pref1 == FoodPreference.veggie || pref1 == FoodPreference.vegan
                || pref2 == FoodPreference.veggie || pref2 == FoodPreference.vegan
                || pref3 == FoodPreference.veggie || pref3 == FoodPreference.vegan) {
            hasVeggieOrVegan = true;
        }

        return !(hasMeat && hasVeggieOrVegan);
    }

    /**
     * Finds which pair should cook for a given group based on their kitchen distances to the party location.
     *
     * @param group   The group for which to determine the cooking pair.
     * @param pairs   The list of pairs.
     */
    private void findWhichPairToCook(Group group, List<Pair> pairs){
        Pair pair1 = group.pair1;
        Pair pair2 = group.pair2;
        Pair pair3 = group.pair3;

        int index1 = pairs.indexOf(group.pair1);
        int index2 = pairs.indexOf(group.pair2);
        int index3 = pairs.indexOf(group.pair3);

        int minIndex = Math.min(index1, Math.min(index2, index3));
        if (minIndex == index1 && !pair1.isHaveCooked()) {
            pair1.setHaveCooked(true);
            pairsWhoCooked.add(pair1);
        } else if (minIndex == index2 && !pair2.isHaveCooked()) {
            group.pair2.setHaveCooked(true);
            pairsWhoCooked.add(pair2);
        } else {
            pair3.setHaveCooked(true);
            pairsWhoCooked.add(pair3);
        }
    }


    /**
     * Checks if the kitchens of two pairs and the party location are within a given radius.
     *
     * @param kithcen1    The kitchen of the first pair.
     * @param kitchen2    The kitchen of the second pair.
     * @param kitchen3    The kitchen of the third pair.
     * @param radius      The radius within which the kitchens should be located.
     * @return            True if the kitchens are within the radius, false otherwise.
     */
    public boolean checkTwoKitchenWithin(Kitchen kithcen1, Kitchen kitchen2, Kitchen kitchen3, double radius){
        double long1 = kithcen1.getKitchen_location().getLongitude();
        double lat1 = kithcen1.getKitchen_location().getLatitude();
        double lat2 = kitchen2.getKitchen_location().getLatitude();
        double long2 = kitchen2.getKitchen_location().getLongitude();
        double lat3 = kitchen3.getKitchen_location().getLatitude();
        double long3 = kitchen3.getKitchen_location().getLongitude();

        double distance1 = Distance.calculateDistance(lat1,long1,lat2,long2);
        double distance2 = Distance.calculateDistance(lat2,long2,lat3,long3);
        double distance3 = Distance.calculateDistance(lat1,long1,lat3,long3);

        return (distance1 < radius) && (distance2 < radius) && (distance3 < radius);
    }

        public static List<Pair> getstarterSuccessors(){return starterSuccessors;}
        public static List<Pair> getMainDishSuccessors(){return mainDishSuccessors;}
        public static List<Pair> getdessertSuccessors(){return dessertSuccessors;}

}
