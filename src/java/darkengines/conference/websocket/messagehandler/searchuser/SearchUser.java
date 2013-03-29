/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.searchuser;

import com.google.gson.Gson;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

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
	    ArrayList<UserSearchResult> result = search(user, raw.split(" "));
	    for (UserSearchResult u : result) {
		u.setOnline(!manager.getUserSessions(u.getId()).isEmpty());
	    }
	    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.SEARCH, result);
	    webSocket.sendMessage(message);
	}
	} catch (Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}
    }
    
    private ArrayList<UserSearchResult> search(User searcher, String[] words) {
	ArrayList<UserSearchResult> results = new ArrayList<UserSearchResult>();
	try (Connection connection = Database.getConnection()) {
	    String query = Repository.getQuery("search_user.sql", true, SearchUser.class);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, searcher.getId());
		ps.setString(2, StringUtils.join(words, "|"));
		try (ResultSet resultSet = ps.executeQuery()) {
		    while (resultSet.next()) {
			UserSearchResult userResult = UserSearchResult.map(resultSet);
			results.add(userResult);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}
	return results;
    }
    
}
