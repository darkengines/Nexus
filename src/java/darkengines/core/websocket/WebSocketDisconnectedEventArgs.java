/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import darkengines.user.User;

/**
 *
 * @author Quicksort
 */
class WebSocketDisconnectedEventArgs {
    private User user;
    private WebSocket webSocket;

    public WebSocketDisconnectedEventArgs(User user, WebSocket webSocket) {
	this.user = user;
	this.webSocket = webSocket;
    }
    
    public User getUser() {
	return user;
    }

    private void setUser(User user) {
	this.user = user;
    }

    public WebSocket getWebSocket() {
	return webSocket;
    }

    private void setWebSocket(WebSocket webSocket) {
	this.webSocket = webSocket;
    }
}
