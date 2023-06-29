package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static model.HasKitchen.maybe;
import static model.HasKitchen.no;
import static model.PairGenerator.calculateWGCount;

/**
 * The Cancellation class manages participant cancellations and generates new pairs/groups accordingly.
 * It extends the ParticipantManager class.
 */
public class Cancellation extends ParticipantManager {

    List<Pair> newPairs = new ArrayList<>();

    /**
     * Cancels the specified participants and generates new pairs/groups based on the updated participant list.
     *
     * @param cancelledParticipants The list of participants to be cancelled.
     * @param pairs          The list of all pairs.
     * @param groups         the list of all groups.
     * @param maximum        The maximum number of participants.
     */
    public void cancelPerson(List<Participant> cancelledParticipants, List<Pair> pairs,List<Group> groups ,int maximum,int[] numbers,Location partyLocation) throws FileNotFoundException {
        for (int i = 0;i<cancelledParticipants.size();i++) {
            Participant cancelledPerson = cancelledParticipants.get(i);
//            if(participants.contains(cancelledPerson)){
//                Pair pair = pairs.stream().filter(x-> x.getParticipantsInPair().contains(cancelledPerson)).findFirst().orElse(null);
//                cancelIfSingleRegistered(cancelledPerson,pair,pairs,numbers,partyLocation);
//            }
            Pair pair = pairs.stream().filter(x-> x.getParticipantsInPair().contains(cancelledPerson)).findFirst().orElse(null);
            Participant otherPerson = pair.getPerson1().equals(cancelledPerson)? pair.getPerson2() : pair.getPerson1();
            if (cancelledParticipants.contains(otherPerson)) {
                cancelAsPair(pairs,pair,numbers,partyLocation);
                cancelledParticipants.remove(cancelledPerson);
                cancelledParticipants.remove(otherPerson);
            } else if(participants.contains(cancelledPerson)) {
                    cancelIfSingleRegistered(cancelledPerson,pair,pairs,numbers,partyLocation);
                }else {
                cancelIfOnePersonCancelled(cancelledPerson,pair,pairs,numbers,partyLocation);
            }
            }
        }

    private void cancelIfOnePersonCancelled(Participant cancelledPerson, Pair pair, List<Pair> pairs, int[] numbers,Location partyLocation) throws FileNotFoundException {
        Participant otherPerson = pair.getPerson1().equals(cancelledPerson)? pair.getPerson2() : pair.getPerson1();
        pairSuccessors.add(otherPerson);
        List<Pair> pairsToCheck = generatePairsForCancellation(pairSuccessors,numbers);
        boolean getPaired = false;
        Pair newPair = null;
        for(Pair pair1 : pairsToCheck){
            if(pair1.getParticipantsInPair().contains(otherPerson)){
                getPaired = true;
                newPair = pair1;
            }
        }
        if(getPaired){
            pairs.remove(pair);
            pairs.add(newPair);
            newGroupGenerator(pairs,numbers,partyLocation);
        }else {
            pairs.remove(pair);
            newGroupGenerator(pairs,numbers,partyLocation);
        }

    }


