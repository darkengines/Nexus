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
public class SendChatMessage implements IWebSocketMessageHandler {

    private Gson gson;
    private WebSocketManager manager;

    public SendChatMessage(WebSocketManager manager) {
        gson = new Gson();
        this.manager = manager;
    }

    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction) {
        try {
            ChatMessage chatMessage = gson.fromJson(data, ChatMessage.class);
            boolean areFriends = FriendshipModule.getFriendshipRepository().areFriends(user.getId(), chatMessage.getRecipientId());
            if (areFriends) {
                Collection<WebSocket> sockets = manager.getUserSessions(chatMessage.getRecipientId());
                chatMessage.setAuthorId(user.getId());
                WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHAT_MESSAGE, chatMessage);
                for (WebSocket socket: sockets) {
                    socket.sendMessage(message);
                }
                webSocket.sendMessage(message);
            }
        } catch (Exception e) {
	    Logger.getLogger(SendChatMessage.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
