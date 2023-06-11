package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PairGenerator extends ParticipantManager {




    /**
     * Generates the initial population of pairs from a list of participants.
     *
     * @param participants The list of participants from which to generate the initial population.
     * @return The list of pairs representing the initial population.
     */
    public static List<Pair> generateInitialPopulation(List<Participant> participants) {
        HashSet<Participant> usedParticipants = new HashSet<>();
        calculateWGCount();
        for (int i = 0; i < participants.size(); i++) {
            Participant participant1 = participants.get(i);

            for (int j = i + 1; j < participants.size(); j++) {
                Participant participant2 = participants.get(j);

                if (!usedParticipants.contains(participant1) &&
                        !usedParticipants.contains(participant2)) {
                    Pair pair = new Pair(participant1, participant2, false);

                    double fitness = PairFitnessEvaluator.evaluateFitness(pair,kitchenMap);
                    if (fitness > 4.0) {
                        generatedPairs.add(pair);
                        usedParticipants.add(participant1);
                        usedParticipants.add(participant2);
                    }
                }
            }
        }

        pairSuccessors = participants.stream().filter(x -> !usedParticipants.contains(x)).collect(Collectors.toList());
        return generatedPairs;
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
        ParticipantManager.pairs = pairs;
        return pairs;
    }
    public static void calculateWGCount(){
        for (Pair pair:CSV_Pairs) {
            Kitchen kitchen = pair.getKitchen();
            List<Pair> pairList = kitchenMap.getOrDefault(kitchen, new ArrayList<>());
            pairList.add(pair);
            kitchenMap.put(kitchen, pairList);
        }
    }




    public static String makeIndicatorForPairsList(List<Pair> pairs){
        String indicator = "";
        int pairSize = pairs.size();
        int successorSize = pairSuccessors.size();
        double sexDeviation = 0 ;
        double ageDifference = 0 ;
        double preferenceDeviation = 0 ;

        for (Pair pair : pairs  ){
            sexDeviation += pair.getSexDeviation();
            ageDifference += pair.getAgeDifference();
            preferenceDeviation += pair.getPreferenceDeviation();
        }

        sexDeviation = sexDeviation / pairSize;
        double averageSexDeviation = 0;
        for(Pair pair : pairs){
            averageSexDeviation = Math.abs(pair.getSexDeviation() - sexDeviation);
        }
        averageSexDeviation = averageSexDeviation / pairSize;
        ageDifference = ageDifference / pairSize;
        preferenceDeviation = preferenceDeviation / pairSize;

        DecimalFormat df = new DecimalFormat("#.##");
        return indicator + pairSize + " _ "  + successorSize + " _ " + df.format(averageSexDeviation)+ " _ " + df.format(ageDifference) + " _ " + df.format(preferenceDeviation);
    }


}