    /**
     * Cancels a single participant if they are registered individually, and generates new pairs/groups based on the updated participant list.
     *
     * @param personToRemove       The participant to be cancelled.
     */
    private void cancelIfSingleRegistered(Participant personToRemove,Pair toUpdatePair,List<Pair> pairs,int[] numbers,Location partyLocation) throws FileNotFoundException {
        participants.remove(personToRemove);
        Participant remainingPerson = toUpdatePair.getPerson1().equals(personToRemove) ? toUpdatePair.getPerson2() : toUpdatePair.getPerson1();
        Participant toAdd = null;
        if (pairSuccessors.size() > 0) {
            // Find a pair successor with matching food preference and kitchen availability
            boolean foundPerson = false;
            Pair pair = toUpdatePair;
            for (int i = 0; i < pairSuccessors.size(); i++) {
                Participant personToAdd = pairSuccessors.get(i);
                if (personToRemove.getFoodPreference() == personToAdd.getFoodPreference() &&
                        ((remainingPerson.getHasKitchen() == HasKitchen.yes || remainingPerson.getHasKitchen() == maybe) || (personToAdd.getHasKitchen() == HasKitchen.yes || personToAdd.getHasKitchen() == maybe))) {
                    participants.add(personToAdd);
                    toAdd = personToAdd;
                    foundPerson = true;
                    break;
                }
            }
            if (foundPerson) {
                if (toUpdatePair.getPerson1().equals(personToRemove)) {
                    pair.setPerson1(toAdd);
                } else {
                    pair.setPerson2(toAdd);
                }
//                List<Group> groupsToUpdate = generatedGroups.stream().filter(x -> x.getPairsInGroup().contains(toUpdatePair)).collect(Collectors.toCollection(ArrayList::new));
//                if (groupsToUpdate.size() != 3) {
//                    throw new RuntimeException("Found Groups to Update for Cancellation are less than 3");
//                }
//                for (Group group : groupsToUpdate) {
//
//                        if (group.pair1.equals(toUpdatePair)) {
//                            group.pair1 = pair;
//                        } else if (group.pair2.equals(toUpdatePair)) {
//                            group.pair2 = pair;
//                        } else {
//                            group.pair3 = pair;
//                        }
//                    }
                pairs.remove(toUpdatePair);
                pairs.add(pair);
                newGroupGenerator(pairs,numbers,partyLocation);
            }
        }else if (starterSuccessors.size() > 0) {
            boolean foundPair = false;
            //Pair newPair = toUpdatePair;
            for (int i = 0; i < starterSuccessors.size(); i++) {
                Pair pairToAdd = starterSuccessors.get(i);
                if (pairToAdd.getMainFoodPreference() == toUpdatePair.getMainFoodPreference()) {
                    pairs.remove(toUpdatePair);
                    pairs.add(pairToAdd);
                    foundPair = true;
                   // newPair = pairToAdd;
                    break;
                }
            }
            if(foundPair){
//                List<Group> groupsToUpdate = generatedGroups.stream().filter(x -> x.getPairsInGroup().contains(toUpdatePair)).collect(Collectors.toCollection(ArrayList::new));
//                if (groupsToUpdate.size() != 3) {
//                    throw new RuntimeException("Found Groups to Update for Cancellation are less than 3");
//                }
//                for (Group group : groupsToUpdate) {
//
//                        if (group.pair1.equals(toUpdatePair)) {
//                            group.pair1 = newPair;
//                        } else if (group.pair2.equals(toUpdatePair)) {
//                            group.pair2 = newPair;
//                        } else {
//                            group.pair3 = newPair;
//                        }
//                    }
                newGroupGenerator(pairs,numbers,partyLocation);
                }
            }
        else {
            pairs.remove(toUpdatePair);
            newGroupGenerator(pairs,numbers,partyLocation);
        }
        }


    /**
     * Generates new groups based on the provided pairs and radius.
     *
     * @param pairs  The list of pairs for generating groups.
     */
    private void newGroupGenerator(List<Pair> pairs,int[]numbers,Location partyLocation) throws FileNotFoundException {
        GroupGenerator groupGenerator = new GroupGenerator();
        groupGenerator.callGroupsGenerator(pairs,numbers,partyLocation);
    }

    private void cancelAsPair(List<Pair> pairs,Pair pair,int[] numbers,Location partyLocation) throws FileNotFoundException {
        if(starterSuccessors.size() > 0){
            boolean foundPair = false;
            //Pair newPair = pair;
            for (int i = 0; i < starterSuccessors.size(); i++) {
                Pair pairToAdd = starterSuccessors.get(i);
                if (pairToAdd.getMainFoodPreference() == pair.getMainFoodPreference()) {
                    pairs.remove(pair);
                    pairs.add(pairToAdd);
                    foundPair = true;
                   // newPair = pairToAdd;
                    break;
                }
            }
            if(foundPair){
//                List<Group> groupsToUpdate = generatedGroups.stream().filter(x -> x.getPairsInGroup().contains(pair)).collect(Collectors.toCollection(ArrayList::new));
//                if (groupsToUpdate.size() != 3) {
//                    throw new RuntimeException("Found Groups to Update for Cancellation are less than 3");
//                }
//                for (Group group : groupsToUpdate) {
//
//                    if (group.pair1.equals(pair)) {
//                        group.pair1 = newPair;
//                    } else if (group.pair2.equals(pair)) {
//                        group.pair2 = newPair;
//                    } else {
//                        group.pair3 = newPair;
//                    }
//                }
                newGroupGenerator(pairs,numbers,partyLocation);
            }
        } else if (pairSuccessors.size() > 2) {
           List<Pair> pairsToCheck = generatePairsForCancellation(pairSuccessors,numbers);
           if(pairsToCheck.size() > 0){
               pairs.remove(pair);
               pairs.add(pairsToCheck.get(0));
               newGroupGenerator(pairs,numbers,partyLocation);
           }else {
               pairs.remove(pair);
               newGroupGenerator(pairs,numbers,partyLocation);
           }
        }
    }
    public  List<Pair> generatePairsForCancellation(List<Participant> participants,int[] numbers) {
        HashSet<Participant> usedParticipants = new HashSet<>();
        List<Pair> result = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {
            Participant participant1 = participants.get(i);

            for (int j = i + 1; j < participants.size(); j++) {
                Participant participant2 = participants.get(j);

                if (!usedParticipants.contains(participant1) &&
                        !usedParticipants.contains(participant2)) {
                    Pair pair = new Pair(participant1, participant2, false);

                    double fitness = PairFitnessEvaluator.evaluateFitness(pair,kitchenMap,numbers);
                    if (fitness > 4.0) {
                        result.add(pair);
                        usedParticipants.add(participant1);
                        usedParticipants.add(participant2);
                    }
                }
            }
        }
        return result;
    }
}
