/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

/**
 *
 * @author Quicksort
 */
public class CallData {
    long userId;
    long socketId;

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public long getSocketId() {
	return socketId;
    }

    public void setSocketId(long socketId) {
	this.socketId = socketId;
    }
}
