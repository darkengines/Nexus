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
public class Offer {
    private long caller;
    private long callee;
    private long token;
    private int uniqueId;
    private RTCSessionDescription description;

    public long getCaller() {
	return caller;
    }

    public void setCaller(long caller) {
	this.caller = caller;
    }

    public long getCallee() {
	return callee;
    }

    public void setCallee(long callee) {
	this.callee = callee;
    }

    public RTCSessionDescription getDescription() {
	return description;
    }

    public void setDescription(RTCSessionDescription description) {
	this.description = description;
    }

    public long getToken() {
	return token;
    }

    public void setToken(long token) {
	this.token = token;
    }

    public int getUniqueId() {
	return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
	this.uniqueId = uniqueId;
    }
    
}
