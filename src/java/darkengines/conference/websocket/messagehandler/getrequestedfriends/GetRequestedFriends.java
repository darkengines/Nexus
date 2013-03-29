/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.getrequestedfriends;

import com.google.gson.Gson;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class GetRequestedFriends implements IWebSocketMessageHandler {
    private Gson gson;
    private WebSocketManager manager;
    public GetRequestedFriends(WebSocketManager manager) {
        this.manager = manager;
        gson = new Gson();
    }
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
        ArrayList<Friend> requestedFriends = new ArrayList<Friend>();
        try(Connection connection = Database.getConnection()) {
            String request = Repository.getQuery("get_requested_friends.sql", true, GetRequestedFriends.class);
            try (PreparedStatement ps = connection.prepareStatement(request)) {
                ps.setLong(1, user.getId());
                try (ResultSet result = ps.executeQuery()) {
                    while (result.next()) {
                        Friend friend = Friend.map(result);
                        friend.setOnline(!manager.getUserSessions(friend.getId()).isEmpty());
                    }
                    webSocket.sendMessage(new WebSocketMessage(WebSocketMessageType.GET_REQUESTED_FRIENDS, requestedFriends));
                }
            }
        }catch(Exception e) {
            Logger.getLogger(GetRequestedFriends.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
