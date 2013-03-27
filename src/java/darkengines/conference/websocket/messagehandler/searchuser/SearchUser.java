/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.searchuser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.conference.websocket.messagehandler.getfriends.Friend;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class SearchUser implements IWebSocketMessageHandler {
    
    private WebSocketManager manager;
    private Gson gson;
    
    public SearchUser(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }
    
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try {
	String raw = gson.fromJson(data, String.class);
	if (raw.length() > 2) {
	    ArrayList<User> result = UserModule.getUserRepository().searchByEmail(raw.split(" "));
	    ArrayList<Friend> friends = new ArrayList<Friend>();
	    for (User u : result) {
		friends.add(new Friend(u, !manager.getUserSessions(u.getId()).isEmpty()));
	    }
	    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.SEARCH, friends);
	    webSocket.sendMessage(message);
	}
	} catch (Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}
    }
    
}
