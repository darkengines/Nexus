/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.user.User;

/**
 *
 * @author Quicksort
 */
public class Call implements IWebSocketMessageHandler {
    
    private WebSocketManager manager;
    private Gson gson;

    public Call(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }
    
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
