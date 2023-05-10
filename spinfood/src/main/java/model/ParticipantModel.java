package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParticipantModel extends Service{



    public ParticipantModel() {
        super();
        participants = new ArrayList<>();
        groups = new ArrayList<>();
        successors = new ArrayList<>();
    }


    @Override
    public void myAbstractMethod() {
        System.out.println("TODO sth. later");
    }


    /**
     * read csvFile if exist
     * @param csvFileName
     * @throws FileNotFoundException
     */
    public void readCSVData_2(String csvFileName) throws FileNotFoundException {
        File csvFile = new File(csvFileName);
        Scanner scanner = new Scanner(csvFile);
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // skip header row
        }

        // Extracting field values from the input array
        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().split(",");
            String id = fields[1];
            String name = fields[2];
            FoodPreference foodPreference = FoodPreference.valueOf(fields[3]);
            int age = Integer.parseInt(fields[4]);
            Sex sex = Sex.valueOf(fields[5]);
            HasKitchen hasKitchen = HasKitchen.valueOf(fields[6]);

            // Kitchen has or not
            int kitchen_story = 0;
            if (fields.length >= 8 && !fields[7].isEmpty()) {
                kitchen_story = Integer.parseInt(fields[7]);
            }

            // Location of kitchen
            double kitchen_long = 0;
            double kitchen_lat = 0;
            if (fields.length >= 10 && !fields[8].isEmpty() && !fields[9].isEmpty()) {
                kitchen_long = Double.parseDouble(fields[8]);
                kitchen_lat = Double.parseDouble(fields[9]);
            }

            // Creat location object
            Location kitchen_location = new Location(kitchen_long, kitchen_lat);

            // Handling second person fields if available
            String id_2 = "";
            String name_2 = "";
            int age_2 = 0;
            Sex sex_2 = null;

            // Extracting second person fields
            if (fields.length >= 14) {
                id_2 = fields[10];
                name_2 = fields[11];
                age_2 = Integer.parseInt(fields[12]);
                sex_2 = Sex.valueOf(fields[13]);

                // Creat a Pair object using the extracted values
                Participant personGroup = new Participant(id, name, age, hasKitchen, foodPreference, sex,
                        kitchen_story, kitchen_location, id_2, name_2, age_2, sex_2);
                groups.add(personGroup);
            }
            //Creat a single object using the extracted values
            participants.add(new Participant(id, name, age, hasKitchen, foodPreference, sex));
        }
        scanner.close();
    }



    public static List<Participant> getParticipants() {
        return participants;
    }

    public static List<Participant> getGroups() {
        return groups;
    }

    public static List<Participant> getSuccessor() {
        return successors;
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


    public String toStringParticipants() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\n");
        for( Participant participant : participants){
            sb.append("\t").append(participant.toString_1()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }


    public String toStringGroups() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\n");
        for( Participant participant : groups){
            sb.append("\t").append(participant.toString_2()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }


    public String toStringSuccessors() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\n");
        for( Participant participant : successors){
            sb.append("\t").append(participant.toString_2()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
