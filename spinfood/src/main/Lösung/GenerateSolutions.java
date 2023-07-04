import model.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static model.CSVFileReader.getCSV_Pairs;
import static model.CSVFileReader.getParticipants;

public class GenerateSolutions {
    public static void main(String[] args) {
        CSVFileReader CSVFileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        PartyLocation partyLocation = new PartyLocation();
        JsonMaker jsonMaker = new JsonMaker();
        List<Pair> csvPairs = getCSV_Pairs();

        try {
            CSVFileReader.readCSVFile("spinfood/teilnehmerliste.csv");
            partyLocation.readCSVFilePartyLocation("spinfood/partylocation.csv");
            System.out.println(partyLocation);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("**************************************************");

        List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants(),new int[4]);
        List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair,csvPairs);

        int[] ersteOrdnung = {1,3,4,2,5};
        int[] zweiteOrdnung = {2,5,4,3,1};
        int[] dritteOrdnung = {3,2,1,4,5};

        Map<Pair, List<Group>> map1 = groupGenerator.makePairGroups(concatenatedlist, ersteOrdnung);
        GroupGenerator.UniqueGroupsResult ersteLoesung = groupGenerator.findUniqueStarterGroups(map1);


        Map<Pair, List<Group>> map2 = groupGenerator.makePairGroups(concatenatedlist, ersteOrdnung);
        GroupGenerator.UniqueGroupsResult zweiteLoesung = groupGenerator.findUniqueStarterGroups(map2);


        Map<Pair, List<Group>> map3 = groupGenerator.makePairGroups(concatenatedlist, ersteOrdnung);
        GroupGenerator.UniqueGroupsResult dritteLoesung = groupGenerator.findUniqueStarterGroups(map3);

    }
}
