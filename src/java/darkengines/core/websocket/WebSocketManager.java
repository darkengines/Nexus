/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import darkengines.core.event.EventHandler;
import darkengines.user.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.jetty.websocket.api.Session;

/**
 *
 * @author Quicksort
 */
public class WebSocketManager {
    private HashMap<Long, HashMap<Integer, WebSocket>> webSockets;
    public EventHandler<WebSocketConnectedEventArgs> connect;
    public EventHandler<WebSocketDisconnectedEventArgs> disconnect;
    
    public WebSocketManager() {
	webSockets = new HashMap<Long, HashMap<Integer, WebSocket>>();
	connect = new EventHandler<WebSocketConnectedEventArgs>();
	disconnect = new EventHandler<WebSocketDisconnectedEventArgs>();
    }
    
    public void addSocket(User user, WebSocket webSocket) {
	if (!webSockets.containsKey(user.getId())) {
	    webSockets.put(user.getId(), new HashMap<Integer, WebSocket>());
	    //User connection
	}
	HashMap<Integer, WebSocket> userSockets = webSockets.get(user.getId());
	userSockets.put(webSocket.hashCode(), webSocket);
    }
    
    public void removeSocket(User user, WebSocket webSocket) {
	if (webSockets.containsKey(user.getId())) {
	    HashMap<Integer, WebSocket> userSockets = webSockets.get(user.getId());
	    if (userSockets.containsKey(webSocket.hashCode())) {
		userSockets.remove(webSocket.hashCode());
		if (userSockets.isEmpty()) {
		    webSockets.remove(user.getId());
		    //User disconnection
		}
	    }
	}
    }
    
    public Collection<WebSocket> getUserSessions(User user) {
	return webSockets.get(user.getId()).values();
    }
    
    public WebSocket getUserSession(User user, Integer sessionHash) {
	return webSockets.get(user.getId()).get(sessionHash);
    }
    
    public Collection<WebSocket> getUsersSessions(Collection<User> users) {
	ArrayList<WebSocket> sessions = new ArrayList<WebSocket>();
	for (User user: users) {
	    sessions.addAll(getUserSessions(user));
	}
	return sessions;
    }
    
    public Collection<WebSocket> getUsersSession(Collection<Pair<User, Integer>> pairs) {
	ArrayList<WebSocket> sessions = new ArrayList<WebSocket>();
	for (Pair<User, Integer> pair: pairs) {
	    sessions.add(getUserSession(pair.getLeft(), pair.getRight()));
	}
	return sessions;
    }
}
