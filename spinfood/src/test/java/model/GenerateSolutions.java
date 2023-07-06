package model;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import model.GroupGenerator.UniqueGroupsResult;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.CSVFileReader.*;
import static model.ParticipantManager.getGeneratedGroups;

public class GenerateSolutions {
    //    a) 5 > 8 > 6 > 7 > 9
//    b) 9 > 5 > 8 > 7 > 6
//    c) 7 > 6 > 5 > 8 > 9
    int[] a = {1, 3, 4, 2, 5};
    int[] b = {2, 5, 4, 3, 1};
    int[] c = {3, 2, 1, 4, 5};
    JsonMaker jsonMaker = new JsonMaker();
    @Test
    public void generateSolution1() throws FileNotFoundException {
        CSVFileReader CSVFileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        PartyLocation partyLocation = new PartyLocation();
        List<Pair> csvPairs = getCSV_Pairs();
        try {
            CSVFileReader.readCSVFile("src/main/resources/teilnehmerlisteNew.csv");
            partyLocation.readCSVFilePartyLocation("src/main/resources/partylocation.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants(), a);
        List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair, csvPairs);
        System.out.println("Pair Kenzahlen in Solution1-> " + pairGenerator.makeIndicatorForPairsList(concatenatedlist));
        //groupGenerator.pairsSortedBasedOnDistance(concatenatedlist);
        groupGenerator.callGroupsGenerator(concatenatedlist, a, new Location(partyLocation.getLongitude(), partyLocation.getLatitude()));
        System.out.println("Group Kenzahlen in Solution1->" + GroupGenerator.makeIndicatorForGroupList(getGeneratedGroups()));
        jsonMaker.makeJsonObject("src/main/resources/solution1.json");
    }

    @Test
    public void generateSolution2() throws FileNotFoundException {
        CSVFileReader CSVFileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        PartyLocation partyLocation = new PartyLocation();
        List<Pair> csvPairs = getCSV_Pairs();
        try {
            CSVFileReader.readCSVFile("src/main/resources/teilnehmerlisteNew.csv");
            partyLocation.readCSVFilePartyLocation("src/main/resources/partylocation.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants(), b);
        List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair, csvPairs);
        System.out.println("Pair Kenzahlen in Solution2-> " + pairGenerator.makeIndicatorForPairsList(concatenatedlist));
        //groupGenerator.pairsSortedBasedOnDistance(concatenatedlist);
        groupGenerator.callGroupsGenerator(concatenatedlist, b, new Location(partyLocation.getLongitude(), partyLocation.getLatitude()));
        System.out.println("Group Kenzahlen in Solution2->" + GroupGenerator.makeIndicatorForGroupList(getGeneratedGroups()));
        jsonMaker.makeJsonObject("src/main/resources/solution2.json");
    }

    @Test
    public void generateSolution3() throws FileNotFoundException {
        CSVFileReader CSVFileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        PartyLocation partyLocation = new PartyLocation();
        List<Pair> csvPairs = getCSV_Pairs();
        try {
            CSVFileReader.readCSVFile("src/main/resources/teilnehmerlisteNew.csv");
            partyLocation.readCSVFilePartyLocation("src/main/resources/partylocation.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants(), c);
        List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair, csvPairs);
        System.out.println("Pair Kenzahlen in Solution3-> " + pairGenerator.makeIndicatorForPairsList(concatenatedlist));
        //groupGenerator.pairsSortedBasedOnDistance(concatenatedlist);
        groupGenerator.callGroupsGenerator(concatenatedlist, c, new Location(partyLocation.getLongitude(), partyLocation.getLatitude()));
        System.out.println("Group Kenzahlen in Solution3->" + GroupGenerator.makeIndicatorForGroupList(getGeneratedGroups()));
        jsonMaker.makeJsonObject("src/main/resources/solution3.json");
    }
}