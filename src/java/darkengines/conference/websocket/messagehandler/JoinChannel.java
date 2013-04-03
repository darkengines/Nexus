/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try (Connection connection = Database.getConnection()) {
	    long channelId = gson.fromJson(data, Long.class);
	    ChannelInvitation invitation = ChannelModule.getChannelInvitationRepository().getChannelInvitationByChannelAndUserId(channelId, user.getId());
	    if (invitation != null) {
		ChannelParticipant participant = new ChannelParticipant();
		participant.setChannelId(channelId);
		participant.setUserId(user.getId());
		ChannelModule.getChannelParticipantRepository().insertChannelParticipant(participant);
		String query = Repository.getQuery("get_channel_users.sql", true, JoinChannel.class);
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL_PARTICIPANT, participant);
		try (PreparedStatement ps = connection.prepareStatement(query)) {
		    ps.setLong(1, channelId);
		    try (ResultSet result = ps.executeQuery()) {
			ArrayList<Friend> friends = new ArrayList<Friend>();
			while (result.next()) {
			    friends.add(Friend.map(result));
			}
			for (Friend friend : friends) {
			    if (friend.getId() != user.getId()) {
				Collection<WebSocket> sockets = manager.getUserSessions(friend.getId());
				friend.setOnline(!sockets.isEmpty());
				for (WebSocket socket : sockets) {
				    socket.sendMessage(message);
				}
			    }
			}
			message.setType(WebSocketMessageType.CHANNEL);
			DetailledChannel dc = new DetailledChannel();
			dc.setId(channelId);
			dc.setName("caca");
			dc.setParticipants(friends);
			message.setData(dc);
			webSocket.sendMessage(message);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(JoinChannel.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
