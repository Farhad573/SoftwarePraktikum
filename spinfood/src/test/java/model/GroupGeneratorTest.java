package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static model.CSVFileReader.getParticipants;

public class GroupGeneratorTest {
    CSVFileReader CSVFileReader = new CSVFileReader();
    PairGenerator pairGenerator = new PairGenerator();
    GroupGenerator groupGenerator = new GroupGenerator();
    @Test
    void generateInitialPopulation() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);
        List<Group> groupGeneration = groupGenerator.generateGroup(population,1);
        for (Group pair : groupGeneration) {
            Assertions.assertNotEquals(pair.getPair1(), pair.getPair2());
            Assertions.assertNotEquals(pair.getPair2(), pair.getPair3());
            Assertions.assertNotEquals(pair.getPair1(), pair.getPair3());
        }
    }

    @Test
    void generateInitialPopulationFromDifferentParticipant() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();//read file
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method
        List<Group> groupGeneration = groupGenerator.generateGroup(population,1);
        HashSet<Pair> hashsetPairs = new HashSet<>();
//        for (int i = 0; i < population.size(); i++) {
//            hashSetParticipant.add(population.get(i).getPerson1());
//            hashSetParticipant.add(population.get(i).getPerson2());
//        }

        for (Group group : groupGeneration) {
            Pair pair1 = group.pair1;
            Pair pair2 = group.pair2;
            Pair pair3 = group.pair3;

            // Check for duplicates before adding participants to the set
            if (hashsetPairs.contains(pair1) || hashsetPairs.contains(pair2) || hashsetPairs.contains(pair3)) {
                Assertions.fail("Duplicate participants found in generated pairs.");
            }

            hashsetPairs.add(pair1);
            hashsetPairs.add(pair2);
            hashsetPairs.add(pair3);
        }

    }
    @Test
    void checkMeatWithVeganOderVeggie() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);
        List<Group> groupGeneration = groupGenerator.generateGroup(population,1);
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
    private List<Participant> createSampleParticipant() throws FileNotFoundException {
        try{
            CSVFileReader.readCSVFile("teilnehmerliste.csv");
        }catch (FileNotFoundException e){
            System.out.println("file did not found");
        }
        return getParticipants();
    }
}
