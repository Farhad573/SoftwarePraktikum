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

        for (Pair pair:
                pairGenerator.generateNextGeneration(initialPair)
             ) {
            System.out.println(pair);
        }

    }
}
