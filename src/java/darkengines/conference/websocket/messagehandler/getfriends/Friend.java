/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.getfriends;

import darkengines.user.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Quicksort
 */
public class Friend {
    private long id;
    private String displayName;
    private String email;
    private boolean online;

    public static Friend map(ResultSet resultSet) throws SQLException {
	Friend friend = new Friend();
	friend.setId(resultSet.getLong("id"));
	friend.setDisplayName(resultSet.getString("display_name"));
	friend.setEmail(resultSet.getString("email"));
	return friend;
    }
    
    public Friend() {
	
    }

    public Friend(User user, boolean online) {
	this(user);
	this.online = online;
    }
    public Friend(User user) {
	id = user.getId();
	displayName = user.getDisplayName();
	email = user.getEmail();
    }
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }
    
    public void setOnline(boolean online) {
	this.online = online;
    }
    
    public boolean isOnline() {
	return online;
    }
}
