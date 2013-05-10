/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.getfriends;

import com.google.gson.JsonElement;
import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class GetFriends implements IWebSocketMessageHandler {

    private WebSocketManager webSocketManager;

    public GetFriends(WebSocketManager webSocketManager) {
	this.webSocketManager = webSocketManager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try (Connection connection = Database.getConnection()) {
	    try (PreparedStatement ps = connection.prepareStatement(Repository.getQuery("get_user_friends.sql", true, this.getClass()))) {
		ps.setLong(1, user.getId());
		try (ResultSet result = ps.executeQuery()) {
		    ArrayList<Friend> friends = new ArrayList<Friend>();
		    while (result.next()) {
			Friend friend = Friend.map(result);
			Collection<WebSocket> friendSockets = webSocketManager.getUserSessions(friend.getId());
			friend.setOnline(!friendSockets.isEmpty());
			friends.add(friend);
		    }
		    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.GET_FRIENDS, friends);
		    webSocket.sendMessage(message);
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}
    }
}
