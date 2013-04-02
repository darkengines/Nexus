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
    private long userId;
    
    public ClientFriendRequest(long id, long userId) {
	this.id = id;
	this.userId = userId;
    }
    
    public static ClientFriendRequest map(ResultSet result) throws SQLException {
	return new ClientFriendRequest(result.getLong("id"), result.getLong("user_id"));
    }
}
