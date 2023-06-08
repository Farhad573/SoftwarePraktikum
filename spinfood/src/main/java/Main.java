import model.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static model.CSVFileReader.getPairs;
import static model.CSVFileReader.getParticipants;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
       CSVFileReader CSVFileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        PartyLocation partyLocation = new PartyLocation();
        List<Pair> csvPairs = getPairs();
        try {
            CSVFileReader.readCSVFile("teilnehmerliste.csv");
            partyLocation.readCSVFilePartyLocation("partylocation.csv");
            System.out.println(partyLocation.toString());
            //System.out.println(CSVFileReader.toStringParticipants());
            //System.out.println(CSVFileReader.toStringPairs());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");

        int participantsLength = getParticipants().size();
        System.out.println("Length of participants list is: " + participantsLength);

        System.out.println("************************************************");

        int pairLength = getPairs().size();
        System.out.println("Length of pair list is: " + pairLength);

        System.out.println("************************************************");
        int count = participantsLength + pairLength ;
        System.out.println("the count of all members : " + count);

        System.out.println("###############################################");
        System.out.println(getParticipants().size());

        System.out.println("###############################################");
        List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants());
        System.out.println("number of generated Pairs is " + initialPair.size());
        System.out.println("number of successor is " + model.CSVFileReader.getSuccessor().size());
        System.out.println("###############################################");
        System.out.println("number of initial pairs from initial population generator is " + initialPair.size() );
        System.out.println("number of pairs from CSV is " + csvPairs.size());
        System.out.println("###############################################");



        List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair,csvPairs);
        groupGenerator.pairsSortedBasedOnDistance(concatenatedlist);
        System.out.println("number of all Pairs (1ka3) is " + concatenatedlist.size());
        List<Group> groupList = groupGenerator.makeStarterGroups(concatenatedlist,1);
//        System.out.println(groupList);
        List<Pair> pairsWhoCookInStarter = groupList.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Set<Pair> hashsetForStarter = new HashSet<>(pairsWhoCookInStarter);
        System.out.println("check if we have duplicates in list of pairs who cook");
        System.out.println(pairsWhoCookInStarter.size() == hashsetForStarter.size());
        System.out.println(" number of pairs who cook in starter -> " + pairsWhoCookInStarter.size());
     System.out.println("Number of generated groups in starter is " + groupList.size());
        List<Group> groupList1 = groupGenerator.makeMainDishGroups(concatenatedlist, 1);
        List<Pair> pairsWhoCookInMainDish = groupList1.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
     System.out.println("Number of generated groups in Maindish is " + groupList1.size());
        List<Group> desertGroups = groupGenerator.makeDessertGroups(concatenatedlist);
        System.out.println("Number of generated groups in dessert -> " + desertGroups.size());
//        for (Pair pair: concatenatedlist
//             ) {
//            System.out.println(pair);
//        }

    }
}
