/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.getchannels;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.channel.ChannelModule;
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
public class GetChannels implements IWebSocketMessageHandler {

    private Gson gson;
    private WebSocketManager manager;

    public GetChannels(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	ArrayList<ChannelData> cds = new ArrayList<ChannelData>();
	try (Connection connection = Database.getConnection()) {
	    String query = Repository.getQuery("get_user_channels.sql", true, GetChannels.class);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, user.getId());
		try (ResultSet result = ps.executeQuery()) {
		    query = Repository.getQuery("get_channel_users", true, GetChannels.class);
		    String query1 = Repository.getQuery("get_channel_invited_users", true, GetChannels.class);
		    while (result.next()) {
			ChannelData cd = new ChannelData();
			cd.setId(result.getLong("id"));
			cd.setName(result.getString("name"));
			ArrayList<Friend> friends = new ArrayList<Friend>();
			try (PreparedStatement ps1 = connection.prepareStatement(query)) {
			    ps1.setLong(1, cd.getId());
			    try (ResultSet result1 = ps.executeQuery()) {
				while (result1.next()) {
				    Friend friend = Friend.map(result1);
				    if (friend.getId() != user.getId()) {
					friends.add(friend);
				    }
				}
			    }
			}
			cd.setParticipants(friends);
			ArrayList<Friend> invitations = new ArrayList<Friend>();
			try (PreparedStatement ps1 = connection.prepareStatement(query1)) {
			    ps1.setLong(1, cd.getId());
			    try (ResultSet result1 = ps.executeQuery()) {
				while (result1.next()) {
				    Friend friend = Friend.map(result1);
				    friends.add(friend);
				}
			    }
			}
			cd.setInvitedParticipants(invitations);
			cds.add(cd);
		    }
		}
	    }
	    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.GET_CHANNELS, cds);
	    webSocket.sendMessage(message);
	} catch (Exception e) {
	    Logger.getLogger(GetChannels.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
