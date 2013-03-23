/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import darkengines.core.event.EventHandler;
import darkengines.nexus.Nexus;
import darkengines.nexus.NexusWebSocket;
import darkengines.session.SessionModule;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

/**
 *
 * @author Quicksort
 */
public class WebSocket implements WebSocketListener {

    private static MessageSerializer serializer;

    static {
        serializer = new MessageSerializer();
    }
    private User user;
    private Session session;
    public EventHandler<WebSocketConnectedEventArgs> connectedSocket;
    public EventHandler<WebSocketDisconnectedEventArgs> disconnectedSocket;
    public EventHandler<WebSocketMessageEventArgs> receivedMessage;

    public WebSocket(User user) {
        super();
        this.user = user;
    }

    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWebSocketClose(int i, String string) {
        disconnectedSocket.execute(this, new WebSocketDisconnectedEventArgs(user, this));
    }

    @Override
    public void onWebSocketConnect(Session sn) {
        session = sn;
        connectedSocket.execute(this, new WebSocketConnectedEventArgs(user, this));
    }

    @Override
    public void onWebSocketError(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWebSocketText(String message) {
        receivedMessage.execute(this, new WebSocketMessageEventArgs(user, this, serializer.deserialize(message)));
    }
    
    public void sendMessage(WebSocketMessage message) {
        try {
            session.getRemote().sendString(serializer.Serialize(message));
        } catch (IOException ex) {
            Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
