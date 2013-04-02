/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.init;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Quicksort
 */
public class UserData {
    private long id;
    private String displayName;
    
    public UserData(long id, String displayName) {
	this.id = id;
	this.displayName = displayName;
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
    public static UserData map(ResultSet result) throws SQLException {
	return new UserData(result.getLong("id"), result.getString("display_name"));
    }
}
