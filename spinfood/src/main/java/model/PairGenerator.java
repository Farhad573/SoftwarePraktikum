package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PairGenerator extends ParticipantManager {

    private static Set<Participant> hashSet = new HashSet<>();
    private static List<Pair> initialPopulation = new ArrayList<>();

    /**
     * Generates the initial population of pairs from a list of participants.
     *
     * @param participants The list of participants from which to generate the initial population.
     * @return The list of pairs representing the initial population.
     */
    public static List<Pair> generateInitialPopulation(List<Participant> participants) {
        List<Pair> population = new ArrayList<>();
        HashSet<Participant> usedParticipants = new HashSet<>();
        for (int i = 0; i < participants.size(); i++) {
            Participant participant1 = participants.get(i);

            for (int j = i + 1; j < participants.size(); j++) {
                Participant participant2 = participants.get(j);

                if (!usedParticipants.contains(participant1) &&
                        !usedParticipants.contains(participant2)) {
                    Pair pair = new Pair(participant1, participant2, false);

                    double fitness = PairFitnessEvaluator.evaluateFitness(pair);
                    if (fitness > 4.0) {
                        population.add(pair);
                        usedParticipants.add(participant1);
                        usedParticipants.add(participant2);
                    }
                }
            }
        }

        pairSuccessors = participants.stream().filter(x -> !usedParticipants.contains(x)).collect(Collectors.toList());
        initialPopulation = population;
        return population;
    }

    /**
     * Combines two lists of pairs into a single list of pairs.
     *
     * @param l1 The first list of pairs.
     * @param l2 The second list of pairs.
     * @return The combined list of pairs.
     */
    public List<Pair> makeAllPairsTogether(List<Pair> l1, List<Pair> l2) {
        List<Pair> pairs = Stream.concat(l1.stream(), l2.stream())
                .collect(Collectors.toList());
        this.pairs = pairs;
        //?
        makeIndicatorForPairs(pairs);
        return pairs;
    }

    /**
     * it loops through the whole Pairs and calculate their Indicator(Kenzahl)
     *
     * @param list
     */
    public void makeIndicatorForPairs(List<Pair> list) {
        for (Pair pair : list
        ) {
            pair.indicator += list.size();
            pair.indicator += pairSuccessors.size();
            pair.indicator += pair.getSexDeviation();
            pair.indicator += pair.getAgeDifference();
            pair.indicator += pair.getPreferenceDeviation();
        }
    }


}
