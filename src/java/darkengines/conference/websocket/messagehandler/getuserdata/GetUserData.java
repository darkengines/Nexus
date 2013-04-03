/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.getuserdata;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.conference.websocket.messagehandler.UserData;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class GetUserData implements IWebSocketMessageHandler {
    private WebSocketManager manager;
    private Gson gson;
    
    public GetUserData(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try (Connection connection = Database.getConnection()) {
	    long id = gson.fromJson(data, Long.class);
	    String query = Repository.getQuery("get_user_data.sql", true, GetUserData.class);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, id);
		try (ResultSet result = ps.executeQuery()) {
		    if (result.next()) {
			UserData ud = UserData.map(result);
			ud.setOnline(!manager.getUserSessions(id).isEmpty());
			WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.USER_DATA, ud);
			webSocket.sendMessage(message);
		    }
		}
	    }		
	} catch (Exception e) {
	    Logger.getLogger(GetUserData.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
}
