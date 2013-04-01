/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

/**
 *
 * @author Quicksort
 */
public enum WebSocketMessageType {
    KEEP_ALIVE(0x0),
    GET_ONLINE_USERS(0x1),
    OFFLINE_USER(0x2),
    ONLINE_USER(0x3), 
    CHAT_MESSAGE(0x20),
    CALL_REQUEST(0x4),
    CALL_RESPONSE(0x5),
    ICE_CANDIDATE(0x6),
    OFFER(0x7),
    ANSWER(0x8),
    HANGUP(0x9),
    SEARCH(0x10),
    MAKE_FRIEND(0x11),
    GET_FRIENDS(0x12), 
    ONLINE_FRIEND(0x13),
    OFFLINE_FRIEND(0x14),
    STATE_CHANGED(0x15),
    GET_FRIEND_REQUESTS(0x16),
    FRIEND_REQUEST(0x17),
    REJECT_FRIEND_REQUEST(0x18),
    GET_REQUESTED_FRIENDS(0x19),
    FRIEND_REQUESTED(0x21), 
    CHANNEL(0x22), 
    CHANNEL_INVITATION(0x23), 
    CHANNEL_INVITATION_SENT(0x24), 
    CHANNEL_PARTICIPANT(0x25), 
    CHANNEL_INVITATION_REJECTED(0x26), GET_CHANNELS(0x27);
    
    private int code;

    WebSocketMessageType(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
