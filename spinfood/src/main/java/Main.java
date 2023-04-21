import model.ParticipantModel;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        ParticipantModel participantModel = new ParticipantModel();

        try {
            participantModel.readCSVData("src/main/java/model/teilnehmerliste.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
