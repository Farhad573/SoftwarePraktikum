package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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



    // ???????????????????????????????????????????????????????
    @Test
    void generateNextGeneration() throws FileNotFoundException {
        List<Participant> participantList = createSampleParticipant();

        List<Pair> population = pairGenerator.generateInitialPopulation(participantList);

        List<Pair> nextGen = pairGenerator.generateNextGeneration(population);

        HashSet<Participant> participantHashSet = new HashSet<>();

        for (Pair pair : nextGen) {
            Participant person1 = pair.getPerson1();
            Participant person2 = pair.getPerson2();

            // Check for duplicates before adding participants to the set
            if (participantHashSet.contains(person1) || participantHashSet.contains(person2)) {
                Assertions.fail("Duplicate participants found in generated pairs.");
            }

            participantHashSet.add(person1);
            participantHashSet.add(person2);
        }

        Assertions.assertEquals(164, participantHashSet.size());
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