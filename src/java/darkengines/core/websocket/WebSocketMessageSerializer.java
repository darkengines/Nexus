/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
/**
 *
 * @author Quicksort
 */
public class WebSocketMessageSerializer implements JsonSerializer<WebSocketMessage> {

    @Override
    public JsonElement serialize(WebSocketMessage src, Type typeOfSrc, JsonSerializationContext context) {
	JsonObject element = new JsonObject();
	element.addProperty("type", src.getType().toString());
	element.add("data", src.getData());
	return element;
    }
    
}
