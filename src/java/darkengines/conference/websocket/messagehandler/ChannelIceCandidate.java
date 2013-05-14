/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.friendship.FriendshipModule;
import darkengines.user.User;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class ChannelIceCandidate implements IWebSocketMessageHandler {

    private WebSocketManager manager;
    private Gson gson;

    public ChannelIceCandidate(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try {
	    ChannelIceCandidateData iceCandidate = gson.fromJson(data, ChannelIceCandidateData.class);
	    boolean friendship = FriendshipModule.getFriendshipRepository().areFriends(user.getId(), iceCandidate.getUserId());
	    if (friendship) {
		WebSocket socket = manager.getUserSession(iceCandidate.getUserId(), iceCandidate.getSocketId());
                iceCandidate.setUserId(user.getId());
                iceCandidate.setSocketId(socket.hashCode());
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL_ICE_CANDIDATE, iceCandidate);
		socket.sendMessage(message);
	    }
	} catch (Exception e) {
	    Logger.getLogger(Offer.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
