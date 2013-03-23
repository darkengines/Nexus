/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import darkengines.user.User;
import java.util.HashMap;

/**
 *
 * @author Quicksort
 */
public class WebSocketMessageManager {
    private HashMap<WebSocketMessageType, IWebSocketMessageHandler> handlers;
    public WebSocketMessageManager() {
	handlers = new HashMap<WebSocketMessageType, IWebSocketMessageHandler>();
    }
    public void registerMessageHandler(WebSocketMessageType type, IWebSocketMessageHandler handler) {
	handlers.put(type, handler);
    }
    public void processMessage(User user, WebSocket webSocket, WebSocketMessage message) {
	if (handlers.containsKey(message.getType())) {
	    handlers.get(message.getType()).processMessage(user, webSocket, message.getData());
	}
    }
}
