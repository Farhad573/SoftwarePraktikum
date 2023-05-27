import model.CSVFileReader;
import model.Pair;
import model.PairGenerator;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       CSVFileReader CSVFileReader = new CSVFileReader();



        try {
            CSVFileReader.readCSVFile("teilnehmerliste.csv");
            //System.out.println(CSVFileReader.toStringParticipants());
            //System.out.println(CSVFileReader.toStringPairs());
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

        System.out.println("###############################################");

        PairGenerator pairGenerator = new PairGenerator();
        List<Pair> initialPair = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());

        System.out.println(initialPair.size());
//        for (Pair pair:
//                pairGenerator.generateNextGeneration(initialPair)
//             ) {
//            System.out.println(pair);
//        }


//        System.out.println(initialPair.size());
//        for (Pair pair:
//             initialPair) {
//
//            System.out.println(pair);
//
//        }

        for (Pair pair:
                pairGenerator.generateNextGeneration(initialPair)
             ) {
            System.out.println(pair);
            System.out.println();
        }

        // Test : 1- Kitchen , if no-no-Kitchen are not together
        //        2- Kitchen Location should be not more than 3 same Obj.
        //        3- Duplicate from Participant to make pair ->
        //        4- Food Preferences
        //        5- Age Rage
        //        6- make pair same participant

    }
}
