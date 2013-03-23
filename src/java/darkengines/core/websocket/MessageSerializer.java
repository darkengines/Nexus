/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Quicksort
 */
public class MessageSerializer {

    private Gson gson;

    public MessageSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WebSocketMessage.class, new WebSocketMessageDeserializer());
        gsonBuilder.registerTypeAdapter(WebSocketMessage.class, new WebSocketMessageSerializer());
        gson = gsonBuilder.create();
    }

    public String Serialize(WebSocketMessage message) {
        return gson.toJson(message);
    }

    public WebSocketMessage deserialize(String message) {
        return gson.fromJson(message, WebSocketMessage.class);
    }
}
