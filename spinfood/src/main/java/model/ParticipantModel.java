package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The ParticipantModel class represents a model for managing participants and pairs.
 * It provides functionality to read participant data from a CSV file and store them in lists.
 * The class also provides methods to access and manipulate the participant and pair data.
 */
public class ParticipantModel extends ParticipantManager implements FileReader {


    /**
     * Constructs a ParticipantModel object.
     * Initializes the lists for participants, pairs, and successors.
     */
    public ParticipantModel() {
        super();
        participants = new ArrayList<>();
        pairs = new ArrayList<>();
        successors = new ArrayList<>();
    }


    /**
     * Reads a CSV file with the specified file name and populates the participant and pair lists.
     *
     * @param csvFileName the name of the CSV file to be read
     * @throws FileNotFoundException if the CSV file is not found
     */
    @Override
    public void readCSVFile(String csvFileName) throws FileNotFoundException {
        File csvFile = new File(csvFileName);
        Scanner scanner = new Scanner(csvFile);
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // skip header row
        }
        while (scanner.hasNextLine()) {
            String[] fields = scanner.nextLine().split(",");
            String id = fields[1];
            String name = fields[2];
            FoodPreference foodPreference = FoodPreference.valueOf(fields[3]);
            int age = Integer.parseInt(fields[4]);
            Sex sex = Sex.valueOf(fields[5]);
            HasKitchen hasKitchen = HasKitchen.valueOf(fields[6]);

            // check kitchen attributes
            int kitchen_story = 0;
            double kitchen_long = 0;
            double kitchen_lat = 0;
            if (fields.length >= 8 && !fields[7].isEmpty()) {
                kitchen_story = Integer.parseInt(fields[7]);
            }

            // check kitchen location
            Location kitchen_location = null;
            if (fields.length >= 10 && !fields[8].isEmpty() && !fields[9].isEmpty()) {
                kitchen_long = Double.parseDouble(fields[8]);
                kitchen_lat = Double.parseDouble(fields[9]);
                kitchen_location = new Location(kitchen_long, kitchen_lat);
            }
            Kitchen kitchen = new Kitchen(kitchen_story, kitchen_location);


            String id_2 = "";
            String name_2 = "";
            int age_2 = 0;
            Sex sex_2 = null;
            if (fields.length >= 14) {
                id_2 = fields[10];
                name_2 = fields[11];
                age_2 = Integer.parseInt(fields[12]);
                sex_2 = Sex.valueOf(fields[13]);
                Participant person1 = new Participant(id, name, age, hasKitchen, foodPreference, sex, kitchen);
                Participant person2 = new Participant(id_2, name_2, age_2, hasKitchen, foodPreference, sex_2, kitchen);
                Pair pair = new Pair(person1, person2);
                pairs.add(pair);
            }else
                participants.add(new Participant(id, name, age, hasKitchen, foodPreference, sex, kitchen));
        }
        scanner.close();
    }



    /**
     * Returns the list of participants.
     *
     * @return the list of participants
     */
    public static List<Participant> getParticipants() {
        return participants;
    }

    /**
     * Returns the list of pairs.
     *
     * @return the list of pairs
     */
    public static List<Pair> getPairs() {
        return pairs;
    }

    /**
     * Returns the list of successors.
     *
     * @return the list of successors
     */
    public static List<Participant> getSuccessor() {
        return successors;
    }


    /**
     * Returns a string representation of the participants in the ParticipantModel object.
     *
     * @return a string representation of the participants
     */
    public String toStringParticipants() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\n");
        for( Participant participant : participants){
            sb.append("\t").append(participant.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }


    /**
     * Returns a string representation of the pairs in the ParticipantModel object.
     *
     * @return a string representation of the pairs
     */
    public String toStringPairs() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\n");
        for( Pair pairs : pairs){
            sb.append("\t").append(pairs.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }


    /**
     * Returns a string representation of the successors in the ParticipantModel object.
     *
     * @return a string representation of the successors
     */
    public String toStringSuccessors() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParticipantModel{\n");
        for( Participant participant : successors){
            sb.append("\t").append(participant.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
