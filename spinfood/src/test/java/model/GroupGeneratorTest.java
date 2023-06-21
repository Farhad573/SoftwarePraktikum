package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static model.CSVFileReader.getParticipants;
import static model.ParticipantManager.participants;

public class GroupGeneratorTest {
    static CSVFileReader CSVFileReader ;
    static PairGenerator pairGenerator  ;
    static GroupGenerator groupGenerator;
    static  List<Group> starter ;
    static List<Group> mainDish ;
    static List<Group> dessert ;
    static HashSet<Pair> usedPairsInStarter;
    static HashSet<Pair> usedParisInMainDish ;
    static HashSet<Pair> usedPairsInDessert;

    @BeforeEach
    void setUp() {
        CSVFileReader = new CSVFileReader();
        pairGenerator = new PairGenerator();
        groupGenerator = new GroupGenerator();
        starter = new ArrayList<>();
        mainDish = new ArrayList<>();
        dessert = new ArrayList<>();
        usedPairsInStarter = new HashSet<>();
        usedParisInMainDish = new HashSet<>();
        usedPairsInDessert = new HashSet<>();

        try {
            CSVFileReader.readCSVFile("teilnehmerliste.csv");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }



    @Test
    void StarterGroupDuplicateTest() throws FileNotFoundException {
        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        //List<Group> groupGeneration = groupGenerator.makeStarterGroups(concat, 2);
        groupGenerator.callGroupsGenerator(concat,1);

        for (Group group : ParticipantManager.getGeneratedGroupsinStarter()) {
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


    }


    @Test
    void StarterGroupHasOnePairCookTest() throws FileNotFoundException {
        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        List<Group> groupGeneration = groupGenerator.makeStarterGroups(concat, 2);
        for (Group group : groupGeneration) {
            Pair pair1 = group.pair1;
            Pair pair2 = group.pair2;
            Pair pair3 = group.pair3;

            // Check for duplicates before adding participants to the set
            if (!checkIfOneOfPairsHaveCooked(pair1, pair2, pair3)) {
                Assertions.fail("One Pair did not cook");
            }
        }
    }




    @Test
    void checkMeatWithVeganOderVeggieInStarter() throws FileNotFoundException {

        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concate = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        List<Group> groupGeneration = groupGenerator.makeStarterGroups(concate, 2);

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
    void MainDishDuplicateTest() throws FileNotFoundException {
        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        List<Group> groupGeneration = groupGenerator.makeMainDishGroups(concat, 2);


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



    @Test
    void MainGroupHasTwoPairCookTest() throws FileNotFoundException {
        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        List<Group> groupGeneration0 = groupGenerator.makeStarterGroups(concat, 2);

        List<Group> groupGeneration = groupGenerator.makeMainDishGroups(concat, 2);
        for (Group group : groupGeneration) {
            Pair pair1 = group.pair1;
            Pair pair2 = group.pair2;
            Pair pair3 = group.pair3;

            // Check for duplicates before adding participants to the set
            if (!checkIfTwoOfPairsHaveCooked(pair1, pair2, pair3)) {
                Assertions.fail("two Pair did not cook");
            }
        }

    }






    @Test
    void checkVeggieVegganFleichiInMainDishGroup() throws FileNotFoundException {

        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        List<Group> groupGeneration = groupGenerator.makeStarterGroups(concat, 2);

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

        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concate = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        List<Group> groupGeneration = groupGenerator.makeDessertGroups(concate);


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

    @Test
    void DessertGroupHaveAllPairCookTest() throws FileNotFoundException {
        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        List<Group> groupGeneration0 = groupGenerator.makeStarterGroups(concat, 1);
        List<Group> groupGeneration1 = groupGenerator.makeMainDishGroups(concat, 1);
        List<Group> groupGeneration = groupGenerator.makeDessertGroups(concat);

        for (Group group : groupGeneration) {
            Pair pair1 = group.pair1;
            //pair1.printPairsWhoMet();
            Pair pair2 = group.pair2;
            //pair2.printPairsWhoMet();
            Pair pair3 = group.pair3;
            //pair3.printPairsWhoMet();
            //group.printPairsWhoCooked();

            // Check for duplicates before adding participants to the set
            if (!checkIfAllPairsHaveCooked(pair1, pair2, pair3)) {
                Assertions.fail("All Pair did not cook");
            }
        }

    }




    @Test
    void checkVeggieVegganFleichiInDessertGroup() throws FileNotFoundException {

        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        List<Group> groupGeneration = groupGenerator.makeStarterGroups(concat, 2);;

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
    void checkNumberOfPairsWhoCookedInStarter() throws FileNotFoundException {
        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        groupGenerator.pairsSortedBasedOnDistance(concat);
        List<Group> groupGeneration = groupGenerator.makeStarterGroups(concat, 1);
        List<Pair> pairsWhoCookInStarter = groupGeneration.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Assertions.assertEquals(groupGeneration.size(),pairsWhoCookInStarter.size());

        for (Group group : groupGeneration) {
            group.printPairsWhoCooked();
        }
    }

    @Test
    void checkNumberOfPairsWhoCookedInMainDish() throws FileNotFoundException {
        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        groupGenerator.pairsSortedBasedOnDistance(concat);
        List<Group> starterGroups = groupGenerator.makeStarterGroups(concat, 2);
        List<Pair> pairsWhoCookInStarter = starterGroups.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Assertions.assertEquals(starterGroups.size() ,pairsWhoCookInStarter.size());
        for (Group group : starterGroups) {
            group.printPairsWhoCooked();
        }
        System.out.println("#".repeat(30));
        List<Group> mainDishGroup = groupGenerator.makeMainDishGroups(concat, 1);
        List<Pair> pairsWhoCookInMainDish = mainDishGroup.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Assertions.assertEquals(mainDishGroup.size() * 2,pairsWhoCookInMainDish.size());

        for (Group group : mainDishGroup) {
            group.printPairsWhoCooked();
        }
    }
    @Test
    void checkNumberOfPairsWhoCookedInDessert() throws FileNotFoundException {
        List<Pair> population = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, CSVFileReader.getCSV_Pairs());
        groupGenerator.pairsSortedBasedOnDistance(concat);
        List<Group> starterGroups = groupGenerator.makeStarterGroups(concat, 2);
        List<Pair> pairsWhoCookInStarter = starterGroups.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Assertions.assertEquals(starterGroups.size() ,pairsWhoCookInStarter.size());
        List<Pair> pairsInStarter = starterGroups.stream().flatMap(x-> x.getPairsInGroup().stream()).collect(Collectors.toList());
        System.out.println("number of Pairs in starter is " + pairsInStarter.size());



//        for (Group group : starterGroups) {
//            group.printPairsWhoCooked();
//        }

        System.out.println("#".repeat(90));
        List<Group> mainDishGroup = groupGenerator.makeMainDishGroups(pairsInStarter, 1);
        List<Pair> pairsWhoCookInMainDish = mainDishGroup.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Assertions.assertEquals(mainDishGroup.size() * 2,pairsWhoCookInMainDish.size());
       // List<Pair> pairsInMainDish = mainDishGroup.stream().flatMap(x -> x.getPairsInGroup().stream()).toList();


//        for (Group group : mainDishGroup) {
//            group.printPairsWhoCooked();
//        }

        System.out.println("#".repeat(30));
        List<Group> dessertGroup = groupGenerator.makeDessertGroups(pairsInStarter);
        List<Pair> pairsWhoCookInDessert = dessertGroup.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Assertions.assertEquals(dessertGroup.size() * 3,pairsWhoCookInDessert.size());
        System.out.println("number of pairs in starterMap is " + GroupGenerator.kitchenLocationsInStarter.keySet().size());
        System.out.println("number of pairs in MainDishMap is " + GroupGenerator.kitchenLocationsInMainDish.keySet().size());
        System.out.println("number of pairs in DessertMap is " + GroupGenerator.kitchenLocationsInDessert.keySet().size());

//        for (Group group : dessertGroup) {
//            group.printPairsWhoCooked();
//        }
//        for (Pair pair: concat
//             ) {
//            pair.printPairsWhoMet();
//        }
        System.out.println("#".repeat(30));

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


    // Helper Function


    /**
     * Checks if one of the pairs in a group has cooked.
     *
     * @param pairs   The pairs.
     * @return        True if one of the pairs has cooked, false otherwise.
     */
    private boolean checkIfOneOfPairsHaveCooked(Pair... pairs){
        int i = 0 ;
        for (Pair pair : pairs) {
            if (pair.isHaveCooked()) {
                i++;
            }
        }
        return i==1;
    }

    /**
     * Checks if two of the pairs in a group has cooked.
     *
     * @param pairs   The pairs.
     * @return        True if one of the pairs has cooked, false otherwise.
     */
    private boolean checkIfTwoOfPairsHaveCooked(Pair... pairs){
        int i = 0 ;
        for (Pair pair : pairs) {
            if (pair.isHaveCooked()) {
                i++;
            }
        }
        return i==2;
    }

    /**
     * Checks if all the pairs in a group has cooked.
     *
     * @param pairs   The pairs.
     * @return        True if one of the pairs has cooked, false otherwise.
     */
    private boolean checkIfAllPairsHaveCooked(Pair... pairs) {
        int cookedCount = 0; // Counter variable for cooked pairs

        for (Pair pair : pairs) {
            if (pair.isHaveCooked()) {
                cookedCount++;
            }
        }

        return cookedCount == 3; // Return true if all pairs have cooked
    }



    // read CSV File
    private static List<Participant> createSampleParticipant() throws FileNotFoundException {
        try{
            CSVFileReader.readCSVFile("teilnehmerliste.csv");
        }catch (FileNotFoundException e){
            System.out.println("file did not found");
        }
        participants = getParticipants();
        return getParticipants();
    }

}
