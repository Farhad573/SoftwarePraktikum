package model;

import controller.Distance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static model.CSVFileReader.getParticipants;
import static model.ParticipantManager.participants;

public class GroupGeneratorTest {
    static CSVFileReader CSVFileReader =  new CSVFileReader() ;
    static PairGenerator pairGenerator = new PairGenerator() ;
    static GroupGenerator groupGenerator = new GroupGenerator();
    static  List<Group> starter = new ArrayList<>();
    static List<Group> mainDish = new ArrayList<>();
    static List<Group> dessert = new ArrayList<>();
    static HashSet<Pair> usedPairsInStarter = new HashSet<>();
    static HashSet<Pair> usedParisInMainDish = new HashSet<>();
    static HashSet<Pair> usedPairsInDessert = new HashSet<>();
//    static List<Participant> participantList;
//    static List<Pair> CSVPairs;


//    @BeforeAll
//    static void setup() throws FileNotFoundException {
//        CSVFileReader = new CSVFileReader();
//        pairGenerator = new PairGenerator();
//        groupGenerator = new GroupGenerator();
//        participantList = createSampleParticipant();
//        CSVPairs = CSVFileReader.getPairs();
//        starter = makeStarterGroup(2);
//        mainDish = makeMainDishGroup();
//        dessert = makeDessertGroup();
//
//    }



    @Test
    void StarterGroupDuplicateTest() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();//read file
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVFileReader.getPairs());
//
       List<Group> groupGeneration = groupGenerator.makeStarterGroups(concate,2);
        //List<Group> groupGeneration = makeStarterGroup(2);
        //HashSet<Pair> usedPairsInStarter = new HashSet<>();
//        for (int i = 0; i < population.size(); i++) {
//            hashSetParticipant.add(population.get(i).getPerson1());
//            hashSetParticipant.add(population.get(i).getPerson2());
//        }

        for (Group group : groupGeneration) {
            Pair pair1 = group.pair1;
            Pair pair2 = group.pair2;
            Pair pair3 = group.pair3;

            // Check for duplicates before adding participants to the set
            if (usedPairsInStarter.contains(pair1) || usedPairsInStarter.contains(pair2) || usedPairsInStarter.contains(pair3)) {
                Assertions.fail("Duplicate participants found in generated pairs.");
            }

            usedPairsInStarter.add(pair1);
            usedPairsInStarter.add(pair2);
            usedPairsInStarter.add(pair3);
        }
        System.out.println("number of elements in starter Hashset is " + usedPairsInStarter.size());

        List<Group> groupGeneration1 = groupGenerator.makeMainDishGroups(concate);
        //HashSet<Pair> usedParisInMainDish = new HashSet<>();
//        for (int i = 0; i < population.size(); i++) {
//            hashSetParticipant.add(population.get(i).getPerson1());
//            hashSetParticipant.add(population.get(i).getPerson2());
//        }

        for (Group group : groupGeneration1) {
            Pair pair1 = group.pair1;
            Pair pair2 = group.pair2;
            Pair pair3 = group.pair3;

            // Check for duplicates before adding participants to the set
            if (usedParisInMainDish.contains(pair1) || usedParisInMainDish.contains(pair2) || usedParisInMainDish.contains(pair3)) {
                Assertions.fail("Duplicate participants found in generated pairs.");
            }

            usedParisInMainDish.add(pair1);
            usedParisInMainDish.add(pair2);
            usedParisInMainDish.add(pair3);
        }
        System.out.println("number of elements in mainDish Hashset is " + usedParisInMainDish.size());

    }

//    private static List<Group> makeStarterGroup(int radius) {
//        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
//        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVPairs);
//
//        List<Group> groupGeneration = groupGenerator.makeStarterGroups(concate,radius);
//        return groupGeneration;
//    }

    @Test
    void checkMeatWithVeganOderVeggieInStarter() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVFileReader.getPairs());

        List<Group> groupGeneration = groupGenerator.makeStarterGroups(concate,2);
       // List<Group> groupGeneration = makeStarterGroup(2);
        for (Group group : groupGeneration) {
            FoodPreference pref1 = group.pair1.getMainFoodPreference();
            FoodPreference pref2 = group.pair2.getMainFoodPreference();
            FoodPreference pref3 = group.pair3.getMainFoodPreference();


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

            // Check for duplicates before adding participants to the set
            if (hasMeat && hasVeggieOrVegan) {
                Assertions.fail("Fleichi && Veggie/vegan Pair found.");
            }

        }
    }
    private static List<Participant> createSampleParticipant() throws FileNotFoundException {
        try{
            CSVFileReader.readCSVFile("teilnehmerliste.csv");
        }catch (FileNotFoundException e){
            System.out.println("file did not found");
        }
        participants = getParticipants();
        return getParticipants();
    }

    @Test
    void MainDishDuplicateTest() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVFileReader.getPairs());

        List<Group> groupGeneration = groupGenerator.makeMainDishGroups(concate);
        //HashSet<Pair> usedParisInMainDish = new HashSet<>();
