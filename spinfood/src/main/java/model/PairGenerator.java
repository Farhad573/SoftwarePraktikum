package model;

import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

import static model.FitnessEvaluator.*;

public class PairGenerator {
    private static Random random = new Random();
    private static final int TOURNAMENT_SIZE = 3;
    private final double CROSSOVER_RATE = 0.8;
    private final double MUTATION_RATE = 0.1;
    private static double generatedFitness = 0;
    private  static Set<Participant> hashSet = new HashSet<>();
    private List<Pair> initialPopulation = new ArrayList<>();
    private static List<Participant> successor = new ArrayList<>();
    //private  HardlyRepeatedRandomNumberGenerator numberGenerator = new HardlyRepeatedRandomNumberGenerator(0,this.initialPopulation.size() - 1);


    /**
    Generates the initial population of pairs from a list of participants.
    @param participants The list of participants from which to generate the initial population.
     @return The list of pairs representing the initial population.
     */
    public List<Pair> generateInitialPopulation(List<Participant> participants) {
    // make pair list randomly -> initial
       //List<Pair> population = new ArrayList<>();
//
//        // Shuffle the participants randomly
//        List<Participant> shuffledParticipants = new ArrayList<>(participants);
//        Collections.shuffle(shuffledParticipants, new Random());
//        // Iterate over the shuffled participants and create pairs
//        if (shuffledParticipants.size() % 2 == 0) {
//            for (int i = 0; i < shuffledParticipants.size(); i += 2) {
//                Participant participant1 = shuffledParticipants.get(i);
//                Participant participant2 = shuffledParticipants.get(i + 1);
//                Pair pair = new Pair(participant1, participant2, false);
//                population.add(pair);
//            }
//        } else {
//            for (int i = 0; i < shuffledParticipants.size() - 1; i += 2) {
//                Participant participant1 = shuffledParticipants.get(i);
//                Participant participant2 = shuffledParticipants.get(i + 1);
//                Pair pair = new Pair(participant1, participant2, false);
//                population.add(pair);
//            }
//
//        }
//        this.initialPopulation = population;
//        return population;
        List<Pair> population = new ArrayList<>();
        HashSet<Participant> usedParticipants = new HashSet<>();
        HashMap<Participant,Boolean> hashmap = new HashMap<>();
        for (Participant participant : participants){
            hashmap.put(participant,false);
        }
        int limit = participants.size() % 2 == 0? participants.size() : participants.size() - 1;
        for (int i = 0; i < participants.size(); i++) {
            Participant participant1 = participants.get(i);

            for (int j = i + 1; j < participants.size(); j++) {
                Participant participant2 = participants.get(j);

                Pair pair = new Pair(participant1, participant2, false);

                if (checkFoodPreferenceFitness(pair) && checkKitchenFitness(pair) && checkKitchenCount(pair) && !usedParticipants.contains(participant1) && !usedParticipants.contains(participant2) ) {
                    population.add(pair);
                    usedParticipants.add(participant1);
                    hashmap.put(participant1,true);
                    usedParticipants.add(participant2);
                    hashmap.put(participant2,true);
                }
        }
        }

//        long num = hashmap.entrySet().stream().filter(x -> x.getValue() == false).count();
//        System.out.println("amount of  used participants " + usedParticipants.size());
//        System.out.println("amount of not used participants " + num);
        //hashmap.entrySet().stream().filter(x-> x.getValue()==false).forEach(System.out::println);


        successor = hashmap.entrySet().stream().filter(x-> x.getValue()==false).map(x -> x.getKey()).collect(Collectors.toList());


        return this.initialPopulation = population;
    }

    public static List<Participant> getSuccessor() {
        return successor;
    }

    public List<Pair> getInitialPopulation() {
        return initialPopulation;
    }

}
