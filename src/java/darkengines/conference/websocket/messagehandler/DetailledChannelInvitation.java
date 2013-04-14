/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import java.util.HashMap;

/**
 *
 * @author Quicksort
 */
public class DetailledChannelInvitation {
    private long id;
    private ChannelData channel;
    private HashMap<Long, UserData> users;
    
    public DetailledChannelInvitation() {
	users = new HashMap<>();
    }
    
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public ChannelData getChannel() {
	return channel;
    }

    public void setChannel(ChannelData channel) {
	this.channel = channel;
    }

    public HashMap<Long, UserData> getUsers() {
	return users;
    }

    public void setUsers(HashMap<Long, UserData> users) {
	this.users = users;
    }
   
}
