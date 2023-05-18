
import model.CSVFileReader;
import model.Kitchen;
import model.Location;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        CSVFileReader CSVFileReader = new CSVFileReader();

        try {
            CSVFileReader.readCSVFile("src/main/java/model/teilnehmerliste.csv");
            System.out.println(CSVFileReader.toStringParticipants());
            System.out.println(CSVFileReader.toStringPairs());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");

        int participantsLength = CSVFileReader.getParticipants().size();
        System.out.println("Length of participants list is: " + participantsLength);

        System.out.println("************************************************");

        int pairLength = CSVFileReader.getPairs().size();
        System.out.println("Length of pair list is: " + pairLength);

        System.out.println("************************************************");
        int count = participantsLength + pairLength ;
        System.out.println("the count of all members : " + count);







    }
}
