/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import darkengines.nexus.NexusMessage;
import darkengines.nexus.NexusMessageDeserializer;
import darkengines.nexus.NexusMessageSerializer;
import darkengines.user.User;
import java.util.HashMap;
import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author Quicksort
 */
class WebSocketMessageManager {
    private HashMap<WebSocketMessageType, IWebSocketMessageHandler> handlers;
    private final WebSocketManager webSocketManager;
    private Gson gson;
    public WebSocketMessageManager(WebSocketManager webSocketManager) {
	GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(NexusMessage.class, new WebSocketMessageDeserializer());
	gsonBuilder.registerTypeAdapter(NexusMessage.class, new WebSocketMessageSerializer());
	gson = gsonBuilder.create();
	this.webSocketManager = webSocketManager;
	handlers = new HashMap<WebSocketMessageType, IWebSocketMessageHandler>();
    }
    public void registerMessageHandler(WebSocketMessageType type, IWebSocketMessageHandler handler) {
	handlers.put(type, handler);
    }
    public void processMessage(User user, Session session, String message) {
	WebSocketMessage msg = gson.fromJson(message, WebSocketMessage.class);
	if (handlers.containsKey(msg.getType())) {
	    handlers.get(msg.getType()).processMessage(user, session, msg.getData());
	}
    }
}
