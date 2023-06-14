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
     * @param participants          The list of all participants.
     * @param maximum               The maximum number of participants.
     * @param radius                The radius for generating groups.
     */
    public void cancelPerson(List<Participant> cancelledParticipants, List<Participant> participants, int maximum, double radius) throws FileNotFoundException {
        for (Participant cancelledPerson : cancelledParticipants) {
            if (participants.contains(cancelledPerson)) {
                cancelIfSingleRegistered(cancelledPerson, participants, radius);
            }
        }
    }

    /**
     * Cancels a single participant if they are registered individually, and generates new pairs/groups based on the updated participant list.
     *
     * @param person       The participant to be cancelled.
     * @param participants The list of all participants.
     * @param radius       The radius for generating groups.
     */
    private void cancelIfSingleRegistered(Participant person, List<Participant> participants, double radius) throws FileNotFoundException {
        participants.remove(person);

        if (pairSuccessors.size() > 0) {
            // Find a pair successor with matching food preference and kitchen availability
            for (int i = 0; i < pairSuccessors.size(); i++) {
                if (person.getFoodPreference() == pairSuccessors.get(i).getFoodPreference() && person.getHasKitchen() != no) {
                    participants.add(pairSuccessors.get(i));
                    break;
                }
            }

            this.newPairs = PairGenerator.generateInitialPopulation(participants);
            newGroupGenerator(this.newPairs, radius);
        } else {
            this.newPairs = PairGenerator.generateInitialPopulation(participants);
            newGroupGenerator(this.newPairs, radius);
        }
    }

    /**
     * Generates new groups based on the provided pairs and radius.
     *
     * @param pairs  The list of pairs for generating groups.
     * @param radius The radius for generating groups.
     */
    private void newGroupGenerator(List<Pair> pairs, double radius) throws FileNotFoundException {
        GroupGenerator groupGenerator = new GroupGenerator();
        groupGenerator.callGroupsGenerator(pairs, radius);
    }
}
