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
    public void readCSVData_1(String csvFileName) throws FileNotFoundException{
        File csvFile = new File(csvFileName);
        Scanner scanner = new Scanner(csvFile);
        // skip header row
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fields = line.split(",");

            String id = fields[1];
            String name = fields[2];
            FoodPreference foodPreference = FoodPreference.valueOf(fields[3]);
            int age = Integer.parseInt(fields[4]);
            Sex sex = Sex.valueOf(fields[5]);
            HasKitchen hasKitchen = HasKitchen.valueOf(fields[6]);

            participants.add(new Participant(id, name, age, hasKitchen,foodPreference, sex));
        }
        scanner.close();
    }



    /**
     *
     * @param csvFileName
     * @throws FileNotFoundException
     */
    public void readCSVData_2(String csvFileName) throws FileNotFoundException{
        File csvFile = new File(csvFileName);
        Scanner scanner = new Scanner(csvFile);
        // skip header row
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fields = line.split(",");
            String id = fields[1];
            String name = fields[2];
            FoodPreference foodPreference = FoodPreference.valueOf(fields[3]);
            int age = Integer.parseInt(fields[4]);
            Sex sex = Sex.valueOf(fields[5]);
            HasKitchen hasKitchen = HasKitchen.valueOf(fields[6]);

            int kitchen_story = 0 ;
            if (fields.length >= 8 && !fields[7].isEmpty()){
                kitchen_story = Integer.parseInt(fields[7]);
            }


            //check long. && lat.
            double kitchen_long = 0;
            if (fields.length >= 10 && !fields[8].isEmpty()){
                kitchen_long = Double.parseDouble(fields[8]);
            }
            double kitchen_lat = 0;
            if (fields.length >= 11 && !fields[9].isEmpty()){
                kitchen_lat = Double.parseDouble(fields[9]);
            }

            Location kitchen_location;
            kitchen_location = new Location(kitchen_long, kitchen_lat);


            String id_2 = "";
            String name_2 = "";
            int age_2 = 0;
            Sex sex_2 = null;
            if (fields.length >= 14){
                id_2 = fields[10];
                name_2 = fields[11];
                age_2 = Integer.parseInt(fields[12]);
                sex_2 = Sex.valueOf(fields[13]);
            }
            participants.add(new Participant(id, name, age, hasKitchen,foodPreference
                    , sex, kitchen_story, kitchen_location, id_2, name_2, age_2, sex_2));
        }
        scanner.close();
    }



    public String toString_1() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\n");
        for( Participant participant : participants){
            sb.append("\t").append(participant.toString_1()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }


    public String toString_2() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\n");
        for( Participant participant : participants){
            sb.append("\t").append(participant.toString_2()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
