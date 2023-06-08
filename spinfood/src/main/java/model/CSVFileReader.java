package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The ParticipantModel class represents a model for managing participants and pairs.
 * It provides functionality to read participant data from a CSV file and store them in lists.
 * The class also provides methods to access and manipulate the participant and pair data.
 */
public class CSVFileReader extends ParticipantManager implements FileReader {



    /**
     * Constructs a ParticipantModel object.
     * Initializes the lists for participants, pairs, and successors.
     */
    public CSVFileReader() {
        super();
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
            double age = Double.parseDouble(fields[4]);
            Sex sex = Sex.valueOf(fields[5]);
            HasKitchen hasKitchen = HasKitchen.valueOf(fields[6]);
            double kitchen_story = 0;
            double kitchen_long = 0;
            double kitchen_lat = 0;
            String id_2 = "";
            String name_2 = "";
            double age_2 = 0;
            Sex sex_2 = null;
            boolean checkHasKitchen = hasKitchen != HasKitchen.no;
            boolean checkIfRegisteredAsPair = fields.length >= 14;
            if(checkHasKitchen){
                // check kitchen attributes

                boolean checkIfKitchenStoryIsGiven = fields.length >= 8 && !fields[7].isEmpty();
                if (checkIfKitchenStoryIsGiven) {
                    kitchen_story = Double.parseDouble(fields[7]);
                }
                // check kitchen location
                Location kitchen_location = null;
                boolean checkIfKitchenCoordinatesIsGiven = fields.length >= 10 && !fields[8].isEmpty() && !fields[9].isEmpty();
                if (checkIfKitchenCoordinatesIsGiven) {
                    kitchen_location = makeLocationObject(fields);
                }
                Kitchen kitchen = new Kitchen(kitchen_story, kitchen_location);
                int count = kitchenCountMap.getOrDefault(kitchen, 0);
                kitchenCountMap.put(kitchen, count + 1);
                if (checkIfRegisteredAsPair) {
                    id_2 = fields[10];
                    name_2 = fields[11];
                    age_2 = Double.parseDouble(fields[12]);
                    sex_2 = Sex.valueOf(fields[13]);
                    createAndAddParticipantPair(id, name, age, hasKitchen, foodPreference, sex, kitchen, id_2, name_2, age_2, sex_2);
                }else {
                    createAndAddParticipant(id, name, age, hasKitchen, foodPreference, sex, kitchen);
                }
            }else {
                if (checkIfRegisteredAsPair) {
                    id_2 = fields[10];
                    name_2 = fields[11];
                    age_2 = Double.parseDouble(fields[12]);
                    sex_2 = Sex.valueOf(fields[13]);
                    createAndAddParticipantPair(id, name, age, hasKitchen, foodPreference, sex, null, id_2, name_2, age_2, sex_2);
                }else {
                    createAndAddParticipant(id, name, age, hasKitchen, foodPreference, sex, null);
                }
            }
        }
        scanner.close();

        // Update the count field for each person in singles list
        updateKitchenCountInParticipantsList();

        // Update the count field for each person in couples list
            updateKitchenCountInPairsList();
    }

    private static void updateKitchenCountInPairsList() {
        for (Pair person : pairs) {
            Kitchen kitchen = person.getPerson1().getKitchen();
            int count = kitchenCountMap.getOrDefault(kitchen, 0);
            person.getPerson1().setKitchenCount(count);
        }
    }

    private static void updateKitchenCountInParticipantsList() {
        for (Participant person : participants) {
            Kitchen kitchen = person.getKitchen();
            int count = kitchenCountMap.getOrDefault(kitchen, 0);
            person.setKitchenCount(count);
        }
    }

    private static Location makeLocationObject(String[] fields) {
        double kitchen_lat;
        double kitchen_long;
        Location kitchen_location;
        kitchen_long = Double.parseDouble(fields[8]);
        kitchen_lat = Double.parseDouble(fields[9]);
        kitchen_location = new Location(kitchen_long, kitchen_lat);
        return kitchen_location;
    }

    private void createAndAddParticipant(String id, String name, double age, HasKitchen hasKitchen, FoodPreference foodPreference, Sex sex, Kitchen kitchen) {
        participants.add(new Participant(id, name, age, hasKitchen, foodPreference, sex, kitchen));
    }

    private void createAndAddParticipantPair(String id1, String name1, double age1, HasKitchen hasKitchen1, FoodPreference foodPreference1,
                                             Sex sex1, Kitchen kitchen, String id2, String name2, double age2, Sex sex2) {
        Participant person1 = new Participant(id1, name1, age1, hasKitchen1, foodPreference1, sex1, kitchen);
        Participant person2 = new Participant(id2, name2, age2, hasKitchen1, foodPreference1, sex2, kitchen);
        pairs.add(new Pair(person1, person2, true));
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
        return pairSuccessors;
    }

    public static Map<Kitchen,Integer> getKitchenCountMap(){
        return kitchenCountMap;
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
        for( Participant participant : pairSuccessors){
            sb.append("\t").append(participant.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
