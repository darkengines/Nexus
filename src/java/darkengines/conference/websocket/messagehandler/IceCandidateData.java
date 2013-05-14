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
public class IceCandidateData {
    private long userId;
    private int socketId;
    private RTCIceCandidate iceCandidate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getSocketId() {
        return socketId;
    }

    public void setSocketId(int socketId) {
        this.socketId = socketId;
    }

    public RTCIceCandidate getIceCandidate() {
        return iceCandidate;
    }

    public void setIceCandidate(RTCIceCandidate iceCandidate) {
        this.iceCandidate = iceCandidate;
    }
}
