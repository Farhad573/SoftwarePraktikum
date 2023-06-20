package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static model.CSVFileReader.getParticipants;

class PairGeneratorTest {
    CSVFileReader CSVFileReader = new CSVFileReader();
    PairGenerator pairGenerator = new PairGenerator();

    @Test
    void generateInitialPopulation() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);
        Set<Pair> uniquePairs = new HashSet<>(population);

        Assertions.assertEquals(population.size(), uniquePairs.size());

        for (Pair pair : population) {
            Assertions.assertNotEquals(pair.getPerson1(), pair.getPerson2());
        }
    }

    //check duplicate
    @Test
    void generateInitialPopulationFromDifferentParticipant() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();//read file
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method

        HashSet<Participant> hashSetParticipant = new HashSet<>();


        for (Pair pair : population) {
            Participant person1 = pair.getPerson1();
            Participant person2 = pair.getPerson2();

            // Check for duplicates before adding participants to the set
            if (hashSetParticipant.contains(person1) || hashSetParticipant.contains(person2)) {
                Assertions.fail("Duplicate participants found in generated pairs.");
            }

            hashSetParticipant.add(person1);
            hashSetParticipant.add(person2);
        }

    }




    @Test
    void checkHasKitchenNo() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);


        for (Pair pair : population) {
            Participant person1 = pair.getPerson1();
            Participant person2 = pair.getPerson2();

            // Check for duplicates before adding participants to the set
            if (person1.getHasKitchen() == HasKitchen.no && person2.getHasKitchen() == HasKitchen.no) {
                Assertions.fail("Pair with no Kitchen Found.");
            }

        }
        System.out.println("population size is :" + population.size());
    }





    @Test
    void checkMeatWithVeganOderVeggie() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);

        for (Pair pair : population) {
            Participant person1 = pair.getPerson1();
            Participant person2 = pair.getPerson2();

            // Check for duplicates before adding participants to the set
            if (((person1.getFoodPreference() == FoodPreference.veggie || person1.getFoodPreference() == FoodPreference.vegan) && (person2.getFoodPreference() == FoodPreference.meat)) ||
                    ((person2.getFoodPreference() == FoodPreference.veggie || person2.getFoodPreference() == FoodPreference.vegan) && (person1.getFoodPreference() == FoodPreference.meat))) {
                Assertions.fail("Fleichi && Veggie/vegan Pair found.");
            }

        }
    }




    @Test
    void checkPreferenceDeviation() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);
        for (Pair pair: population
        ) {
            Assertions.assertTrue(pair.getPreferenceDeviation() <= 3);
        }
    }
    @Test
    void checkkitchenCount() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();
        List<Pair> population = PairGenerator.generateInitialPopulation(participantList);
        Map<Kitchen,List<Pair>> kitchenMap = new HashMap<>();
        for (Pair pair:population) {
            Kitchen kitchen = pair.getKitchen();
            List<Pair> pairList = kitchenMap.getOrDefault(kitchen, new ArrayList<>());
            pairList.add(pair);
            kitchenMap.put(kitchen, pairList);
        }
        Assertions.assertEquals(1,kitchenMap.values().stream().filter(x -> x.size() >= 4).count());

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