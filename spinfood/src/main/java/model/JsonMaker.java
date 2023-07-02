package model;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;


import java.io.FileWriter;
import java.io.IOException;



public class JsonMaker {
    public JsonObject rootObject;
    public String jsonString;

    public JsonObject makeJsonObject() {
        JsonObject root = new JsonObject();
        JsonArray groupsJsonArray = new JsonArray();
        for (Group group:ParticipantManager.generatedGroups
             ) {
            groupsJsonArray.add(group.toJson());
        }
        JsonArray pairsJsonArray = new JsonArray();
        for (Pair pair: ParticipantManager.pairs
             ) {
            pairsJsonArray.add(pair.toJson());
        }
        JsonArray successorPairsJsonArray = new JsonArray();
        for (Pair pair: ParticipantManager.starterSuccessors
        ) {
            successorPairsJsonArray.add(pair.toJson());
        }
        JsonArray successorParticipantsJsonArray = new JsonArray();
        for (Participant person: ParticipantManager.pairSuccessors
        ) {
            successorParticipantsJsonArray.add(person.toJson());
        }
        root.put("groups",groupsJsonArray);
        root.put("pairs",pairsJsonArray);
        root.put("successorPairs",successorPairsJsonArray);
        root.put("successorParticipants",successorParticipantsJsonArray);
        this.rootObject = root;
        this.jsonString = Jsoner.serialize(root);
        try (FileWriter writer = new FileWriter("D:\\Uni_Marburrg\\4th-semester\\Software Praktikum\\Repo\\data_test11.json")) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }



}
