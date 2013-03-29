/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.searchuser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Quicksort
 */
class UserSearchResult {

    private long id;
    private String email;
    private String displayName;
    private boolean isFriend;
    private boolean online;
    
    static UserSearchResult map(ResultSet resultSet) throws SQLException {
	UserSearchResult result = new UserSearchResult();
	result.id = resultSet.getLong("id");
	result.email = resultSet.getString("email");
	result.displayName = resultSet.getString("display_name");
	result.isFriend = resultSet.getBoolean("is_friend");
	return result;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public boolean isIsFriend() {
	return isFriend;
    }

    public void setIsFriend(boolean isFriend) {
	this.isFriend = isFriend;
    }

    public boolean isOnline() {
	return online;
    }

    public void setOnline(boolean online) {
	this.online = online;
    }
    
}
