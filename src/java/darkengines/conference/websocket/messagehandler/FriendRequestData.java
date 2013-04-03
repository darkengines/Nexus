/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

/**
 *
 * @author Quicksort
 */
public class FriendRequestData {
    private long id;
    private UserData user;

    public FriendRequestData(long id, UserData user) {
	this.id = id;
	this.user = user;
    }
    
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public UserData getUser() {
	return user;
    }

    public void setUser(UserData user) {
	this.user = user;
    }
}
