/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.conference.websocket.messagehandler.getfriends.Friend;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.friendship.Friendship;
import darkengines.friendship.FriendshipModule;
import darkengines.user.User;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class RejectFriendRequest implements IWebSocketMessageHandler {

    private Gson gson;
    private WebSocketManager manager;

    public RejectFriendRequest(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try {
	    Long id = gson.fromJson(data, Long.class);
	    Friendship friendship = FriendshipModule.getFriendshipRepository().getFriendshipById(id);
	    if (friendship != null && friendship.getTarget() == user.getId()) {
		FriendshipModule.getFriendshipRepository().deleteFriendshipById(friendship.getId());
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.FRIEND_REQUEST_REJECTED, friendship.getId());
		webSocket.sendMessage(message);
		Collection<WebSocket> sockets = manager.getUserSessions(friendship.getOwner());
		message.setType(WebSocketMessageType.REJECTED_FRIEND_REQUEST);
		for (WebSocket socket: sockets) {
		    socket.sendMessage(message);
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}
    }
}
