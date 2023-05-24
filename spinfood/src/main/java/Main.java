
import model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//       CSVFileReader CSVFileReader = new CSVFileReader();
//
//        try {
//            CSVFileReader.readCSVFile("teilnehmerliste.csv");
//            System.out.println(CSVFileReader.toStringParticipants());
//            System.out.println(CSVFileReader.toStringPairs());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("**************************************************");
//
//        int participantsLength = CSVFileReader.getParticipants().size();
//        System.out.println("Length of participants list is: " + participantsLength);
//
//        System.out.println("************************************************");
//
//        int pairLength = CSVFileReader.getPairs().size();
//        System.out.println("Length of pair list is: " + pairLength);
//
//        System.out.println("************************************************");
//        int count = participantsLength + pairLength ;
//        System.out.println("the count of all members : " + count);


        List <Integer>list = new ArrayList<>();
        for(int i = 0; i<= 10;i++){
            list.add(i);
        }
        for (int i = 0; i < list.size() - 1; i += 2) {
            int participant1 = list.get(i);
            int participant2 = list.get(i + 1);
            System.out.println(participant1 +  " + " + participant2);
        }



    }
}
