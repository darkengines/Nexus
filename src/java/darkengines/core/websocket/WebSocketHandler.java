/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import darkengines.core.event.EventHandler;
import darkengines.user.User;
import java.util.EventListener;
import java.util.Hashtable;
import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author Quicksort
 */
public class WebSocketHandler {
    private Hashtable<User, Session> webSockets;
    public EventHandler<WebSocketConnectedEventArgs> connect;
    public EventHandler<WebSocketDisconnectedEventArgs> disconnect;
    public EventHandler<WebSocketMessageEventArgs> message;
    
    public WebSocketHandler() {
	webSockets = new Hashtable<User, Session>();
	connect = new EventHandler<WebSocketConnectedEventArgs>();
	disconnect = new EventHandler<WebSocketDisconnectedEventArgs>();
    }
    
    public void addSocket(User user, Session session) {
	webSockets.put(user, session);
	connect.execute(this, new WebSocketConnectedEventArgs(user, session));
    }
    
    public void removeSocket(User user, Session session) {
	webSockets.remove(user);
	disconnect.execute(this, new WebSocketDisconnectedEventArgs(user, session));
    }
}
