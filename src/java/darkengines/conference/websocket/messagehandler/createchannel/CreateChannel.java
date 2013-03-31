/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.createchannel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.channel.Channel;
import darkengines.channel.ChannelModule;
import darkengines.channel.ChannelParticipant;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.core.websocket.WebSocketMessage;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.user.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class CreateChannel implements IWebSocketMessageHandler {

    private Gson gson;
    
    public CreateChannel() {
	gson = new Gson();
    }
    
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try {
	    String channelName = gson.fromJson(data, String.class);
	    Channel channel = new Channel();
	    channel.setName(channelName);
	    channel = ChannelModule.getChannelRepository().insertChannel(channel);
	    ChannelParticipant participant = new ChannelParticipant();
	    participant.setChannelId(channel.getId());
	    participant.setUserId(user.getId());
	    ChannelModule.getChannelParticipantRepository().insertChannelParticipant(participant);
	    WebSocketMessage message = new WebSocketMessage(WebSocketMessageType.CHANNEL, channel);
	    webSocket.sendMessage(message);
	} catch (Exception e) {
	    Logger.getLogger(CreateChannel.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
}
