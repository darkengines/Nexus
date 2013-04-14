/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import darkengines.channel.ChannelInvitation;

/**
 *
 * @author Quicksort
 */
public class ChannelInvitationRepport {
    private ChannelInvitation invitation;
    private UserData user;

    public ChannelInvitation getInvitation() {
	return invitation;
    }

    public void setInvitation(ChannelInvitation invitation) {
	this.invitation = invitation;
    }

    public UserData getUser() {
	return user;
    }

    public void setUser(UserData user) {
	this.user = user;
    }
    
}
