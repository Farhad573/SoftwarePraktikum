
import model.ParticipantModel;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        ParticipantModel participantModel = new ParticipantModel();

        try {
            participantModel.readCSVFile("src/main/java/model/teilnehmerliste.csv");
            System.out.println(participantModel.toStringParticipants());
            System.out.println(participantModel.toStringPairs());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");

        int participantsLength = participantModel.getParticipants().size();
        System.out.println("Length of participants list is: " + participantsLength);

        System.out.println("************************************************");

        int pairLength = participantModel.getPairs().size();
        System.out.println("Length of pair list is: " + pairLength);

        System.out.println("************************************************");
        int count = participantsLength + pairLength ;
        System.out.println("the count of all members : " + count);


    }
}
