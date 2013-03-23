/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket.messagehandler;

import com.google.gson.JsonElement;
import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.nexus.NexusMessage;
import darkengines.user.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jetty.websocket.api.Session;

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
    public void processMessage(User user, Session session, JsonElement data) {
	try {
	ArrayList<Friend> friends = new ArrayList<Friend>();
	PreparedStatement ps = Database.getConnection().prepareStatement(Repository.getQuery("get_user_friends.sql", true, this.getClass()));
	ps.setLong(1, user.getId());
	ResultSet result = ps.executeQuery();
	while (result.next()) {
	    Friend friend = Friend.map(result);
	    Collection<Session> friendSockets = webSocketManager.getUserSessions(user);
	    friend.setOnline(!friendSockets.isEmpty());
	    friends.add(friend);
	}
	WebSocketMessage message = new WebSocketMessage();
	message.setType(WebSocketMessageType.GET_FRIENDS);
	message.setData(gson.toJsonTree(friends));
	socket.getSession().getRemote().sendString(gson.toJson(message));
	} catch(Exception e) {
	    
	}
    }
    
}
