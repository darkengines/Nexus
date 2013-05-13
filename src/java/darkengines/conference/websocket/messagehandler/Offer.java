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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class Offer implements IWebSocketMessageHandler {

    private WebSocketManager manager;
    private Gson gson;

    public Offer(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try {
	    OfferData offer = gson.fromJson(data, OfferData.class);
	    boolean friendship = FriendshipModule.getFriendshipRepository().areFriends(user.getId(), offer.getUserId());
	    if (friendship) {
		WebSocket socket = manager.getUserSession(offer.getUserId(), offer.getSocketId());
		offer.setUserId(user.getId());
		offer.setSocketId(webSocket.hashCode());
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.OFFER, offer);
		socket.sendMessage(message);
	    }
	} catch (Exception e) {
	    Logger.getLogger(Offer.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
