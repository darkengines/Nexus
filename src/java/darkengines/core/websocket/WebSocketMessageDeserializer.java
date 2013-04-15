/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 *
 * @author Quicksort
 */
public class WebSocketMessageDeserializer implements JsonDeserializer<WebSocketMessage> {

    @Override
    public WebSocketMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	WebSocketMessage message = new WebSocketMessage();
        JsonObject messageObject = json.getAsJsonObject();
	message.setType(WebSocketMessageType.valueOf(messageObject.get("type").getAsString()));
	if (messageObject.has("data") && messageObject.get("data") != null) {
	    message.setData(messageObject.get("data"));
	}
        if (messageObject.has("token") && messageObject.get("token") != null) {
            message.setToken(messageObject.get("token").getAsLong());
        }
	return message;
    }

}
