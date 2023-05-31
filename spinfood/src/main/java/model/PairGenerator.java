package model;

import java.util.*;
import java.util.stream.Collectors;

import static model.FitnessEvaluator.*;

public class PairGenerator extends ParticipantManager {

    private  static Set<Participant> hashSet = new HashSet<>();
    private static List<Pair> initialPopulation = new ArrayList<>();




    /**
    Generates the initial population of pairs from a list of participants.
    @param participants The list of participants from which to generate the initial population.
     @return The list of pairs representing the initial population.
     */
    public static List<Pair> generateInitialPopulation(List<Participant> participants) {
        List<Pair> population = new ArrayList<>();
        HashSet<Participant> usedParticipants = new HashSet<>();
        int limit = participants.size() % 2 == 0? participants.size() : participants.size() - 1;
        for (int i = 0; i < participants.size(); i++) {
            Participant participant1 = participants.get(i);

            for (int j = i + 1; j < participants.size(); j++) {
                Participant participant2 = participants.get(j);

                Pair pair = new Pair(participant1, participant2, false);

                if (checkFoodPreferenceFitness(pair) && checkKitchenFitness(pair) && checkKitchenCount(pair) && !usedParticipants.contains(participant1) && !usedParticipants.contains(participant2) ) {
                    population.add(pair);
                    usedParticipants.add(participant1);
                    usedParticipants.add(participant2);
                }
        }
        }

        pairSuccessors = participants.stream().filter(x-> !usedParticipants.contains(x)).collect(Collectors.toList());
        initialPopulation = population;
        return population;
    }



}
