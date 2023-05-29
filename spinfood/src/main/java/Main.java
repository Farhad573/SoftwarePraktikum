import model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static model.CSVFileReader.getParticipants;
import static model.FitnessEvaluator.checkFoodPreferenceFitness;
import static model.FitnessEvaluator.checkKitchenFitness;
import static model.GroupGenerator.*;
import static model.ParticipantManager.*;

public class Main {
    public static void main(String[] args) {
       CSVFileReader CSVFileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();

        try {
            CSVFileReader.readCSVFile("src/main/resources/teilnehmerlisteNew.csv");
            //System.out.println(CSVFileReader.toStringParticipants());
            //System.out.println(CSVFileReader.toStringPairs());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");

        int participantsLength = getParticipants().size();
        System.out.println("Length of participants list is: " + participantsLength);

        System.out.println("************************************************");

        int pairLength = CSVFileReader.getPairs().size();
        System.out.println("Length of pair list is: " + pairLength);

        System.out.println("************************************************");
        int count = participantsLength + pairLength ;
        System.out.println("the count of all members : " + count);

        System.out.println("###############################################");
        System.out.println(getParticipants().size());

        System.out.println("###############################################");
        List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants());
        System.out.println("number of generated Pairs is " + initialPair.size());
        System.out.println("number of successor is " + CSVFileReader.getSuccessor().size());

        System.out.println("###############################################");
//        for (Participant pair:
//                CSVFileReader.getParticipants()) {
//            System.out.println(pair);
//        }
//        for (Pair pair:
//                initialPair) {
//            System.out.println(pair);
//        }
        System.out.println("###############################################");
        List<Group> groupList = groupGenerator.generateGroup(initialPair,3);
        System.out.println("Number of generated groups is " + groupList.size());
        System.out.println("number of groupSuccessors is " + getGroupSuccessors().size());






    }
}
