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
public class ClientFriendRequest {
    private long id;
    private UserData user;
    
    public ClientFriendRequest(long id, UserData userData) {
	this.id = id;
	this.user = userData;
    }
    
    public static ClientFriendRequest map(ResultSet result) throws SQLException {
	UserData userData = new UserData(result.getLong("user_id"), result.getString("display_name"));
	return new ClientFriendRequest(result.getLong("id"), userData);
    }
}
