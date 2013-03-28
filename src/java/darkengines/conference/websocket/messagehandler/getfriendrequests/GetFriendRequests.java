/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.getfriendrequests;

import com.google.gson.JsonElement;
import darkengines.conference.websocket.messagehandler.getfriends.Friend;
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
public class GetFriendRequests implements IWebSocketMessageHandler {

    public GetFriendRequests(WebSocketManager webSocketManager) {
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try (Connection connection = Database.getConnection()) {
	    try (PreparedStatement ps = connection.prepareStatement(Repository.getQuery("get_friend_requests.sql", true, this.getClass()))) {
		try (ResultSet result = ps.executeQuery()) {
		    ArrayList<Friend> friends = new ArrayList<Friend>();
		    while (result.next()) {
			Friend friend = Friend.map(result);
			friends.add(friend);
		    }
		    webSocket.sendMessage(new WebSocketMessage(WebSocketMessageType.GET_FRIEND_REQUESTS, friends));
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(GetFriendRequests.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
