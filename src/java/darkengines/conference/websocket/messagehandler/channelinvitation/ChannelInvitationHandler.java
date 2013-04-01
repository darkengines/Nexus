/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.channelinvitation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.channel.ChannelModule;
import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.user.User;
import darkengines.user.UserModule;
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
public class ChannelInvitationHandler implements IWebSocketMessageHandler {
    
    private Gson gson;
    private WebSocketManager manager;
    
    public ChannelInvitationHandler(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }
    
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try (Connection connection = Database.getConnection()) {
	    darkengines.channel.ChannelInvitation invitation = gson.fromJson(data, darkengines.channel.ChannelInvitation.class);
            String query = Repository.getQuery("get_channel_by_participant_and_channel_id.sql", true, darkengines.channel.ChannelInvitation.class);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setLong(2, invitation.getChannelId());
                ps.setLong(1, user.getId());
                try (ResultSet result = ps.executeQuery()) {
                    if (result.next()) {
                       User target = UserModule.getUserRepository().getUserById(invitation.getUserId());
                       if (target != null) {
                           invitation = ChannelModule.getChannelInvitationRepository().insertChannelInvitation(invitation);
                           Collection<WebSocket> sockets = manager.getUserSessions(invitation.getUserId());
                           WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL_INVITATION, invitation);
                           for (WebSocket socket: sockets) {
                               socket.sendMessage(message);
                           }
			   message.setType(WebSocketMessageType.CHANNEL_INVITATION_SENT);
			   message.setData(target);
			   webSocket.sendMessage(message);
                       }
                    }
                }
            }
	} catch (Exception e) {
	    Logger.getLogger(darkengines.channel.ChannelInvitation.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
}
