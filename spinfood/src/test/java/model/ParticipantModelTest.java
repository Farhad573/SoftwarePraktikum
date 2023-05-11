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

}
