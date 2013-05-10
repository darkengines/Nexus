package darkengines.conference.websocket.messagehandler;

import darkengines.core.WRTC.PeerToPeerConnection.RTCIceCandidate;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Quicksort
 */
public class IceCandidate {
    private long author;
    private long recipient;
    private long token;
    private int uniqueId;
    private RTCIceCandidate iceCandidate;

    public long getAuthor() {
	return author;
    }

    public void setAuthor(long author) {
	this.author = author;
    }

    public long getRecipient() {
	return recipient;
    }

    public void setRecipient(long recipient) {
	this.recipient = recipient;
    }

    public RTCIceCandidate getIceCandidate() {
	return iceCandidate;
    }

    public void RTCIceCandidate(RTCIceCandidate iceCandidate) {
	this.iceCandidate = iceCandidate;
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
