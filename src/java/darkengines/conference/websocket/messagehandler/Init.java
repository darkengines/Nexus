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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class Init implements IWebSocketMessageHandler {

    private WebSocketManager manager;
    private Gson gson;

    public Init(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	InitializationData id = new InitializationData();
	try (Connection connection = Database.getConnection()) {
	    String getUsersQuery = Repository.getQuery("get_related_users.sql", true, Init.class);
	    String getChannelsQuery = Repository.getQuery("get_user_channels.sql", true, Init.class);
	    String getChannelParticipantsQuery = Repository.getQuery("get_channel_participants.sql", true, Init.class);
	    String getChannelInvitationsQuery = Repository.getQuery("get_channel_invitations.sql", true, Init.class);
	    String getChannelInvitedUsersQuery = Repository.getQuery("get_channel_invited_users.sql", true, Init.class);
	    String getFriendRequestsQuery = Repository.getQuery("get_friend_requests.sql", true, Init.class);
	    String getRequestedFriendsQuery = Repository.getQuery("get_requested_friends.sql", true, Init.class);
	    String getFriendsQuery = Repository.getQuery("get_friends.sql", true, Init.class);
	    try (PreparedStatement getUsersPs = connection.prepareStatement(getUsersQuery)) {
		getUsersPs.setLong(1, user.getId());
		getUsersPs.setLong(2, user.getId());
		getUsersPs.setLong(3, user.getId());
		getUsersPs.setLong(4, user.getId());
		try (ResultSet result = getUsersPs.executeQuery()) {
		    while (result.next()) {
			UserData ud = UserData.map(result);
			ud.setOnline(!manager.getUserSessions(ud.getId()).isEmpty());
			id.getUsers().put(ud.getId(), ud);
		    }
		}
	    }
	    try (PreparedStatement getChannelsPs = connection.prepareStatement(getChannelsQuery)) {
		getChannelsPs.setLong(1, user.getId());
		try (ResultSet result = getChannelsPs.executeQuery()) {
		    while (result.next()) {
			ChannelData channelData = new ChannelData(result.getLong("id"), result.getString("name"));
			try (PreparedStatement getParticipantsPs = connection.prepareStatement(getChannelParticipantsQuery)) {
			    getParticipantsPs.setLong(1, channelData.getId());
			    try (ResultSet subResult = getParticipantsPs.executeQuery()) {
				while (subResult.next()) {
				    Long userId = subResult.getLong("user_id");
				    if (userId != user.getId()) {
					channelData.getParticipants().put(userId, userId);
				    }
				}
			    }
			}
			try (PreparedStatement getInvitedUsersPs = connection.prepareStatement(getChannelInvitedUsersQuery)) {
			    getInvitedUsersPs.setLong(1, channelData.getId());
			    try (ResultSet subResult = getInvitedUsersPs.executeQuery()) {
				while (subResult.next()) {
				    Long userId = subResult.getLong("user_id");
				    channelData.getInvitedUsers().put(userId, userId);
				}
			    }
			}
			id.getChannels().put(channelData.getId(), channelData);
		    }
		}
	    }
	    try (PreparedStatement getChannelInvitationsPs = connection.prepareStatement(getChannelInvitationsQuery)) {
		getChannelInvitationsPs.setLong(1, user.getId());
		try (ResultSet result = getChannelInvitationsPs.executeQuery()) {
		    while (result.next()) {
			ChannelInvitationData cid = ChannelInvitationData.map(result);
			id.getChannelInvitations().put(cid.getId(), cid);
		    }
		}
	    }
	    try (PreparedStatement getFriendRequestPs = connection.prepareStatement(getFriendRequestsQuery)) {
		getFriendRequestPs.setLong(1, user.getId());
		try (ResultSet result = getFriendRequestPs.executeQuery()) {
		    while (result.next()) {
			ClientFriendRequest cfr = ClientFriendRequest.map(result);
			id.getFriendRequests().put(cfr.getId(), cfr);
		    }
		}
	    }
	    try (PreparedStatement getRequestedFriendsPs = connection.prepareStatement(getRequestedFriendsQuery)) {
		getRequestedFriendsPs.setLong(1, user.getId());
		try (ResultSet result = getRequestedFriendsPs.executeQuery()) {
		    while (result.next()) {
			ClientFriendRequest cfr = ClientFriendRequest.map(result);
			id.getRequestedFriends().put(cfr.getId(), cfr);
		    }
		}
	    }
	    try (PreparedStatement getFriendsPs = connection.prepareStatement(getFriendsQuery)) {
		getFriendsPs.setLong(1, user.getId());
		try (ResultSet result = getFriendsPs.executeQuery()) {
		    while (result.next()) {
			long userId = result.getLong("target");
			id.getFriends().put(userId, userId);
		    }
		}
	    }
	    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.INIT, id);
	    webSocket.sendMessage(message);
	} catch (Exception e) {
	    Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
