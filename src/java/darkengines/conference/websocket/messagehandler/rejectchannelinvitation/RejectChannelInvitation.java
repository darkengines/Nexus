/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.rejectchannelinvitation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.channel.ChannelInvitation;
import darkengines.channel.ChannelModule;
import darkengines.channel.ChannelParticipant;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.user.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class RejectChannelInvitation implements IWebSocketMessageHandler{

    private Gson gson;
    private WebSocketManager manager;
    
    public RejectChannelInvitation(WebSocketManager manager) {
	gson = new Gson();
	this.manager = manager;
    }
    
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try {
	    long invitationId = gson.fromJson(data, Long.class);
	    ChannelInvitation invitation = ChannelModule.getChannelInvitationRepository().getChannelInvitationById(invitationId);
	    if (invitation != null && invitation.getUserId() == user.getId()) {
		ChannelModule.getChannelInvitationRepository().deleteChannelInvitationById(invitationId);
		ArrayList<ChannelParticipant> participants = ChannelModule.getChannelParticipantRepository().getChannelParticipants(invitation.getChannelId());
		WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL_INVITATION_REJECTED, invitation);
		for (ChannelParticipant participant: participants) {
		    Collection<WebSocket> sockets = manager.getUserSessions(participant.getUserId());
		    for (WebSocket socket: sockets) {
			socket.sendMessage(message);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(RejectChannelInvitation.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
}