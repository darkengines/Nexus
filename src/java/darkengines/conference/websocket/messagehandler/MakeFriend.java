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
public class MakeFriend implements IWebSocketMessageHandler {

    private WebSocketManager manager;
    private Gson gson;

    public MakeFriend(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try {
	    Long id = gson.fromJson(data, Long.class);
	    Friend friend = new Friend(UserModule.getUserRepository().getUserById(id));
	    Friendship friendship = FriendshipModule.getFriendshipRepository().getFriendshipsByOnwerAndTargetId(user.getId(), id);
	    if (friendship == null) {
		friendship = new Friendship();
		friendship.setOwner(user.getId());
		friendship.setTarget(id);
		FriendshipModule.getFriendshipRepository().insertFriendship(friendship);
		Friendship reverseFriendship = FriendshipModule.getFriendshipRepository().getFriendshipsByOnwerAndTargetId(id, user.getId());
		Collection<WebSocket> friendSockets = manager.getUserSessions(id);
		if (reverseFriendship != null) {
		    Collection<WebSocket> userSockets = manager.getUserSessions(user.getId());

		    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.STATE_CHANGED, null);
		    message.setData(new Friend(user, !userSockets.isEmpty()));

		    for (WebSocket socket : friendSockets) {
			socket.sendMessage(message);
		    }

		    friend.setOnline(!friendSockets.isEmpty());
		    message.setData(friend);
		    for (WebSocket socket : userSockets) {
			socket.sendMessage(message);
		    }
		} else {
		    UserData ud = new UserData(user.getId(), user.getDisplayName());
		    ud.setOnline(true);
		    FriendRequestData frd = new FriendRequestData(friendship.getId(), ud);
		    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.FRIEND_REQUEST, frd);
		    for (WebSocket socket : friendSockets) {
			socket.sendMessage(message);
		    }
		    frd.getUser().setId(friend.getId());
		    frd.getUser().setDisplayName(friend.getDisplayName());
		    frd.getUser().setOnline(!friendSockets.isEmpty());
		    message.setType(WebSocketMessageType.FRIEND_REQUESTED);
		    message.setData(frd);
		    webSocket.sendMessage(message);
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}
    }
}
