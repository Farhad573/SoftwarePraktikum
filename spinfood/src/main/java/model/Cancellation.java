package model;

import java.util.ArrayList;
import java.util.List;

import static model.HasKitchen.no;

public class Cancellation extends ParticipantManager {

    List<Pair> newPairs = new ArrayList<>();


    public void cancelPerson(List<Participant> cancelledParticipants, List<Participant> participants, int maximum, double radius){
        for(Participant cancelledPerson : cancelledParticipants){
            if(participants.contains(cancelledPerson)){
                cancelIfSingleRegistered(cancelledPerson, participants, radius);
            }
        }
    }



    private void cancelIfSingleRegistered(Participant person,List<Participant> participants, double radius){
        participants.remove(person);
        if(pairSuccessors.size() > 0){
            for (int i = 0 ; i < pairSuccessors.size() ; i++ ) {
                if (person.getFoodPreference() == pairSuccessors.get(i).getFoodPreference() && person.getHasKitchen() != no){
                    participants.add(pairSuccessors.get(i));
                    break;
                }
            }
            this.newPairs = PairGenerator.generateInitialPopulation(participants) ;
            newGroupGenerator(this.newPairs, radius);
        }else {
            this.newPairs = PairGenerator.generateInitialPopulation(participants) ;
            newGroupGenerator(this.newPairs, radius);
        }
    }



    private void newGroupGenerator(List<Pair> pairs, double radius){
        GroupGenerator groupGenerator = new GroupGenerator();
        groupGenerator.callGroupsGenerator(pairs, radius);
    }


}
