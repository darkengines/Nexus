/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import com.google.gson.JsonElement;
import darkengines.user.User;

/**
 *
 * @author Quicksort
 */
public interface IWebSocketMessageHandler {

    public void processMessage(User user, WebSocket webSocket, JsonElement data, long transaction);
    
}
