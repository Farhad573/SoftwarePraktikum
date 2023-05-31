package model;

import java.util.List;
import java.util.NoSuchElementException;

public class Cancellation extends ParticipantManager {
    public void cancel(List<Participant> cacelledParticipants, List<Participant> participants,int maximum){
        for(Participant cancelledPErson : cacelledParticipants){
            if(participants.contains(cancelledPErson)){
                cancelIfSingleRegistered(cancelledPErson,participants);
            }
        }
    }



    private void cancelIfSingleRegistered(Participant person,List<Participant> participants){
        participants.remove(person);
        if(pairSuccessors.size() > 0){
            participants.add(pairSuccessors.get(0));


        }else {
            throw new NoSuchElementException("Successor list is empty");
        }
    }

}
