import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.JsonValidator;
import com.networknt.schema.ValidationMessage;
import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static model.CSVFileReader.*;

public class Main {
    public static boolean checkDuplicatesinAllSteps(List<Group> starter,List<Group> main,List<Group> dessert){
        List<Pair> pairsWhoCookInStarter = starter.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Set<Pair> hashsetForStarter = new HashSet<>(pairsWhoCookInStarter);
        //System.out.println("check if we dont have duplicates in list of pairs who cook in starter");
        Boolean starterChecker = pairsWhoCookInStarter.size() == hashsetForStarter.size();
        System.out.println(" number of pairs who cook in starter -> " + pairsWhoCookInStarter.size());

        List<Pair> pairsWhoCookInMain = main.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Set<Pair> hashsetForMain = new HashSet<>(pairsWhoCookInMain);
        //System.out.println("check if we dont have duplicates in list of pairs who cook in Main");
        Boolean mainChecker = pairsWhoCookInMain.size() == hashsetForMain.size();
        System.out.println(" number of pairs who cook in main -> " + pairsWhoCookInMain.size());

        List<Pair> pairsWhoCookInDessert = dessert.stream()
                .flatMap(x -> x.getPairsInGroup().stream())
                .filter(Pair::isHaveCooked).toList();
        Set<Pair> hashsetForDessert = new HashSet<>(pairsWhoCookInDessert);
        //System.out.println("check if we dont have duplicates in list of pairs who cook in Dessert");
        Boolean dessertChecker = pairsWhoCookInDessert.size() == hashsetForDessert.size();
        System.out.println(" number of pairs who cook in dessert -> " + pairsWhoCookInDessert.size());
        return starterChecker && mainChecker && dessertChecker;

    }
    public static void main(String[] args) throws FileNotFoundException {
       CSVFileReader CSVFileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        PartyLocation partyLocation = new PartyLocation();
        Cancellation cancellation = new Cancellation();
        JsonMaker jsonMaker = new JsonMaker();
        List<Pair> csvPairs = getCSV_Pairs();
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

        int pairLength = getCSV_Pairs().size();
        System.out.println("Length of pair list is: " + pairLength);

        System.out.println("************************************************");
        int count = participantsLength + pairLength ;
        System.out.println("the count of all members : " + count);

        System.out.println("###############################################");

        System.out.println("###############################################");
        List<Pair> initialPair = pairGenerator.generateInitialPopulation(getParticipants(),new int[4]);
        System.out.println("number of generated Pairs is " + getGeneratedPairs().size());
        System.out.println("number of successor is " + model.CSVFileReader.getSuccessor().size());
        System.out.println("###############################################");
        System.out.println("number of initial pairs from initial population generator is " + initialPair.size() );
        System.out.println("number of pairs from CSV is " + csvPairs.size());
        System.out.println("###############################################");


        List<Pair> concatenatedlist = pairGenerator.makeAllPairsTogether(initialPair,csvPairs);
        System.out.println("pair kenZahl is -> " + pairGenerator.makeIndicatorForPairsList(concatenatedlist));

        //groupGenerator.pairsSortedBasedOnDistance(concatenatedlist);
        System.out.println("number of all Pairs (1ka3) is " + concatenatedlist.size());
//        List<Group> starterGroups = groupGenerator.makeStarterGroups(concatenatedlist,1);
//        List<Pair> pairsInStarter = starterGroups.stream().flatMap(x -> x.getPairsInGroup().stream()).collect(Collectors.toCollection(ArrayList::new));
//        System.out.println("number of pairs in starter is " + pairsInStarter.size());
//        System.out.println(pairsInStarter.stream().filter(x-> x.getMetPairsInStarter().size() == 2).count());
//        List<Group> mainDishGroup = groupGenerator.makeMainDishGroups(pairsInStarter, 4);
//        List<Group> desertGroups = groupGenerator.makeDessertGroups(pairsInStarter);
        int[] numbers = {4,4,4,4,5};
        groupGenerator.callGroupsGenerator(concatenatedlist,numbers,new Location(partyLocation.getLongitude(),partyLocation.getLatitude()));


        System.out.println("Number of generated groups in starter is " + getGeneratedGroupsinStarter().size());
        System.out.println("Number of generated groups in Maindish is " + getGeneratedGroupsInMainDish().size());
        System.out.println("Number of generated groups in dessert -> " + getGeneratedGroupsInDessert().size());
        System.out.println("number of pairs in starterMap is " + GroupGenerator.kitchenLocationsInStarter.keySet().size());
        System.out.println("number of pairs in MainDishMap is " + GroupGenerator.kitchenLocationsInMainDish.keySet().size());
        System.out.println("number of pairs in DessertMap is " + GroupGenerator.kitchenLocationsInDessert.keySet().size());

        System.out.println("Group Kenzahl is -> " + GroupGenerator.makeIndicatorForGroupList(getGeneratedGroups()));
        List<Participant> cancelled = new ArrayList<>();
        cancelled.add(getGeneratedGroups().get(0).getPairsInGroup().get(0).getPerson1());
        cancelled.add(getGeneratedGroups().get(0).getPairsInGroup().get(0).getPerson2());
        List<Participant> tmp = new ArrayList<>();
        tmp.addAll(cancelled);
        boolean check1 = false;
        for (Group group: getGeneratedGroups()
        ) {
            for (Pair pair: group.getPairsInGroup()
            ) {
                for (Participant person: pair.getParticipantsInPair()
                ) {
                    if(person.equals(tmp.get(0)) || person.equals(tmp.get(1))){
                        check1 = true;
                    }
                }
            }

        }
        System.out.println("check1 is: " + check1);
        cancellation.cancelPerson(cancelled,getPairs(),getGeneratedGroups(),5,numbers,new Location(partyLocation.getLongitude(),partyLocation.getLatitude()));
        boolean check2 = false;
        for (Group group: getGeneratedGroups()
             ) {
            for (Pair pair: group.getPairsInGroup()
                 ) {
                for (Participant person: pair.getParticipantsInPair()
                     ) {
                    if(person.equals(tmp.get(0)) || person.equals(tmp.get(1))){
                        check2 = true;
                    }
                }
            }

        }
        System.out.println("check2 is: " + check2);
        //jsonMaker.makeJsonObject();










    }

}
