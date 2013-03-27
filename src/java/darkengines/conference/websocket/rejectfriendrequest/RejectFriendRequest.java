/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.rejectfriendrequest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import darkengines.core.websocket.IWebSocketMessageHandler;
import darkengines.core.websocket.WebSocket;
import darkengines.friendship.Friendship;
import darkengines.friendship.FriendshipModule;
import darkengines.user.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class RejectFriendRequest implements IWebSocketMessageHandler {
    private Gson gson;
    public RejectFriendRequest() {
	gson = new Gson();
    }
    @Override
    public void processMessage(User user, WebSocket webSocket, JsonElement data) {
	try {
	Long id = gson.fromJson(data, Long.class);
	Friendship friendship = FriendshipModule.getFriendshipRepository().getFriendshipsByOnwerAndTargetId(id, user.getId());
	if (friendship != null) {
	    FriendshipModule.getFriendshipRepository().deleteFriendshipById(friendship.getId());
	}
	} catch(Exception e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}
    }
    
}
