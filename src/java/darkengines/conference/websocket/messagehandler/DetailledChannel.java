/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import darkengines.channel.Channel;
import darkengines.conference.websocket.messagehandler.getfriends.Friend;
import java.util.Collection;

/**
 *
 * @author Quicksort
 */
public class DetailledChannel extends Channel {
    private Collection<UserData> participants;

    public Collection<UserData> getParticipants() {
	return participants;
    }

    public void setParticipants(Collection<UserData> participants) {
	this.participants = participants;
    }
    
}
