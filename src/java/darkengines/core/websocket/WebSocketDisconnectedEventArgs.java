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
class WebSocketDisconnectedEventArgs {
    private User user;
    private Session session;

    public WebSocketDisconnectedEventArgs(User user, Session session) {
	this.user = user;
	this.session = session;
    }
    
    public User getUser() {
	return user;
    }

    private void setUser(User user) {
	this.user = user;
    }

    public Session getSession() {
	return session;
    }

    private void setSession(Session session) {
	this.session = session;
    }
}
