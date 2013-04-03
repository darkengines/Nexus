/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Quicksort
 */
class ChannelData {
    private long id;
    private String name;
    private Collection<Long> participants;
    private Collection<Long> invitedUsers;
    
    public ChannelData(long id, String name) {
	this.id = id;
	this.name = name;
	participants = new ArrayList<>();
	invitedUsers = new ArrayList<>();
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

    public Collection<Long> getParticipants() {
	return participants;
    }

    public void setParticipants(Collection<Long> participants) {
	this.participants = participants;
    }

    public Collection<Long> getInvitedUsers() {
	return invitedUsers;
    }

    public void setInvitedUsers(Collection<Long> invitedUsers) {
	this.invitedUsers = invitedUsers;
    }
    
}
