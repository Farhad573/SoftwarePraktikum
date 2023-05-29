package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Test
    void generateInitialPopulationFromDifferentParticipant() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();//read file
        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);// make pair with method

        HashSet<Participant> hashSetParticipant = new HashSet<>();
//        for (int i = 0; i < population.size(); i++) {
//            hashSetParticipant.add(population.get(i).getPerson1());
//            hashSetParticipant.add(population.get(i).getPerson2());
//        }

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

        Assertions.assertEquals(164, hashSetParticipant.size());
    }




    @Test
    void checkHasKitchen() throws FileNotFoundException {
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
    void checkPairWithSameSex() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);


        for (Pair pair : population) {
            Participant person1 = pair.getPerson1();
            Participant person2 = pair.getPerson2();

            // Check for duplicates before adding participants to the set
            if (person1.getSex() == person2.getSex()) {
                Assertions.fail("Pair of same sex  found.");
            }

        }
    }
    @Test
    void checkKitchenCount() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);
        List<Participant> kitchen = participantList.stream().filter(x -> x.getKitchenCount() > 3).collect(Collectors.toList());
        for (Participant pair: kitchen
             ) {
            System.out.println(pair);
        }
//        for (Participant participant: kitchen
//             ) {
//            Assertions.assertTrue(FitnessEvaluator.checkKitchenCount(participant));
//        }
    }


    @Test
    void cehckPreferenceDeviation() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);
        for (Pair pair: population
        ) {
            Assertions.assertTrue(pair.getPreferenceDeviation() <= 3);
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