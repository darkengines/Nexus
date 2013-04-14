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
import java.util.ArrayList;
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
	DetailledChannelInvitation cdi = new DetailledChannelInvitation();
	try (Connection connection = Database.getConnection()) {
	    String checkInvitationQuery = Repository.getQuery("get_channel_by_participant_and_channel_id.sql", true, ChannelInvitationHandler.class);
	    String getParticipantsQuery = Repository.getQuery("get_channel_participants_users.sql", true, ChannelInvitationHandler.class);
	    String getInvitedUsersQuery = Repository.getQuery("get_channel_invitation_users.sql", true, ChannelInvitationHandler.class);
	    ChannelInvitation invitation = gson.fromJson(data, darkengines.channel.ChannelInvitation.class);
	    
	    Channel channel = null;
	    try (PreparedStatement ps = connection.prepareStatement(checkInvitationQuery)) {
		    ps.setLong(2, invitation.getChannelId());
		    ps.setLong(1, user.getId());
		    try (ResultSet result = ps.executeQuery()) {
			while (result.next()) {
			    channel = Channel.Map(result);
			}
		    }
		}
	    if (channel != null) {
		UserData invitedUser = new UserData(UserModule.getUserRepository().getUserById(invitation.getUserId()));
		ChannelData channelData = new ChannelData(channel.getId(), channel.getName());
		cdi.setChannel(channelData);
		ArrayList<Long> participantIds = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(getParticipantsQuery)) {
		    ps.setLong(1, channel.getId());
		    try (ResultSet result = ps.executeQuery()) {
			while (result.next()) {
			    UserData ud = UserData.map(result);
			    participantIds.add(ud.getId());
			    cdi.getUsers().put(ud.getId(), ud);
			    cdi.getChannel().getParticipants().put(ud.getId(), ud.getId());
			}
		    }
		}
		try (PreparedStatement ps = connection.prepareStatement(getInvitedUsersQuery)) {
		    ps.setLong(1, channel.getId());
		    try (ResultSet result = ps.executeQuery()) {
			while (result.next()) {
			    UserData ud = new UserData();
			    long userId = result.getLong("user_id");
			    ud.setId(userId);
			    ud.setDisplayName(result.getString("display_name"));
			    cdi.getUsers().put(ud.getId(), ud);
			    cdi.getChannel().getParticipants().put(userId, userId);
			}
		    }
		}
		invitation = ChannelModule.getChannelInvitationRepository().insertChannelInvitation(invitation);
		cdi.setId(invitation.getId());
		ChannelInvitationRepport repport = new ChannelInvitationRepport();
		repport.setInvitation(invitation);
		repport.setUser(invitedUser);
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL_INVITATION_SENT, repport);
		for (Long id: participantIds) {
		    Collection<WebSocket> sockets = manager.getUserSessions(id);
		    for (WebSocket socket: sockets) {
			socket.sendMessage(message);
		    }
		}
		Collection<WebSocket> sockets = manager.getUserSessions(invitation.getUserId());
		message.setType(WebSocketMessageType.CHANNEL_INVITATION);
		message.setData(cdi);
		for (WebSocket socket: sockets) {
		    socket.sendMessage(message);
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(darkengines.channel.ChannelInvitation.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
