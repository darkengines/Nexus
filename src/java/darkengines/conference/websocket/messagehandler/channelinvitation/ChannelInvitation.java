/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.channelinvitation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.user.User;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Quicksort
 */
public class ChannelInvitation implements IWebSocketMessageHandler {
    
    private Gson gson;
    private WebSocketManager manager;
    
    public ChannelInvitation(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }
    
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try {
	    ChannelInvitation invitation = gson.fromJson(data, ChannelInvitation.class);
	} catch (Exception e) {
	    Logger.getLogger(ChannelInvitation.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
}
