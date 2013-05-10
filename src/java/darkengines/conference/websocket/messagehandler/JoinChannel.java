/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.channel.Channel;
import darkengines.channel.ChannelInvitation;
import darkengines.channel.ChannelModule;
import darkengines.channel.ChannelParticipant;
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
public class JoinChannel implements IWebSocketMessageHandler {

    private Gson gson;
    private WebSocketManager manager;

    public JoinChannel(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try (Connection connection = Database.getConnection()) {
	    long id = gson.fromJson(data, Long.class);
	    ChannelInvitation invitation = ChannelModule.getChannelInvitationRepository().getChannelInvitationById(id);
	    if (invitation != null && user.getId() == invitation.getUserId()) {
		ChannelParticipantData participant = new ChannelParticipantData();
		participant.setChannelId(invitation.getChannelId());
		participant.setUser(new UserData(user));
		ChannelModule.getChannelParticipantRepository().insertChannelParticipant(new ChannelParticipant(invitation.getChannelId(), invitation.getUserId()));
		String query = Repository.getQuery("get_channel_users.sql", true, JoinChannel.class);
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.ACCEPTED_CHANNEL_INVITATION, invitation.getId());
		try (PreparedStatement ps = connection.prepareStatement(query)) {
		    ps.setLong(1, invitation.getChannelId());
		    try (ResultSet result = ps.executeQuery()) {
			ArrayList<UserData> users = new ArrayList<>();
			while (result.next()) {
			    users.add(UserData.map(result));
			}
			for (UserData ud : users) {
			    if (ud.getId() != user.getId()) {
				Collection<WebSocket> sockets = manager.getUserSessions(ud.getId());
				ud.setOnline(!sockets.isEmpty());
				for (WebSocket socket : sockets) {
				    socket.sendMessage(message);
				}
			    }
			}
			Channel channel = ChannelModule.getChannelRepository().getChannelById(invitation.getChannelId());
			message.setType(WebSocketMessageType.CHANNEL_INVITATION_ACCEPTED);
			message.setData(invitation.getId());
			webSocket.sendMessage(message);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(JoinChannel.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
