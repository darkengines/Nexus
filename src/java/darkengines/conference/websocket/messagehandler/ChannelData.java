/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Quicksort
 */
class ChannelData {

    private long id;
    private String name;
    private HashMap<Long, Long> participants;
    private HashMap<Long, Long> invitations;

    public ChannelData(long id, String name) {
	this.id = id;
	this.name = name;
	participants = new HashMap<>();
	invitations = new HashMap<>();
    }

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

    public HashMap<Long, Long> getParticipants() {
	return participants;
    }

    public void setParticipants(HashMap<Long, Long> participants) {
	this.participants = participants;
    }

    public HashMap<Long, Long> getInvitations() {
	return invitations;
    }

    public void setInvitations(HashMap<Long, Long> invitedUsers) {
	this.invitations = invitedUsers;
    }
    
}
