package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParticipantModel {

    private List<Participant> participants;

    public ParticipantModel() {
        this.participants = new ArrayList<>();
    }

    public void readCSVData(String csvFileName) throws FileNotFoundException{
        File csvFile = new File(csvFileName);
        Scanner scanner = new Scanner(csvFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fields = line.split(",");
            String name = fields[2];
            String id = fields[1];
            String age = fields[4];
            participants.add(new Participant(id, name, age)); //name, age, email
        }
        scanner.close();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\\n");
        for( Participant participant : participants){
            sb.append("\t").append(participant.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();

    }
}
