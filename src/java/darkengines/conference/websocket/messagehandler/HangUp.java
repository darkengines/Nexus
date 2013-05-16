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
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.user.User;
import java.util.Collection;

/**
 *
 * @author Quicksort
 */
public class HangUp implements IWebSocketMessageHandler {
    
    private WebSocketManager manager;
    private Gson gson;

    public HangUp(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }
    
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	HangUpData hud = gson.fromJson(data, HangUpData.class);
	Collection<WebSocket> sockets = manager.getUserSessions(hud.getUserId());
	hud.setUserId(user.getId());
	WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.HANGUP, hud);
	for (WebSocket socket: sockets) {
	    socket.sendMessage(message);
	}
    }
    
}
