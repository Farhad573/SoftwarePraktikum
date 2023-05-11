
import model.ParticipantModel;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        //new View();
        ParticipantModel participantModel = new ParticipantModel();

/*
        try {
            participantModel.readCSVData_1("src/main/java/model/teilnehmerliste.csv");
            System.out.println(participantModel.toString_1());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");
*/

//
//        try {
//            participantModel.readCSVData_2("src/main/java/model/teilnehmerliste.csv");
//            System.out.println(participantModel.toStringParticipants());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        try {
            participantModel.readCSVData_2("src/main/java/model/teilnehmerliste.csv");
            System.out.println(participantModel.toStringParticipants());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("**************************************************");

        int participantsLength = participantModel.getParticipants().size();
        System.out.println("Length of participants list is: " + participantsLength);

        System.out.println("************************************************");

        int groupsLength = participantModel.getPairs().size();
        System.out.println("Length of group list is: " + groupsLength);


    }
}
