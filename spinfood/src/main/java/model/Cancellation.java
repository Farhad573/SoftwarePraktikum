package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static model.HasKitchen.no;

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
            Pair pair = pairs.stream().filter(x-> x.getParticipantsInPair().contains(cancelledPerson)).findFirst().orElse(null);
            Participant otherPerson = pair.getPerson1().equals(cancelledPerson)? pair.getPerson1() : pair.getPerson2();
            if (cancelledParticipants.contains(otherPerson)) {
                cancelAsPair(pairs,pair,numbers,partyLocation);
                cancelledParticipants.remove(cancelledPerson);
                cancelledParticipants.remove(otherPerson);
            } else {
                if(participants.contains(cancelledPerson)){

                }
            }
        }
    }

    /**
     * Cancels a single participant if they are registered individually, and generates new pairs/groups based on the updated participant list.
     *
     * @param person       The participant to be cancelled.
     * @param participants The list of all participants.
     */
    private void cancelIfSingleRegistered(Participant person, List<Participant> participants) throws FileNotFoundException {
        participants.remove(person);

        if (pairSuccessors.size() > 0) {
            // Find a pair successor with matching food preference and kitchen availability
            for (int i = 0; i < pairSuccessors.size(); i++) {
                if (person.getFoodPreference() == pairSuccessors.get(i).getFoodPreference() && person.getHasKitchen() != no) {
                    participants.add(pairSuccessors.get(i));
                    break;
                }
            }

            this.newPairs = PairGenerator.generateInitialPopulation(participants,new int[4]);
            newGroupGenerator(this.newPairs);
        } else {
            this.newPairs = PairGenerator.generateInitialPopulation(participants,new int[4]);
            newGroupGenerator(this.newPairs);
        }
    }

    /**
     * Generates new groups based on the provided pairs and radius.
     *
     * @param pairs  The list of pairs for generating groups.
     */
    private void newGroupGenerator(List<Pair> pairs,int[]numbers,Location partyLocation) throws FileNotFoundException {
        GroupGenerator groupGenerator = new GroupGenerator();
        groupGenerator.callGroupsGenerator(pairs,new int[4],new Location(1.0,1.0));
    }

    private void cancelAsPair(List<Pair> pairs,Pair pair,int[] numbers,Location partyLocation) throws FileNotFoundException {
        pairs.remove(pair);
        if(starterSuccessors.size() > 0){
            pairs.add(starterSuccessors.get(0));
            newGroupGenerator(pairs,numbers,partyLocation);
        } else if (pairSuccessors.size() > 2) {
            Pair toAdd = new Pair(pairSuccessors.get(0),pairSuccessors.get(1),false);
            pairs.add(toAdd);
            newGroupGenerator(pairs,numbers,partyLocation);
        }
    }
}
