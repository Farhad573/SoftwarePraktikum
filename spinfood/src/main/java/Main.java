import model.ParticipantModel;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

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

        try {
            participantModel.readCSVData_2("src/main/java/model/teilnehmerliste.csv");
            System.out.println(participantModel.toString_2());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
