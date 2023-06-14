package model;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CSVFileReaderTest {

    CSVFileReader CSVFileReader = new CSVFileReader();
    PairGenerator pairGenerator = new PairGenerator();

    // check the count of elements in the lists
    @Test
    public void testCountOfParticipants() {
        // Create an instance of the class under test

        // Call the method that reads CSV data
        try{
            CSVFileReader.readCSVFile("src/main/java/model/teilnehmerliste.csv");
        }catch (FileNotFoundException e){
            System.out.println("file is not found !!!");
        }


        // Assert that the count of elements in the participants list is as expected
        int expectedParticipantCount = 164;
        int actualParticipantCount = CSVFileReader.participants.size();
        assertEquals(expectedParticipantCount, actualParticipantCount);

    }


    @Test
        void testCountOfPairs(){
        try{
            CSVFileReader.readCSVFile("src/main/java/model/teilnehmerliste.csv");
        }catch (FileNotFoundException e){
            System.out.println("file is not found !!!");
        }
        // Assert that the count of elements in the groups list is as expected
        int expectedGroupCount = 73;
        int actualGroupCount = CSVFileReader.CSV_Pairs.size();
        assertEquals(expectedGroupCount, actualGroupCount);
    }

    @Test
        void fileNotFoundTest(){
        assertThrows(FileNotFoundException.class,  () -> {
            // code that should throw FileNotFoundException
            CSVFileReader.readCSVFile("src/main/java/model/teilnehmerliste_Fake.csv");
        });
    }

    @Test
        void checkPairFoodPreference(){
        Participant egali = new Participant(FoodPreference.none);
        Participant egali2 = new Participant(FoodPreference.none);
        Participant fleichi = new Participant(FoodPreference.meat);
        Participant veggie = new Participant(FoodPreference.veggie);
        Participant vegan = new Participant(FoodPreference.vegan);

        Pair egaliPair = new Pair(egali,egali2,true);
        Pair pair1 = new Pair(veggie,fleichi,true);
        Pair pair2 = new Pair(vegan,fleichi,true);
        Pair pair3 = new Pair(fleichi,egali,true);
        Pair pair4 = new Pair(egali,fleichi,true);
        Pair pair5 = new Pair(egali,veggie,true);
        Pair pair6 = new Pair(egali,vegan,true);
        Pair pair7 = new Pair(fleichi,veggie,true);
        Pair pair8 = new Pair(fleichi,vegan,true);
        Pair pair9 = new Pair(egali,fleichi,true);
        Pair pair10 = new Pair(vegan,egali,true);
        assertEquals(FoodPreference.none, egaliPair.getMainFoodPreference());
        assertEquals(FoodPreference.veggie, pair1.getMainFoodPreference());
        assertEquals(FoodPreference.vegan, pair2.getMainFoodPreference());
        assertEquals(FoodPreference.meat, pair3.getMainFoodPreference());
        assertEquals(FoodPreference.meat, pair4.getMainFoodPreference());
        assertEquals(FoodPreference.veggie, pair5.getMainFoodPreference());
        assertEquals(FoodPreference.vegan, pair6.getMainFoodPreference());
        assertEquals(FoodPreference.veggie, pair7.getMainFoodPreference());
        assertEquals(FoodPreference.vegan, pair8.getMainFoodPreference());
        assertEquals(FoodPreference.meat, pair9.getMainFoodPreference());
        assertEquals(FoodPreference.vegan, pair10.getMainFoodPreference());

    }

    @Test
    void checkDuplicatesOfInitialPopulation(){
        try{
            CSVFileReader.readCSVFile("src/main/java/model/teilnehmerliste.csv");
        }catch (FileNotFoundException e){
            System.out.println("file is not found !!!");
        }
        List<Pair> initialPopulation = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());

        boolean duplicate = hasNoDuplicates(initialPopulation);
        assertTrue(duplicate);

    }
    private <T> boolean hasNoDuplicates(List<T> list) {
        Set<T> set = new HashSet<>();

        for (T element : list) {
            if (set.contains(element)) {
                return false;
            }
            set.add(element);
        }

        return true;
    }



}