//        for (int i = 0; i < population.size(); i++) {
//            hashSetParticipant.add(population.get(i).getPerson1());
//            hashSetParticipant.add(population.get(i).getPerson2());
//        }

        for (Group group : groupGeneration) {
            Pair pair1 = group.pair1;
            Pair pair2 = group.pair2;
            Pair pair3 = group.pair3;

            // Check for duplicates before adding participants to the set
            if (usedParisInMainDish.contains(pair1) || usedParisInMainDish.contains(pair2) || usedParisInMainDish.contains(pair3)) {
                Assertions.fail("Duplicate participants found in generated pairs.");
            }

            usedParisInMainDish.add(pair1);
            usedParisInMainDish.add(pair2);
            usedParisInMainDish.add(pair3);
        }

    }

//    private static List<Group> makeMainDishGroup() {
//        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
//        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVPairs);
//
//        List<Group> groupGeneration = groupGenerator.makeMainDishGroups(concate);
//        return groupGeneration;
//    }


    @Test
    void checkVeggieVegganFleichiinMainDishGroup() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVFileReader.getPairs());

        List<Group> groupGeneration = groupGenerator.makeMainDishGroups(concate);
        for (Group group : groupGeneration) {
            FoodPreference pref1 = group.pair1.getMainFoodPreference();
            FoodPreference pref2 = group.pair2.getMainFoodPreference();
            FoodPreference pref3 = group.pair3.getMainFoodPreference();


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

            // Check for duplicates before adding participants to the set
            if (hasMeat && hasVeggieOrVegan) {
                Assertions.fail("Fleichi && Veggie/vegan Pair found.");
            }

        }
    }
    @Test
    void DessertGroupDuplicateTest() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVFileReader.getPairs());
        List<Group> groupGeneration = groupGenerator.makeDessertGroups(concate);
        //HashSet<Pair> usedPairsInDessert = new HashSet<>();
//        for (int i = 0; i < population.size(); i++) {
//            hashSetParticipant.add(population.get(i).getPerson1());
//            hashSetParticipant.add(population.get(i).getPerson2());
//        }

        for (Group group : groupGeneration) {
            Pair pair1 = group.pair1;
            Pair pair2 = group.pair2;
            Pair pair3 = group.pair3;

            // Check for duplicates before adding participants to the set
            if (usedPairsInDessert.contains(pair1) || usedPairsInDessert.contains(pair2) || usedPairsInDessert.contains(pair3)) {
                Assertions.fail("Duplicate participants found in generated pairs.");
            }

            usedPairsInDessert.add(pair1);
            usedPairsInDessert.add(pair2);
            usedPairsInDessert.add(pair3);
        }

    }

//    private static List<Group> makeDessertGroup() {
//        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
//        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVPairs);
//
//        List<Group> groupGeneration = groupGenerator.makeDessertGroups(concate);
//        return groupGeneration;
//    }


    @Test
    void checkVeggieVegganFleichiInDessertGroup() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
        List<Pair> concate = groupGenerator.makeAllPairsTogether(population,CSVFileReader.getPairs());
        List<Group> groupGeneration = groupGenerator.makeDessertGroups(concate);
        for (Group group : groupGeneration) {
            FoodPreference pref1 = group.pair1.getMainFoodPreference();
            FoodPreference pref2 = group.pair2.getMainFoodPreference();
            FoodPreference pref3 = group.pair3.getMainFoodPreference();


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

            // Check for duplicates before adding participants to the set
            if (hasMeat && hasVeggieOrVegan) {
                Assertions.fail("Fleichi && Veggie/vegan Pair found.");
            }

        }
    }

@Test
    void checkNumberOfStarterWithMainDish(){
        Assertions.assertEquals(mainDish.size(),starter.size());
    }
    @Test
    void checkNumberOfMainDishWithDessert(){
        Assertions.assertEquals(mainDish.size(),dessert.size());
    }
    @Test
    void checkNumberOfStarterWithDessert(){
        Assertions.assertEquals(dessert.size(),starter.size());
    }

    @Test
    void checkIfSamePairsAreUsedInStarterAndMain() throws FileNotFoundException {


    }

}
