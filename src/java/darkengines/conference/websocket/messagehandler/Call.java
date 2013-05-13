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
import darkengines.friendship.FriendshipModule;
import darkengines.user.User;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	try {
	    CallData callData = gson.fromJson(data, CallData.class);
	    boolean friendship = FriendshipModule.getFriendshipRepository().areFriends(user.getId(), callData.getUserId());
	    if (friendship) {
		Collection<WebSocket> sockets = manager.getUserSessions(callData.getUserId());
		callData.setSocketId(webSocket.hashCode());
		callData.setUserId(user.getId());
		for (WebSocket socket: sockets) {
		    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CALL, callData);
		    socket.sendMessage(message);
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(Call.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
}
