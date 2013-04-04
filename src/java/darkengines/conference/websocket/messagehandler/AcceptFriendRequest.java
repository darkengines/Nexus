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
import darkengines.user.UserModule;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class AcceptFriendRequest implements IWebSocketMessageHandler {

    private WebSocketManager manager;
    private Gson gson;

    public AcceptFriendRequest(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try {
	   long id = gson.fromJson(data, long.class);
	   Friendship friendship = FriendshipModule.getFriendshipRepository().getFriendshipById(id);
	   if (friendship != null && friendship.getTarget() == user.getId()) {
	       Friendship rfriendship = new Friendship();
	       rfriendship.setOwner(user.getId());
	       rfriendship.setTarget(friendship.getOwner());
	       rfriendship = FriendshipModule.getFriendshipRepository().insertFriendship(rfriendship);
	       Collection<WebSocket> sockets = manager.getUserSessions(friendship.getOwner());
	       WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.ACCEPTED_FRIEND_REQUEST, friendship.getId());
	       for (WebSocket socket: sockets) {
		   socket.sendMessage(message);
	       }
	       message.setType(WebSocketMessageType.FRIEND_REQUEST_ACCEPTED);
	       webSocket.sendMessage(message);
	   }
	} catch (Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}
    }
}
