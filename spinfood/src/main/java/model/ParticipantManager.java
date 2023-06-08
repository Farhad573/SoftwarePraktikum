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
    protected static List<Pair> pairs;
    protected static List<Participant> pairSuccessors;
    protected static List<Group> groups;
    protected static List<Pair> starterSuccessors;
    protected static List<Pair> mainDishSuccessors;
    protected static List<Pair> dessertSuccessors;



    protected static Map<Kitchen,Integer> kitchenCountMap;


    /**
     * Constructs a new ParticipantManager object.
     * Initializes the lists for participants, pairs and successors.
     */
    public ParticipantManager(){
        participants = new ArrayList<>();
        pairSuccessors = new ArrayList<>();
        pairs = new ArrayList<>();
        kitchenCountMap = new HashMap<>();
        groups = new ArrayList<>();
        starterSuccessors = new ArrayList<>();
        mainDishSuccessors = new ArrayList<>();
        dessertSuccessors = new ArrayList<>();
    }


}
