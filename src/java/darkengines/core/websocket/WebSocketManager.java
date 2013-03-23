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
    private HashMap<Long, HashMap<Integer, Session>> webSockets;
    public EventHandler<WebSocketConnectedEventArgs> connect;
    public EventHandler<WebSocketDisconnectedEventArgs> disconnect;
    
    public WebSocketManager() {
	webSockets = new HashMap<Long, HashMap<Integer, Session>>();
	connect = new EventHandler<WebSocketConnectedEventArgs>();
	disconnect = new EventHandler<WebSocketDisconnectedEventArgs>();
    }
    
    public void addSocket(User user, Session session) {
	if (!webSockets.containsKey(user.getId())) {
	    webSockets.put(user.getId(), new HashMap<Integer, Session>());
	    //User connection
	}
	HashMap<Integer, Session> userSockets = webSockets.get(user.getId());
	userSockets.put(session.hashCode(), session);
    }
    
    public void removeSocket(User user, Session session) {
	if (webSockets.containsKey(user.getId())) {
	    HashMap<Integer, Session> userSockets = webSockets.get(user.getId());
	    if (userSockets.containsKey(session.hashCode())) {
		userSockets.remove(session.hashCode());
		if (userSockets.isEmpty()) {
		    webSockets.remove(user.getId());
		    //User disconnection
		}
	    }
	}
    }
    
    public Collection<Session> getUserSessions(User user) {
	return webSockets.get(user.getId()).values();
    }
    
    public Session getUserSession(User user, Integer sessionHash) {
	return webSockets.get(user.getId()).get(sessionHash);
    }
    
    public Collection<Session> getUsersSessions(Collection<User> users) {
	ArrayList<Session> sessions = new ArrayList<Session>();
	for (User user: users) {
	    sessions.addAll(getUserSessions(user));
	}
	return sessions;
    }
    
    public Collection<Session> getUsersSession(Collection<Pair<User, Integer>> pairs) {
	ArrayList<Session> sessions = new ArrayList<Session>();
	for (Pair<User, Integer> pair: pairs) {
	    sessions.add(getUserSession(pair.getLeft(), pair.getRight()));
	}
	return sessions;
    }
}
