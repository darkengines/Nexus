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
    private Session session;
    private String message;
    
    public WebSocketMessageEventArgs(User user, Session session, String message) {
	this.user = user;
	this.session = session;
	this.message = message;
    }
    
    public Session getSession() {
	return session;
    }

    private void setSession(Session session) {
	this.session = session;
    }    
    
    public User getUser() {
	return user;
    }

    private void setUser(User user) {
	this.user = user;
    }

    public String getMessage() {
	return message;
    }

    private void setMessage(String message) {
	this.message = message;
    }    
}
