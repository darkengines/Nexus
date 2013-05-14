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
import darkengines.friendship.FriendshipModule;
import darkengines.user.User;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class ChannelOffer implements IWebSocketMessageHandler {

    private WebSocketManager manager;
    private Gson gson;

    public ChannelOffer(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try {
	    ChannelOfferData offer = gson.fromJson(data, ChannelOfferData.class);
	    boolean isParticipant = ChannelModule.getChannelParticipantRepository().isParticipant(offer.getChannelId(), user.getId());
	    if (isParticipant) {
		WebSocket socket = manager.getUserSession(offer.getUserId(), offer.getSocketId());
		offer.setUserId(user.getId());
		offer.setSocketId(webSocket.hashCode());
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL_OFFER, offer);
		socket.sendMessage(message);
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelOffer.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
