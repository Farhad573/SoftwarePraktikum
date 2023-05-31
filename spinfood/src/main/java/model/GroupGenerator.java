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

    public List<Pair> makeAllPairsTogether(List<Pair> l1, List<Pair> l2) {
        List<Pair> pairs = Stream.concat(l1.stream(), l2.stream())
                .collect(Collectors.toList());
        this.pairs = pairs;
        return pairs;
    }


    public List<Group> generateGroup(List<Pair> pairs,int radius) {
        Set<Pair> usedPairs = new HashSet<>();
        makeStarterGroups(pairs, radius);

            return null;

    }

    public List<Group> makeStarterGroups(List<Pair> pairs, double radius) {
        Set<Pair> usedPairs = new HashSet<>();
        Course course = Course.starter;

        for (int i = 0; i < pairs.size(); i++) {
            Pair pair1 = pairs.get(i);
            for (int j = i + 1; j < pairs.size(); j++) {
                Pair pair2 = pairs.get(j);
                for (int k = j + 1; k < pairs.size(); k++) {
                    Pair pair3 = pairs.get(k);
                    if (checkTwokitchenWithin(pair1.getKitchen(), pair2.getKitchen(), pair3.getKitchen(),radius) &&
                            checkGroupFoodPreference(pair1, pair2, pair3) &&
                            !usedPairs.contains(pair1) &&
                            !usedPairs.contains(pair2) &&
                            !usedPairs.contains(pair3)) {

                        // Create a valid group
                        Group group = new Group(pair1, pair2, pair3);
                        starter.add(group);
                        findWhichPairToCook(group,pairs);
                        setPairsWhoMet(group,course);
                        usedPairs.add(pair1);
                        usedPairs.add(pair2);
                        usedPairs.add(pair3);
                    }
                }
            }
        }
        groupSuccessors = pairs.stream().filter(x-> !usedPairs.contains(x)).collect(Collectors.toList());
        return starter;
    }

    public List<Group> makeMainDishGroups(List<Pair> pairs, double radius) {
        Set<Pair> usedPairs = new HashSet<>();
        Course course = Course.maincourse;

        for (int i = 0; i < pairs.size(); i++) {
            Pair pair1 = pairs.get(i);
            for (int j = i + 1; j < pairs.size(); j++) {
                Pair pair2 = pairs.get(j);
                for (int k = j + 1; k < pairs.size(); k++) {
                    Pair pair3 = pairs.get(k);
                    if (checkGroupFoodPreference(pair1, pair2, pair3) &&
                            !usedPairs.contains(pair1) &&
                            !usedPairs.contains(pair2) &&
                            !usedPairs.contains(pair3) && checkIfAllPairsDidntCook(pair1,pair2,pair3) && didPairsMeet(pair1,pair2,pair3) ) {
                        Group group = new Group(pair1, pair2, pair3);
                        mainDish.add(group);
                        findWhichPairToCook(group,pairs);
                        setPairsWhoMet(group,course);
                        usedPairs.add(pair1);
                        usedPairs.add(pair2);
                        usedPairs.add(pair3);
                    }
                }
            }
        }
        return mainDish;
    }

    public List<Group> makeDessertGroups(List<Pair> pairs) {
        Set<Pair> usedPairs = new HashSet<>();
        Course course = Course.maincourse;

        for (int i = 0; i < pairs.size(); i++) {
            Pair pair1 = pairs.get(i);
            for (int j = i + 1; j < pairs.size(); j++) {
                Pair pair2 = pairs.get(j);
                for (int k = j + 1; k < pairs.size(); k++) {
                    Pair pair3 = pairs.get(k);
                    if (checkGroupFoodPreference(pair1, pair2, pair3) &&
                            !usedPairs.contains(pair1) &&
                            !usedPairs.contains(pair2) &&
                            !usedPairs.contains(pair3) && checkIfAllPairsDidntCook(pair1,pair2,pair3) && didPairsMeet(pair1,pair2,pair3) && checkIfTwoOfPairsHaveCooked(pair1,pair2,pair3) ) {
                        Group group = new Group(pair1, pair2, pair3);
                        mainDish.add(group);
                        findWhichPairToCook(group,pairs);
                        setPairsWhoMet(group,course);
                        usedPairs.add(pair1);
                        usedPairs.add(pair2);
                        usedPairs.add(pair3);
                    }
                }
            }
        }
        return mainDish;
    }
    private boolean checkIfTwoOfPairsHaveCooked(Pair pair1,Pair pair2,Pair pair3){
        return (pair1.isHaveCooked() && pair2.isHaveCooked() && !pair3.isHaveCooked()) || (pair1.isHaveCooked() && pair3.isHaveCooked() && !pair2.isHaveCooked()) || (pair2.isHaveCooked() && pair3.isHaveCooked() && !pair1.isHaveCooked());
    }

    private boolean checkIfAllPairsDidntCook(Pair pair1,Pair pair2,Pair pair3){
        return (!pair1.isHaveCooked()) && (!pair2.isHaveCooked()) && (!pair3.isHaveCooked());
    }



    public List<Pair> pairsSortedBasedOnDistance(List<Pair> pairs) throws FileNotFoundException {
        this.partyLocation.readCSVFilePartyLocation("src/main/resources/partylocation.csv");
        double partyLongitude = this.partyLocation.getLongitude();
        double partylatitude = this.partyLocation.getLatitude();
        Collections.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair pair1, Pair pair2) {
                double distance1 = Distance.calculateDistance(pair1.getKitchen().getKitchen_location().getLatitude(),
                        pair1.getKitchen().getKitchen_location().getLongitude(), partylatitude, partyLongitude);
                double distance2 = Distance.calculateDistance(pair2.getKitchen().getKitchen_location().getLatitude(),
                        pair2.getKitchen().getKitchen_location().getLongitude(), partylatitude, partyLongitude);
                return Double.compare(distance2, distance1);  // Reverse the order of comparison
            }
        });
        return pairs;

    }





    private boolean didPairsMeet(Pair pair1,Pair pair2,Pair pair3){
        return (!pair1.getMetPairs().containsKey(pair2) && !pair1.getMetPairs().containsKey(pair3))
                && (!pair2.getMetPairs().containsKey(pair1) && !pair2.getMetPairs().containsKey(pair3))
                && (!pair3.getMetPairs().containsKey(pair1) && !pair3.getMetPairs().containsKey(pair2));
    }

    private void setPairsWhoMet(Group group,Course course){
        Pair pair1 = group.pair1;
        Pair pair2 = group.pair2;
        Pair pair3 = group.pair3;
        pair1.getMetPairs().put(pair2,course);
        pair1.getMetPairs().put(pair3,course);
        pair2.getMetPairs().put(pair1,course);
        pair2.getMetPairs().put(pair3,course);
        pair3.getMetPairs().put(pair1,course);
        pair3.getMetPairs().put(pair2,course);
    }

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

    private void findWhichPairToCook(Group group, List<Pair> pairs){
        Pair pair1 = group.pair1;
        Pair pair2 = group.pair2;
        Pair pair3 = group.pair3;

        int index1 = pairs.indexOf(group.pair1);
        int index2 = pairs.indexOf(group.pair2);
        int index3 = pairs.indexOf(group.pair3);

        int minIndex = Math.min(index1, Math.min(index2, index3));
        if (minIndex == index1 && !pair1.isHaveCooked()) {
            group.pair1.setHaveCooked(true);
            pairsWhoCooked.add(group.pair1);
        } else if (minIndex == index2 && !pair2.isHaveCooked()) {
            group.pair2.setHaveCooked(true);
            pairsWhoCooked.add(group.pair2);
        } else {
            group.pair3.setHaveCooked(true);
            pairsWhoCooked.add(group.pair3);
        }
    }


    public boolean checkTwokitchenWithin(Kitchen kithcen1,Kitchen kitchen2,Kitchen kitchen3,double radius){
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

        public static List<Pair> getGroupSuccessors(){return groupSuccessors;}







}
