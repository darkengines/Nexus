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
    private Collection<Friend> participants;

    public Collection<Friend> getParticipants() {
	return participants;
    }

    public void setParticipants(Collection<Friend> participants) {
	this.participants = participants;
    }
    
}
