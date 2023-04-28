package model;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParticipantModelTest {

    ParticipantModel participantModel = new ParticipantModel();

    // check the count of elements in the lists
    @Test
    public void testCountOfElements() throws FileNotFoundException {
        // Create an instance of the class under test

        // Call the method that reads CSV data
        participantModel.readCSVData_2("src/main/java/model/teilnehmerliste.csv");

        // Assert that the count of elements in the groups list is as expected
        int expectedGroupCount = 73;
        int actualGroupCount = participantModel.groups.size();
        assertEquals(expectedGroupCount, actualGroupCount);

        // Assert that the count of elements in the participants list is as expected
        int expectedParticipantCount = 237;
        int actualParticipantCount = participantModel.participants.size();
        assertEquals(expectedParticipantCount, actualParticipantCount);
    }
}
