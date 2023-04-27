package model;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantModelTest {

    ParticipantModel participantModel = new ParticipantModel();


    @Test
    public void testReadCSVData_InvalidData() throws IOException {
        // Create a temporary file with invalid test data
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
        FileWriter writer = new FileWriter(tempFile);
        writer.write("id,name,age,has_kitchen,foodPreference,sex\n");
        writer.write("004670cb-47f5-40a4-87d8-5276c18616ec,Person1,21,maybe,veggie,male\n");
        writer.close();

        // Call the readCSVData_1 method with the test file
        try {
            participantModel.readCSVData_1(tempFile.getPath());
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException ex) {
            // Exception expected
        }
    }

    @Test
    public void testReadCSVData_EmptyFile() throws IOException {
        // Create a temporary file with no data
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();

        // Call the readCSVData_1 method with the empty file
        participantModel.readCSVData_1(tempFile.getPath());

        // Verify that the participants list is empty
        List<Participant> participants = participantModel.getParticipants();
        assertEquals(0, participants.size());
    }


    @Test
    public void testReadCSVData_1() throws IOException {
        // Create a temporary file with test data
        File tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
        FileWriter writer = new FileWriter(tempFile);
        writer.write("id,name,age,foodPreference,,sex,has_kitchen\n");
        writer.write("0,1,danial,meat,29,male,yes\n");
        writer.write("0,2,mohsen,vegan,26,female,yes\n");
        writer.write("0,3,farhad,meat,28,male,no\n");
        writer.write("0,4,toni,meat,26,male,yes\n");

        writer.close();

        // Call the readCSVData_1 method with the test file
        participantModel.readCSVData_1(tempFile.getPath());

        // Assert that the participants list has the expected size
        assertEquals(4, participantModel.participants.size());

        // Assert that the participants list contains the expected participants
        Participant p1 = participantModel.participants.get(0);
        assertEquals("1", p1.getId());
        assertEquals("danial", p1.getName());
        assertEquals(29, p1.getAge());
        assertEquals(Sex.male, p1.getSex());
        assertEquals(HasKitchen.yes, p1.getHasKitchen());

        Participant p2 = participantModel.participants.get(1);
        assertEquals("2", p2.getId());
        assertEquals("mohsen", p2.getName());
        assertEquals(FoodPreference.vegan, p2.getFoodPreference());
        assertEquals(26, p2.getAge());
        assertEquals(Sex.female, p2.getSex());
        assertEquals(HasKitchen.yes, p2.getHasKitchen());

        Participant p3 = participantModel.participants.get(2);
        assertEquals("3", p3.getId());
        assertEquals("farhad", p3.getName());
        assertEquals(FoodPreference.meat, p3.getFoodPreference());
        assertEquals(28, p3.getAge());
        assertEquals(Sex.male, p3.getSex());
        assertEquals(HasKitchen.no, p3.getHasKitchen());

        Participant p4 = participantModel.participants.get(3);
        assertEquals("4", p4.getId());
        assertEquals("toni", p4.getName());
        assertEquals(FoodPreference.meat, p4.getFoodPreference());
        assertEquals(26, p4.getAge());
        assertEquals(Sex.male, p4.getSex());
        assertEquals(HasKitchen.yes, p4.getHasKitchen());
    }


}
