/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

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
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class SendChannelChatMessage implements IWebSocketMessageHandler {

    private Gson gson;
    private WebSocketManager manager;

    public SendChannelChatMessage(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	ChannelChatMessage msg = gson.fromJson(data, ChannelChatMessage.class);
	boolean inChannel;
	try (Connection connection = Database.getConnection()) {
	    String checkQuery = Repository.getQuery("get_channel_by_participant_and_channel_id.sql", true, SendChannelChatMessage.class);
	    String getParticipantsQuery = Repository.getQuery("get_channel_participants.sql", true, SendChannelChatMessage.class);
	    try (PreparedStatement ps = connection.prepareStatement(checkQuery)) {
		ps.setLong(1, user.getId());
		ps.setLong(2, msg.getChannelId());
		try (ResultSet result = ps.executeQuery()) {
		    inChannel = result.next();
		}
	    }
	    if (inChannel) {
		msg.setAuthorId(user.getId());
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL_CHAT_MESSAGE, msg);
		try (PreparedStatement ps = connection.prepareStatement(getParticipantsQuery)) {
		    ps.setLong(1, msg.getChannelId());
		    try (ResultSet result = ps.executeQuery()) {
			while (result.next()) {
			    long id = result.getLong("user_id");
			    Collection<WebSocket> sockets = manager.getUserSessions(id);
			    for (WebSocket socket: sockets) {
				socket.sendMessage(message);
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(SendChannelChatMessage.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
