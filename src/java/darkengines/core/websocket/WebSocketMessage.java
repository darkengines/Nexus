/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
/**
 *
 * @author Quicksort
 */
public class WebSocketMessage {
    private static Gson gson = new Gson();
    private WebSocketMessageType type;
    private JsonElement data;
    private Long token;

    public WebSocketMessage() {
	
    }
    
    public WebSocketMessage(WebSocketMessageType type, JsonElement data) {
	this.type = type;
	this.data = data;
    }
    public WebSocketMessage(WebSocketMessageType type, Object data) {
	this.type = type;
	this.data = gson.toJsonTree(data);
    }
    public WebSocketMessageType getType() {
        return type;
    }

    public void setType(WebSocketMessageType type) {
        this.type = type;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
    
    public void setData(Object data) {
	this.data = gson.toJsonTree(data);
    }

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson gson) {
        WebSocketMessage.gson = gson;
    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }
    
}
