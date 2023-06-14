package model;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

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

    public JsonObject makeJsonObject() {
        addGroupsToJson();
        addPairsToJson();
        addSuccessorPairsToJson();
        addSuccessorParticipantsToJson();

        //i can change !!
        writeJsonToFile("data_test.json");

        return rootObject;
    }

    private void addGroupsToJson() {
        JsonArray groupsJsonArray = new JsonArray();
        List<Group> generatedGroups = ParticipantManager.generatedGroups;
        for (Group group : generatedGroups) {
            groupsJsonArray.add(group.toJson());
        }
        rootObject.put("groups", groupsJsonArray); // add to json Object
    }

    private void addPairsToJson() {
        JsonArray pairsJsonArray = new JsonArray();
        List<Pair> pairs = ParticipantManager.pairs;
        for (Pair pair : pairs) {
            pairsJsonArray.add(pair.toJson());
        }
        rootObject.put("pairs", pairsJsonArray);// add to json Object
    }

    private void addSuccessorPairsToJson() {
        JsonArray successorPairsJsonArray = new JsonArray();
        List<Pair> starterSuccessors = ParticipantManager.starterSuccessors;
        for (Pair pair : starterSuccessors) {
            successorPairsJsonArray.add(pair.toJson());
        }
        rootObject.put("successorPairs", successorPairsJsonArray);// add to json Object
    }

    private void addSuccessorParticipantsToJson() {
        JsonArray successorParticipantsJsonArray = new JsonArray();
        List<Participant> pairSuccessors = ParticipantManager.pairSuccessors;
        for (Participant person : pairSuccessors) {
            successorParticipantsJsonArray.add(person.toJson());
        }
        rootObject.put("successorParticipants", successorParticipantsJsonArray);
    }

    private void writeJsonToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getJsonString() {
        if (jsonString.isEmpty()) {
            this.jsonString = Jsoner.serialize(rootObject);
        }
        return jsonString;
    }
}
