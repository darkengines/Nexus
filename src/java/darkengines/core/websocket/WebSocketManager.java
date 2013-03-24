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
    public EventHandler<WebSocketConnectedEventArgs> connectedUser;
    public EventHandler<WebSocketDisconnectedEventArgs> disconnectedUser;
    
    public WebSocketManager() {
	webSockets = new HashMap<Long, HashMap<Integer, WebSocket>>();
	connectedUser = new EventHandler<WebSocketConnectedEventArgs>();
	disconnectedUser = new EventHandler<WebSocketDisconnectedEventArgs>();
    }
    
    public void addSocket(User user, WebSocket webSocket) {
	if (!webSockets.containsKey(user.getId())) {
	    webSockets.put(user.getId(), new HashMap<Integer, WebSocket>());
	    connectedUser.execute(this, new WebSocketConnectedEventArgs(user, webSocket));
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
		    disconnectedUser.execute(this, new WebSocketDisconnectedEventArgs(user, webSocket));
		}
	    }
	}
    }
    
    public Collection<WebSocket> getUserSessions(Long user) {
	if (webSockets.containsKey(user)) {
	    return webSockets.get(user).values();
	} else {
	    return new ArrayList<WebSocket>();
	}
    }
    
    public WebSocket getUserSession(Long user, Integer sessionHash) {
	WebSocket socket = null;
	if (webSockets.containsKey(user)) {
	    if (webSockets.get(user).containsKey(sessionHash)) {
		socket = webSockets.get(user).get(sessionHash);
	    }
	}
	return socket;
    }
    
    public Collection<WebSocket> getUsersSessions(Collection<Long> users) {
	ArrayList<WebSocket> sessions = new ArrayList<WebSocket>();
	for (Long user: users) {
	    if (webSockets.containsKey(user)) {
		sessions.addAll(getUserSessions(user));
	    }
	}
	return sessions;
    }
    
    public Collection<WebSocket> getUsersSession(Collection<Pair<Long, Integer>> pairs) {
	ArrayList<WebSocket> sessions = new ArrayList<WebSocket>();
	WebSocket socket = null;
	for (Pair<Long, Integer> pair: pairs) {
	    socket = getUserSession(pair.getLeft(), pair.getRight());
	    if (socket != null) {
		sessions.add(socket);
	    }
	}
	return sessions;
    }
}
