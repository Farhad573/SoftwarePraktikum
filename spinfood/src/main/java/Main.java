import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        ParticipantModel model = new ParticipantModel();
        model.readCSV("src/main/resources/teilnehmerliste.csv");

        for (Participant person : model.participantList){
            System.out.println(person.toString());
        }

    }
}
