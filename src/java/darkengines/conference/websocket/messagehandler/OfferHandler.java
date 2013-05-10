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
import darkengines.friendship.Friendship;
import darkengines.friendship.FriendshipModule;
import darkengines.user.User;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class OfferHandler implements IWebSocketMessageHandler {

    private WebSocketManager manager;
    private Gson gson;

    public OfferHandler(WebSocketManager manager) {
	this.manager = manager;
	gson = new Gson();
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
	try {
	    Offer offer = gson.fromJson(data, Offer.class);
	    boolean friendship = FriendshipModule.getFriendshipRepository().areFriends(user.getId(), offer.getCallee());
	    if (friendship) {
		offer.setCaller(user.getId());
		offer.setUniqueId(webSocket.hashCode());
		Collection<WebSocket> sockets = manager.getUserSessions(offer.getCallee());
		for (WebSocket socket: sockets) {
		    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.OFFER, offer);
		    socket.sendMessage(message);
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(OfferHandler.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
