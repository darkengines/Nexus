package darkengines.conference.websocket.messagehandler;

import darkengines.core.WRTC.PeerToPeerConnection.RTCSessionDescription;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Quicksort
 */
public class ChannelOfferData {
    private long userId;
    private long channelId;
    private int socketId;
    private RTCSessionDescription description;

    public long getChannelId() {
	return channelId;
    }

    public void setChannelId(long channelId) {
	this.channelId = channelId;
    }
    
    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }
    public RTCSessionDescription getDescription() {
	return description;
    }

    public void setDescription(RTCSessionDescription description) {
	this.description = description;
    }

    public int getSocketId() {
	return socketId;
    }

    public void setSocketId(int socketId) {
	this.socketId = socketId;
    }
}
