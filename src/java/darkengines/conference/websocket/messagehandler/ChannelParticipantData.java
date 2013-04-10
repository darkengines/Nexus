/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import darkengines.conference.websocket.messagehandler.UserData;

/**
 *
 * @author Quicksort
 */
public class ChannelParticipantData {
    private long channelId;
    private UserData user;

    public long getChannelId() {
	return channelId;
    }

    public void setChannelId(long channelId) {
	this.channelId = channelId;
    }

    public UserData getUser() {
	return user;
    }

    public void setUser(UserData user) {
	this.user = user;
    }
    
}
