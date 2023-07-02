package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.CSVFileReader.getParticipants;
import static model.ParticipantManager.generatedGroupsInMainDish;
import static model.ParticipantManager.participants;

public class GroupGeneratorTest {
    static CSVFileReader CSVFileReader;
    static PairGenerator pairGenerator;
    static GroupGenerator groupGenerator;
    static List<Group> starter;
    static List<Group> mainDish;
    static List<Group> dessert;
    static HashSet<Pair> usedPairsInStarter;
    static HashSet<Pair> usedParisInMainDish;
    static HashSet<Pair> usedPairsInDessert;
    static Location partyLocation;
    static int[] num = {1, 1, 1, 1, 1};

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        CSVFileReader = new CSVFileReader();
        pairGenerator = new PairGenerator();
        groupGenerator = new GroupGenerator();
        PartyLocation party = new PartyLocation();
        starter = new ArrayList<>();
        mainDish = new ArrayList<>();
        dessert = new ArrayList<>();
        usedPairsInStarter = new HashSet<>();
        usedParisInMainDish = new HashSet<>();
        usedPairsInDessert = new HashSet<>();

        try {
            CSVFileReader.readCSVFile("teilnehmerliste.csv");
            party.readCSVFilePartyLocation("partylocation.csv");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        partyLocation = new Location(party.getLongitude(), party.getLatitude());
        List<Pair> population = PairGenerator.generateInitialPopulation(getParticipants(), new int[4]);
        List<Pair> concat = pairGenerator.makeAllPairsTogether(population, model.CSVFileReader.getCSV_Pairs());
        groupGenerator.callGroupsGenerator(concat, num, partyLocation);
    }


    @Test
    void StarterGroupDuplicateTest() throws FileNotFoundException {
        for (Group group : ParticipantManager.getGeneratedGroupsinStarter()) {
            Pair pair1 = group.pair1;
            Pair pair2 = group.pair2;
            Pair pair3 = group.pair3;
            if (usedPairsInStarter.contains(pair1) || usedPairsInStarter.contains(pair2) || usedPairsInStarter.contains(pair3)) {
                Assertions.fail("Duplicate participants found in generated pairs.");
            }

            usedPairsInStarter.add(pair1);
            usedPairsInStarter.add(pair2);
            usedPairsInStarter.add(pair3);
        }


    }

    @Test
    void checkMeatWithVeganOderVeggieInStarter() throws FileNotFoundException {
        for (Group group : ParticipantManager.generatedGroupsinStarter) {
            if (!groupGenerator.checkGroupFoodPreference(group.pair1, group.pair2, group.pair3)) {
                Assertions.fail("Fleichi && Veggie/vegan Pair found.");
            }
        }
    }


    @Test
    void MainDishDuplicateTest() throws FileNotFoundException {
        for (Group group : ParticipantManager.generatedGroupsInMainDish) {
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
    void checkVeggieVegganFleichiInMainDishGroup() throws FileNotFoundException {
        for (Group group : generatedGroupsInMainDish) {
            if (!groupGenerator.checkGroupFoodPreference(group.pair1, group.pair2, group.pair3)) {
                Assertions.fail("More than one Fleichi/Egali Pair in Group Found.");
            }
        }
    }


    @Test
    void DessertGroupDuplicateTest() throws FileNotFoundException {
        for (Group group : ParticipantManager.generatedGroupsInDessert) {
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
    void checkVeggieVegganFleichiInDessertGroup() throws FileNotFoundException {
        for (Group group : ParticipantManager.generatedGroupsInDessert) {
            if (!groupGenerator.checkGroupFoodPreference(group.pair1, group.pair2, group.pair3)) {
                Assertions.fail("More than one Fleichi/Egali Pair in Group Found.");
            }
        }
    }

    @Test
    void checkIfPairCooksOnce() throws FileNotFoundException {
        Set<Pair> cookingPairsSet = new HashSet<>();
        for (Group group : ParticipantManager.generatedGroups
        ) {
            if (cookingPairsSet.contains(group.cookingPair)) {
                Assertions.fail("Duplicate Cooking Pair found in generated Groups.");
            }
            cookingPairsSet.add(group.cookingPair);
        }
    }

    @Test
    void checkEachPairIsIn3Groups() throws FileNotFoundException {
        Map<Pair, Set<Group>> pairGroupMap = new HashMap<>();
        for (Group group : ParticipantManager.generatedGroups
        ) {
            Pair pair1 = group.getPair1();
            Pair pair2 = group.getPair2();
            Pair pair3 = group.getPair3();

            // Add group to pair1
            pairGroupMap.computeIfAbsent(pair1, key -> new HashSet<>()).add(group);

            // Add group to pair2
            pairGroupMap.computeIfAbsent(pair2, key -> new HashSet<>()).add(group);

            // Add group to pair3
            pairGroupMap.computeIfAbsent(pair3, key -> new HashSet<>()).add(group);
        }
        for (Map.Entry<Pair, Set<Group>> entry : pairGroupMap.entrySet()) {
            Pair pair = entry.getKey();
            Set<Group> groupList = entry.getValue();

            if (groupList.size() != 3) {
                Assertions.fail("Each Pair is not 3 different groups");
            }
        }
    }

    @Test
    void checkEachPairMeetOthersOnce() throws FileNotFoundException {
        List<Group> dessertGroups = ParticipantManager.generatedGroups.stream().filter(x-> x.getCourse() == Course.dessert).toList();
        for (Group group : dessertGroups
        ) {
            Pair pair1 = group.getPair1();
            Pair pair2 = group.getPair2();
            Pair pair3 = group.getPair3();
            List<Pair> metPairs1 = Stream.concat(Stream.concat(pair1.getMetPairsInStarter().stream(),
                            pair1.getMetPairsInMainDish().stream()),
                    pair1.getMetPairsInDessert().stream()).toList();
            List<Pair> metPairs2 = Stream.concat(Stream.concat(pair2.getMetPairsInStarter().stream(),
                            pair2.getMetPairsInMainDish().stream()),
                    pair2.getMetPairsInDessert().stream()).toList();
            List<Pair> metPairs3 = Stream.concat(Stream.concat(pair3.getMetPairsInStarter().stream(),
                            pair3.getMetPairsInMainDish().stream()),
                    pair3.getMetPairsInDessert().stream()).toList();
            Set<Pair> set1 = new HashSet<>(metPairs1);
            boolean hasDuplicates1 = metPairs1.size() > set1.size();
            Set<Pair> set2 = new HashSet<>(metPairs2);
            boolean hasDuplicates2 = metPairs2.size() > set2.size();
            Set<Pair> set3 = new HashSet<>(metPairs3);
            boolean hasDuplicates3 = metPairs3.size() > set3.size();
            if(hasDuplicates1 || hasDuplicates2 || hasDuplicates3){
                Assertions.fail("A pair has met another one more than once");
            }

        }

    }
}
