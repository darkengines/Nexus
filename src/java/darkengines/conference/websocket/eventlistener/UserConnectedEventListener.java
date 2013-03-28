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
import darkengines.core.websocket.WebSocketConnectedEventArgs;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
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
public class UserConnectedEventListener implements IListener<WebSocketConnectedEventArgs> {

    private final WebSocketManager webSocketManager;

    public UserConnectedEventListener(WebSocketManager webSocketManager) {
        this.webSocketManager = webSocketManager;
    }

    @Override
    public void callback(Object sender, WebSocketConnectedEventArgs eventArgs) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            connection = Database.getConnection();
            Friend localFriend = new Friend(eventArgs.getUser(), true);
            WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.STATE_CHANGED, localFriend);
            ArrayList<Friend> friends = new ArrayList<Friend>();
            connection = Database.getConnection();
            ps = connection.prepareStatement(Repository.getQuery("get_user_reverse_friends.sql", true, this.getClass()));
            ps.setLong(1, eventArgs.getUser().getId());
            result = ps.executeQuery();
            while (result.next()) {
                Friend friend = Friend.map(result);
                Collection<WebSocket> friendSockets = webSocketManager.getUserSessions(friend.getId());
                for (WebSocket socket : friendSockets) {
                    socket.sendMessage(message);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            Database.CloseResultSet(result);
            Database.CloseStatement(ps);
            Database.CloseConnection(connection);
        }
    }
}
