package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ParticipantManager class is responsible for managing participants, pairs, and successors.
 * It provides functionality to add participants, create pairs, and maintain a list of successors.
 */
public class ParticipantManager {


    protected static List<Participant> participants;
    protected static List<Pair> CSV_Pairs;

    protected static List<Pair> pairs;
    protected static List<Participant> pairSuccessors;
    protected static List<Group> generatedGroups;
    protected static List<Pair> starterSuccessors;

    protected static List<Pair> generatedPairs;
    protected static List<Group> generatedGroupsinStarter;
    protected static List<Group> generatedGroupsInMainDish;
    protected static List<Group> generatedGroupsInDessert;



    protected static Map<Kitchen,Integer> kitchenCountMap;
    public static Map<Kitchen,List<Pair>> kitchenMap;


    /**
     * Constructs a new ParticipantManager object.
     * Initializes the lists for participants, pairs and successors.
     */
    public ParticipantManager(){
        participants = new ArrayList<>();
        pairSuccessors = new ArrayList<>();
        pairs = new ArrayList<>();
        generatedPairs = new ArrayList<>();
        kitchenCountMap = new HashMap<>();
        generatedGroups = new ArrayList<>();
        starterSuccessors = new ArrayList<>();
        kitchenMap = new HashMap<>();
        CSV_Pairs = new ArrayList<>();
        generatedGroupsinStarter = new ArrayList<>();
        generatedGroupsInMainDish = new ArrayList<>();
        generatedGroupsInDessert = new ArrayList<>();
    }

    public static List<Group> getGeneratedGroups() {
        return generatedGroups;
    }

    public static List<Participant> getParticipants() {
        return participants;
    }

    public static List<Pair> getCSV_Pairs() {
        return CSV_Pairs;
    }

    public static List<Pair> getPairs() {
        return pairs;
    }

    public static List<Participant> getPairSuccessors() {
        return pairSuccessors;
    }

    public static List<Pair> getStarterSuccessors() {
        return starterSuccessors;
    }

    public static List<Pair> getGeneratedPairs() {
        return generatedPairs;
    }

    public static Map<Kitchen, Integer> getKitchenCountMap() {
        return kitchenCountMap;
    }

    public static Map<Kitchen, List<Pair>> getKitchenMap() {
        return kitchenMap;
    }

    public static List<Group> getGeneratedGroupsinStarter() {
        return generatedGroupsinStarter;
    }

    public static List<Group> getGeneratedGroupsInMainDish() {
        return generatedGroupsInMainDish;
    }

    public static List<Group> getGeneratedGroupsInDessert() {
        return generatedGroupsInDessert;
    }
}
