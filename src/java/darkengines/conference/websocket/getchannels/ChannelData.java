/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.getchannels;

import darkengines.conference.websocket.messagehandler.getfriends.Friend;
import java.util.ArrayList;

/**
 *
 * @author Quicksort
 */
public class ChannelData {
    private long id;
    private String name;
    private ArrayList<Friend> participants;
    private ArrayList<Friend> invitedParticipants;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public ArrayList<Friend> getParticipants() {
	return participants;
    }

    public void setParticipants(ArrayList<Friend> participants) {
	this.participants = participants;
    }

    public ArrayList<Friend> getInvitedParticipants() {
	return invitedParticipants;
    }

    public void setInvitedParticipants(ArrayList<Friend> invitedParticipants) {
	this.invitedParticipants = invitedParticipants;
    }
    
}
