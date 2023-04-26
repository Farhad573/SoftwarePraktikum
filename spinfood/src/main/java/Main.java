import model.ParticipantModel;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        ParticipantModel participantModel = new ParticipantModel();

        try {
            participantModel.readCSVData_2("src/main/java/model/teilnehmerliste.csv");
            System.out.println(participantModel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
