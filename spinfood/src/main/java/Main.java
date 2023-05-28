import model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static model.FitnessEvaluator.checkFoodPreferenceFitness;
import static model.FitnessEvaluator.checkKitchenFitness;

public class Main {
    public static void main(String[] args) {
       CSVFileReader CSVFileReader = new CSVFileReader();



        try {
            CSVFileReader.readCSVFile("teilnehmerliste.csv");
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

        System.out.println("###############################################");

        PairGenerator pairGenerator = new PairGenerator();
        List<Pair> initialPair = pairGenerator.generateInitialPopulation(CSVFileReader.getParticipants());
        System.out.println("number of successor is " + PairGenerator.getSuccessor().size());

//        List<Participant> participantsWithKitchen = CSVFileReader.getParticipants().stream()
//                .filter(p -> p.getKitchen() != null)
//                .collect(Collectors.toList());
//
//        Map<Kitchen, Long> countByAge = participantsWithKitchen.stream()
//                .collect(Collectors.groupingBy(Participant::getKitchen, Collectors.counting()));
//        for (Map.Entry<Kitchen, Long> entry : countByAge.entrySet()
//             ) {
//            System.out.println(entry.getValue());
//        }
//        for (Participant participant: PairGenerator.getSuccessor()
//             ) {
//            System.out.println(participant);
//        }

//        System.out.println(initialPair.size());
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
//
//        for (Pair pair:
//                pairGenerator.generateNextGeneration(initialPair)
//             ) {
//            System.out.println(pair);
//            System.out.println();
//        }
//            List<Integer> list = new ArrayList<>();
//            for(int i = 1; i<10 ; i++){
//                list.add(i);
//            }
//        System.out.println(list.size());
//        for (int i = 0; i < list.size(); i++) {
//
//            for (int j = i + 1; j < list.size(); j++) {
//                System.out.println("i is " + i + " and j is " + j);
//            }
//        }


        // Test : 1- Kitchen , if no-no-Kitchen are not together
        //        2- Kitchen Location should be not more than 3 same Obj.
        //        3- Duplicate from Participant to make pair ->
        //        4- Food Preferences
        //        5- Age Rage
        //        6- make pair same participant

    }
}
