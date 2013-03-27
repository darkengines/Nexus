/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.eventlistener;

import darkengines.conference.websocket.messagehandler.getfriends.Friend;
import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import darkengines.core.event.IListener;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketDisconnectedEventArgs;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
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
public class UserDisconnectedEventListener implements IListener<WebSocketDisconnectedEventArgs> {
    private final WebSocketManager webSocketManager;
    public UserDisconnectedEventListener(WebSocketManager webSocketManager) {
	this.webSocketManager = webSocketManager;
    }
    @Override
    public void callback(Object sender, WebSocketDisconnectedEventArgs eventArgs) {
	try {
	    Friend localFriend = new Friend(eventArgs.getUser(), false);
	    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.STATE_CHANGED, localFriend);
            ArrayList<Friend> friends = new ArrayList<Friend>();
            PreparedStatement ps = Database.getConnection().prepareStatement(Repository.getQuery("get_user_reverse_friends.sql", true, this.getClass()));
            ps.setLong(1, eventArgs.getUser().getId());
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                Friend friend = Friend.map(result);
                Collection<WebSocket> friendSockets = webSocketManager.getUserSessions(friend.getId());
                for (WebSocket socket: friendSockets) {
		    socket.sendMessage(message);
		}
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
}
