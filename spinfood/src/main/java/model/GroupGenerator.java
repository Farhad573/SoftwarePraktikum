package model;

import controller.Distance;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupGenerator extends ParticipantManager {
    Set<Pair> pairsWhoCooked = new HashSet<>();

    private List<Pair> makeAllPairsTogether(List<Pair> l1, List<Pair> l2) {
        List<Pair> pairs = Stream.concat(l1.stream(), l2.stream())
                .collect(Collectors.toList());
        this.pairs = pairs;
        return pairs;
    }


    public List<Group> generateGroup(List<Pair> pairs,int radius) {

//        List<Group> population = new ArrayList<>();
//        HashSet<Pair> usedPairs = new HashSet<>();
//        int pairsSize = pairs.size();
//        int limit = pairsSize % 2 == 0? pairsSize : pairsSize - 1;
//
//
//        for (int i = 0; i < pairs.size(); i++) {
//            Pair pair1 = pairs.get(i);
//            for (int j = i + 1; j < pairs.size(); j++) {
//                Pair pair2 = pairs.get(j);
//                for (int k = j + 1; k < pairs.size(); k++) {
//                    Pair pair3 = pairs.get(k);
//                    if (checkTwokitchenWithin(pair1.getKitchen(), pair2.getKitchen(), pair3.getKitchen(),radius) &&
//                            checkGroupFoodPreference(pair1, pair2, pair3) &&
//                            !usedPairs.contains(pair1) &&
//                            !usedPairs.contains(pair2) &&
//                            !usedPairs.contains(pair3)) {
//
//                        // Create a valid group
//                        Group group = new Group(pair1, pair2, pair3);
//                        population.add(group);
//                        usedPairs.add(pair1);
//                        usedPairs.add(pair2);
//                        usedPairs.add(pair3);
//                    }
//                }
//            }
//        }
//
//
//        groupSuccessors = pairs.stream().filter(x-> !usedPairs.contains(x)).collect(Collectors.toList());
//        return  population;

//        List<Group> groups = new ArrayList<>();
//
//        for (int i = 0; i < 3; i++) {
//            Collections.shuffle(pairs); // Shuffle the list of pairs
//
//            List<Pair> currentGroup = new ArrayList<>();
//            Set<Pair> usedPairs = new HashSet<>();
//
//            // Iterate through the shuffled pairs to form groups
//            for (Pair pair : pairs) {
//                if (!usedPairs.contains(pair)) { // if it is new groups
//                    currentGroup.add(pair);
//                    usedPairs.add(pair);
//
//                    // Check if the group size reaches 3
//                    if (currentGroup.size() == 3) {
//                        Group group = new Group(currentGroup.get(0), currentGroup.get(1),
//                                currentGroup.get(2));
//                        groups.add(group);
//                        currentGroup = new ArrayList<>();
//                    }
//                }
//            }
//
//            //  successors.add(currentGroup); // Add the last group (if it has less than 3 pairs)
//        }
//
//        return groups;
        List<Group> groups = new ArrayList<>();
        List<Group> starter = new ArrayList<>();
        List<Group> mainDish = new ArrayList<>();
        List<Group> dessert = new ArrayList<>();
        Set<Pair> usedPairs = new HashSet<>();


        makeStarterGroups(pairs, radius, groups, starter, usedPairs);

        for (Pair pair : pairs) {
            Collections.shuffle(pairs);
            List<Pair> currentGroup = new ArrayList<>();
            if (!usedPairs.contains(pair)) {
                currentGroup.add(pair);
                usedPairs.add(pair);
                if (currentGroup.size() == 3) {
                    if (checkTwokitchenWithin(currentGroup.get(0).getKitchen(), currentGroup.get(1).getKitchen(), currentGroup.get(2).getKitchen(), radius) &&
                            checkGroupFoodPreference(currentGroup.get(1), currentGroup.get(2), currentGroup.get(3))) {
                        Group group = new Group(currentGroup.get(0), currentGroup.get(1),
                                currentGroup.get(2));
                        findWhichPairToCook(group);
                        groups.add(group);
                        starter.add(group);
                        currentGroup = new ArrayList<>();
                    }
                }

            }

        }

     return null;
    }

    private void makeStarterGroups(List<Pair> pairs, int radius, List<Group> groups, List<Group> starter, Set<Pair> usedPairs) {
        for (Pair pair : pairs) {
            Collections.shuffle(pairs);
            List<Pair> currentGroup = new ArrayList<>();
            if (!usedPairs.contains(pair)) {
                currentGroup.add(pair);
                usedPairs.add(pair);
                if (currentGroup.size() == 3) {
                    if (checkTwokitchenWithin(currentGroup.get(0).getKitchen(), currentGroup.get(1).getKitchen(), currentGroup.get(2).getKitchen(), radius) &&
                            checkGroupFoodPreference(currentGroup.get(1), currentGroup.get(2), currentGroup.get(3))) {
                        Group group = new Group(currentGroup.get(0), currentGroup.get(1),
                                currentGroup.get(2));
                        findWhichPairToCook(group);
                        groups.add(group);
                        starter.add(group);
                        currentGroup = new ArrayList<>();
                    }
                }

            }

        }
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
    private void findWhichPairToCook(Group group){
//        double longitude1 = group.pair1.getKitchen().getKitchen_location().getLongitude();
//        double latitude1 = group.pair1.getKitchen().getKitchen_location().getLatitude();
//        double longitude2 = group.pair2.getKitchen().getKitchen_location().getLongitude();
//        double latitude2 = group.pair2.getKitchen().getKitchen_location().getLatitude();
//        double longitude3 = group.pair3.getKitchen().getKitchen_location().getLongitude();
//        double latitude3 = group.pair3.getKitchen().getKitchen_location().getLatitude();
            Random random = new Random();
            int pairNumber = random.nextInt(3);
            switch (pairNumber){
                case 0:  group.pair1.setHaveCooked(true);
                         pairsWhoCooked.add(group.pair1);
                case 1:  group.pair2.setHaveCooked(true);
                         pairsWhoCooked.add(group.pair2);
                case 2:  group.pair3.setHaveCooked(true);
                         pairsWhoCooked.add(group.pair3);
            }


    }

    public boolean checkTwokitchenWithin(Kitchen kithcen1,Kitchen kitchen2,Kitchen kitchen3,int radius){
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
