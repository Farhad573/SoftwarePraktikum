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


    /**
     *
     * @param csvFileName
     * @throws FileNotFoundException
     */
    public void readCSVData(String csvFileName) throws FileNotFoundException{
        File csvFile = new File(csvFileName);
        Scanner scanner = new Scanner(csvFile);
        // skip header row
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fields = line.split(",");
            String name = fields[2];
            String id = fields[1];
            int age = Integer.parseInt(fields[4]);
            HasKitchen hasKitchen = HasKitchen.valueOf(fields[6]);
            FoodPreference foodPreference = FoodPreference.valueOf(fields[3]);
            Sex sex = Sex.valueOf(fields[5]);

            participants.add(new Participant(id, name, age, hasKitchen,foodPreference, sex));
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
