import java.io.*;
import java.util.Scanner;


import static org.junit.jupiter.api.Assertions.*;

class ParticipantModelTest {

    @org.junit.jupiter.api.Test
    void readCSV() throws IOException {
        ParticipantModel model = new ParticipantModel();
        model.readCSV("src/main/resources/teilnehmerliste.csv");
        try (FileWriter writer = new FileWriter("src/main/resources/csvTest.csv")) {
            // Write header
            writer.write(",ID,Name,FoodPreference,Age,Sex,Kitchen,Kitchen_Story,Kitchen_Longitude,Kitchen_Latitude,ID_2,Name_2,Age_2,Sex_2\n");

            int lineNumber = 0;
            // Write person record
            for (Participant person : model.getParticipantList()) {
                writer.write(lineNumber + ",");
                writer.write(person.getID() + "," + person.getName() + "," + person.getFoodPreference() + "," +
                        person.getAge() + "," +
                        person.getSex() + "," +
                        person.getHas_kitchen() + ",");
                double floor = person.getKitchen().getFloor();
                double longitude = person.getKitchen().getLocation().getLongitude();
                String ID_2 = person.getID_2();
                if (floor == 0) {
                    writer.write("" + ",");
                } else {
                    writer.write(floor + ",");
                }
                if (longitude == 0.0) {
                    writer.write("" + "," + "" + ",");
                } else {
                    writer.write(person.getKitchen().getLocation().getLongitude() + "," + person.getKitchen().getLocation().getLatitude() + ",");
                }

                if (ID_2 == "0") {
                    writer.write("" + "," + "" + "," + "" + "," + "" + "\n");
                } else {
                    writer.write(person.getID_2() + "," +
                            person.getName_2() + "," +
                            person.getAge_2() + "," +
                            person.getSex_2() + "\n");
                }


                lineNumber++;
            }
            //writer.write("hi");

        } catch (IOException e) {
            e.printStackTrace();
        }

//        try (FileWriter writer = new FileWriter("src/main/resources/csvTest2.csv")) {
//            // Write header
//            writer.write(",ID,Name,FoodPreference,Age,Sex,Kitchen,Kitchen_Story,Kitchen_Longitude,Kitchen_Latitude,ID_2,Name_2,Age_2,Sex_2\n");
//
//        }


        String expectedFilePath = "src/main/resources/teilnehmerliste.csv";
        String actualFilePath = "src/main/resources/csvTest.csv";


//        BufferedReader reader1 = new BufferedReader(new FileReader(expectedFilePath));
//        BufferedReader reader2 = new BufferedReader(new FileReader(actualFilePath));
//
//        String line1 = reader1.readLine();
//        String line2 = reader2.readLine();
//        int lineNumber = 1;
//
//        while (line1 != null && line2 != null) {
//            assertEquals(line1, line2);
//            line1 = reader1.readLine();
//            line2 = reader2.readLine();
//            lineNumber++;
//        }
//
//
//        reader1.close();
//        reader2.close();

        Scanner scanner1 = new Scanner(new File(expectedFilePath));
        Scanner scanner2 = new Scanner(new File(actualFilePath));

        int lineNumber = 1;

        while (scanner1.hasNextLine() && scanner2.hasNextLine()) {
            String line1 = scanner1.nextLine();
            String line2 = scanner2.nextLine();
            assertEquals( line1, line2);
            lineNumber++;
        }

        // Check if both files have reached end
       assertEquals(scanner1.hasNextLine(), scanner2.hasNextLine());

        scanner1.close();
        scanner2.close();

    }
}




