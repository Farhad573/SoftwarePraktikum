import model.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static model.CSVFileReader.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        CSVFileReader CSVFileReader = new CSVFileReader();
        PairGenerator pairGenerator = new PairGenerator();
        GroupGenerator groupGenerator = new GroupGenerator();
        PartyLocation partyLocation = new PartyLocation();
        Cancellation cancellation = new Cancellation();
        JsonMaker jsonMaker = new JsonMaker();
        List<Pair> csvPairs = getCSV_Pairs();
        try {
            CSVFileReader.readCSVFile("src/main/resources/teilnehmerlisteNew.csv");
            partyLocation.readCSVFilePartyLocation("src/main/resources/partylocation.csv");
            System.out.println(partyLocation);
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
        //List<Group> starterGroups = groupGenerator.makeStarterGroups(concatenatedlist,new int[]{1,2,5,3,4});
        //List<Pair> pairsInStarter = starterGroups.stream().flatMap(x -> x.getPairsInGroup().stream()).collect(Collectors.toCollection(ArrayList::new));
       // System.out.println("number of pairs in starter is " + pairsInStarter.size());
       // System.out.println(pairsInStarter.stream().filter(x-> x.getMetPairsInStarter().size() == 2).count());
        //List<Group> mainDishGroup = groupGenerator.makeMainDishGroups(pairsInStarter, 4);
       //List<Group> desertGroups = groupGenerator.makeDessertGroups(pairsInStarter);
//        a) 5 > 8 > 6 > 7 > 9
//        b) 9 > 5 > 8 > 7 > 6
//        c) 7 > 6 > 5 > 8 > 9
        int[] numbers = {1,1,1,1,1};
        groupGenerator.callGroupsGenerator(concatenatedlist,numbers,new Location(partyLocation.getLongitude(),partyLocation.getLatitude()));




        System.out.println("Group Kenzahl is -> " + GroupGenerator.makeIndicatorForGroupList(getGeneratedGroups()));
        //jsonMaker.makeJsonObject();


//        Map<Pair, List<Group>> map = groupGenerator.makePairGroups(concatenatedlist, numbers);
//     /**   System.out.println(map.size());
//        int counter = 0;
//        for(List<Group> l: map.values()) {
//            counter++;
//            System.out.println(l.size());
//        }
//
//      */
//
//        GroupGenerator.UniqueGroupsResult starter = groupGenerator.findUniqueStarterGroups(map);
//        System.out.println(starter.getUniqueStarterGroups().size());
//      //  for (Group g : starter.getUniqueStarterGroups()) {
//      //      System.out.println(g.getPairsInGroup());
//      //  }
//        System.out.println("****************");
//        System.out.println("****************");
//        System.out.println("****************");
//        System.out.println("****************");
//
//        System.out.println(starter.getUniqueMainGroups().size());
//      //  for (Group g : starter.getUniqueMainGroups()) {
//        //    System.out.println(g.getPairsInGroup());
//      //  }
//
//        System.out.println("****************");
//        System.out.println("****************");
//        System.out.println("****************");
//        System.out.println("****************");
//
//        System.out.println(starter.getUniqueDessertGroups().size());
//      //  for (Group g : starter.getUniqueDessertGroups()) {
//        //    System.out.println(g.getPairsInGroup());
//     //   }
//
//        System.out.println("****************");
//        System.out.println("****************");
//        System.out.println("****************");
//        System.out.println("****************");
//
//        System.out.println(starter.getSuccessors().size());
//       // for (Pair g : starter.getSuccessors()) {
//       //     System.out.println(g);
//       // }


    }

}
