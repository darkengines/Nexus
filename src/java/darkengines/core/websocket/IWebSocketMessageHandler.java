/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import com.google.gson.JsonElement;
import darkengines.user.User;
import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author Quicksort
 */
public interface IWebSocketMessageHandler {

    void processMessage(User user, Session session, JsonElement data);
    
}
