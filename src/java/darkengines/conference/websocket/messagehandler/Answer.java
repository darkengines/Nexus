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
public class Answer implements IWebSocketMessageHandler {

    private WebSocketManager manager;
    private Gson gson;

    public Answer(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try {
	    OfferData answer = gson.fromJson(data, OfferData.class);
	    boolean friendship = FriendshipModule.getFriendshipRepository().areFriends(user.getId(), answer.getUserId());
	    if (friendship) {
		WebSocket socket  = manager.getUserSession(answer.getUserId(), answer.getSocketId());
		answer.setUserId(user.getId());
		answer.setSocketId(webSocket.hashCode());
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.ANSWER, answer);
		socket.sendMessage(message);
	    }
	} catch (Exception e) {
	    Logger.getLogger(Answer.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
