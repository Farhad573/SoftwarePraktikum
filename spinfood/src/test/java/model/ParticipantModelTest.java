package model;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParticipantModelTest {

    ParticipantModel participantModel = new ParticipantModel();

    // check the count of elements in the lists
    @Test
    public void testCountOfParticipants() {
        // Create an instance of the class under test

        // Call the method that reads CSV data
        try{
            participantModel.readCSVFile("src/main/java/model/teilnehmerliste.csv");
        }catch (FileNotFoundException e){
            System.out.println("file is not found !!!");
        }


        // Assert that the count of elements in the participants list is as expected
        int expectedParticipantCount = 164;
        int actualParticipantCount = participantModel.participants.size();
        assertEquals(expectedParticipantCount, actualParticipantCount);

    }


    @Test
        void testCountOfPairs(){
        try{
            participantModel.readCSVFile("src/main/java/model/teilnehmerliste.csv");
        }catch (FileNotFoundException e){
            System.out.println("file is not found !!!");
        }
        // Assert that the count of elements in the groups list is as expected
        int expectedGroupCount = 73;
        int actualGroupCount = participantModel.pairs.size();
        assertEquals(expectedGroupCount, actualGroupCount);
    }

    @Test
        void fileNotFoundTest(){
        assertThrows(FileNotFoundException.class,  () -> {
            // code that should throw FileNotFoundException
            participantModel.readCSVFile("src/main/java/model/teilnehmerliste_Fake.csv");
        });
    }

    @Test
        void checkPairFoodPreference(){
        Participant egali = new Participant(FoodPreference.none);
        Participant egali2 = new Participant(FoodPreference.none);
        Participant fleichi = new Participant(FoodPreference.meat);
        Participant veggie = new Participant(FoodPreference.veggie);
        Participant vegan = new Participant(FoodPreference.vegan);

        Pair egaliPair = new Pair(egali,egali2);
        Pair pair1 = new Pair(veggie,fleichi);
        Pair pair2 = new Pair(vegan,fleichi);
        Pair pair3 = new Pair(fleichi,egali);
        Pair pair4 = new Pair(egali,fleichi);
        Pair pair5 = new Pair(egali,veggie);
        Pair pair6 = new Pair(egali,vegan);
        assertEquals(FoodPreference.none, egaliPair.getMainFoodPreference());
        assertEquals(FoodPreference.vegan, pair1.getMainFoodPreference());
        assertEquals(FoodPreference.vegan, pair2.getMainFoodPreference());
        assertEquals(FoodPreference.vegan, pair5.getMainFoodPreference());
        assertEquals(FoodPreference.vegan, pair6.getMainFoodPreference());
        assertEquals(FoodPreference.meat, pair3.getMainFoodPreference());
        assertEquals(FoodPreference.meat, pair4.getMainFoodPreference());
    }

    @Test
        void checkPairAgeDifference(){
        Participant person1 = new Participant(26);
        Participant person2 = new Participant(32);
        Pair pair = new Pair(person1,person2);
        assertEquals(2,pair.getAgeDifference());
    }

}
