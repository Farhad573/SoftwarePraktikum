package model;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonMaker {
    private final JsonObject rootObject;
    private String jsonString;


    public JsonMaker() {
        this.rootObject = new JsonObject();
        this.jsonString = "";
    }

    /**
     * Creates a JSON object based on the data from ParticipantManager.
     *
     * @return The created JSON object.
     */
    public JsonObject makeJsonObject() throws IOException {
        addGroupsToJson();
        addPairsToJson();
        addSuccessorPairsToJson();
        addSuccessorParticipantsToJson();

        //i can change !!
        writeJsonToFile("data_test.json");

        return rootObject;
    }

    /**
     * add groups elements to json object
     */
    private void addGroupsToJson() {
        JsonArray groupsJsonArray = new JsonArray();
        List<Group> generatedGroups = ParticipantManager.generatedGroups;
        for (Group group : generatedGroups) {
            groupsJsonArray.add(group.toJson());
        }
        rootObject.put("groups", groupsJsonArray); // add to json Object
    }

    /**
     * add pairs elements to json object
     */
    private void addPairsToJson() {
        JsonArray pairsJsonArray = new JsonArray();
        List<Pair> pairs = ParticipantManager.pairs;
        for (Pair pair : pairs) {
            pairsJsonArray.add(pair.toJson());
        }
        rootObject.put("pairs", pairsJsonArray);// add to json Object
    }


    /**
     * add successorsPair elements to json object
     */
    private void addSuccessorPairsToJson() {
        JsonArray successorPairsJsonArray = new JsonArray();
        List<Pair> starterSuccessors = ParticipantManager.starterSuccessors;
        for (Pair pair : starterSuccessors) {
            successorPairsJsonArray.add(pair.toJson());
        }
        rootObject.put("successorPairs", successorPairsJsonArray);// add to json Object
    }


    /**
     * add successor participants elements to json object
     */
    private void addSuccessorParticipantsToJson() {
        JsonArray successorParticipantsJsonArray = new JsonArray();
        List<Participant> pairSuccessors = ParticipantManager.pairSuccessors;
        for (Participant person : pairSuccessors) {
            successorParticipantsJsonArray.add(person.toJson());
        }
        rootObject.put("successorParticipants", successorParticipantsJsonArray);// add to json Object
    }


    /**
     * write json data on a given file path
     *
     * @param filePath
     */
    private void writeJsonToFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            throw new IOException("File already exists at the specified path: " + filePath);
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the JSON string representation of the rootObject.
     * If the jsonString is empty, it serializes the rootObject and assigns
     * the serialized JSON string to the jsonString field.
     *
     * @return The JSON string representation of the rootObject.
     */
    private String getJsonString() {
        if (jsonString.isEmpty()) {
            this.jsonString = Jsoner.serialize(rootObject);
        }
        return jsonString;
    }
}
