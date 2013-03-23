/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.core.websocket.WebSocketFactory;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessageManager;
import darkengines.core.websocket.WebSocketMessageType;
import darkengines.core.websocket.messagehandler.GetFriends;
import javax.servlet.ServletException;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 *
 * @author Quicksort
 */
public class WebSocket extends WebSocketServlet {
    
    private WebSocketManager webSocketManager;
    private WebSocketMessageManager webSocketMessageManager;
    @Override
    public void init() throws ServletException {
	super.init();
	webSocketManager = new WebSocketManager();
        webSocketMessageManager = new WebSocketMessageManager();
        webSocketMessageManager.registerMessageHandler(WebSocketMessageType.GET_FRIENDS, new GetFriends(webSocketManager));
    }
    
    @Override
    public void configure(WebSocketServletFactory wssf) {
	wssf.getPolicy().setIdleTimeout(10000);
	wssf.setCreator(new WebSocketFactory(webSocketManager, webSocketMessageManager));
    }
  
}
