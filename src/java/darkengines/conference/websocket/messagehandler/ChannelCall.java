/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.channel.ChannelModule;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.user.User;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class ChannelCall implements IWebSocketMessageHandler {
    
    private WebSocketManager manager;
    private Gson gson;

    public ChannelCall(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }
    
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try {
	    ChannelCallData callData = gson.fromJson(data, ChannelCallData.class);
	    boolean isParticipant = ChannelModule.getChannelParticipantRepository().isParticipant(callData.channelId, user.getId());
	    if (isParticipant) {
		Collection<Long> participants = ChannelModule.getChannelParticipantRepository().getChannelParticipants(callData.getChannelId());
		Collection<WebSocket> sockets = manager.getUsersSessions(participants);
		callData.setSocketId(webSocket.hashCode());
		callData.setUserId(user.getId());
		for (WebSocket socket: sockets) {
		    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL_CALL, callData);
		    socket.sendMessage(message);
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelCall.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
}
