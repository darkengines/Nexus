/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import darkengines.session.Session;
import darkengines.session.SessionModule;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 *
 * @author Quicksort
 */
public class WebSocketFactory implements WebSocketCreator {

    private WebSocketConnectedEventListener connectedSocketEventListener;
    private WebSocketDisconnectedEventListener disconnectedSocketEventListener;
    private WebSocketMessageEventListener receivedMessageSocketEventListener;
    public WebSocketFactory(WebSocketManager webSocketManager) {
	connectedSocketEventListener = new WebSocketConnectedEventListener(webSocketManager);
	disconnectedSocketEventListener = new WebSocketDisconnectedEventListener(webSocketManager);
    }
    @Override
    public Object createWebSocket(UpgradeRequest req, UpgradeResponse resp) {
	String suuid = req.getParameterMap().get("uuid")[0];
	if (suuid != null) {
	    try {
		Session session = SessionModule.getSessionRepository().getSessionByUuid(UUID.fromString(suuid));
		if (session != null) {
		    User user = UserModule.getUserRepository().getUserById(session.getUserId());
		    if (user != null) {
			WebSocket socket = new WebSocket(user);
			socket.connectedSocket.addListener(connectedSocketEventListener);
			socket.disconnectedSocket.addListener(disconnectedSocketEventListener);
			socket.disconnectedSocket.addListener(disconnectedSocketEventListener);
			return socket;
		    }
		}
	    } catch (Exception e) {
		Logger.getLogger(WebSocketFactory.class.getName()).log(Level.SEVERE, null, e);
		return null;
	    }
	}
	return null;
    }
}
