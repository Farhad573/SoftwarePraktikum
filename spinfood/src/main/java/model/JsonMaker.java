package model;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class JsonMaker {

    public static void main(String[] args) {
        JsonObject root = new JsonObject();
        root.put("groups",new JsonArray());
        root.put("pairs",new JsonArray());
        root.put("successorPairs",new JsonArray());
        root.put("successorParticipants",new JsonArray());
    }
}
