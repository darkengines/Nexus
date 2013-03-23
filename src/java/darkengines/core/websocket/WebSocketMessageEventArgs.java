/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import darkengines.user.User;
import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author Quicksort
 */
public class WebSocketMessageEventArgs {
    private User user;
    private WebSocket webSocket;
    private WebSocketMessage message;
    
    public WebSocketMessageEventArgs(User user, WebSocket webSocket, WebSocketMessage message) {
	this.user = user;
	this.webSocket = webSocket;
	this.message = message;
    }
    
    public WebSocket getWebSocket() {
	return webSocket;
    }

    private void setWebSocket(WebSocket webSocket) {
	this.webSocket = webSocket;
    }    
    
    public User getUser() {
	return user;
    }

    private void setUser(User user) {
	this.user = user;
    }

    public WebSocketMessage getMessage() {
	return message;
    }

    private void setMessage(WebSocketMessage message) {
	this.message = message;
    }    
}
