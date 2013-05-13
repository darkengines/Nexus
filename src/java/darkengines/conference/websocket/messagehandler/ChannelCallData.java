/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

/**
 *
 * @author Quicksort
 */
public class ChannelCallData {
    long channelId;
    long socketId;
    long userId;

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }
    
    public long getChannelId() {
	return channelId;
    }

    public void setChannelId(long channelId) {
	this.channelId = channelId;
    }

    public long getSocketId() {
	return socketId;
    }

    public void setSocketId(long socketId) {
	this.socketId = socketId;
    }
}
