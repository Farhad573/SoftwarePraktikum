package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.JsonValidator;
import com.networknt.schema.ValidationMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;


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
        //System.out.println(successorPairsJsonArray.get(23));
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
        try (FileWriter writer = new FileWriter("D:\\Uni_Marburrg\\4th-semester\\Software Praktikum\\Repo\\data_test2.json")) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
//    public  boolean validateJson(String jsonDocument, String jsonSchemaPath) {
//        try (InputStream schemaStream = JsonValidator.class.getResourceAsStream("src/main/resources/result_schema.json")) {
//            JsonSchemaFactory factory = new JsonSchemaFactory();
//            JsonSchema schema = factory.getSchema(schemaStream);
//            JsonNode schemaNode = JsonLoader.fromResource("/path/to/schema.json")
//            Set<ValidationMessage> validationResult = schema.validate(jsonDocument);
//            return validationResult.isEmpty();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


}
