/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.core.websocket.WebSocketFactory;
import darkengines.core.websocket.WebSocketManager;
import darkengines.nexus.NexusWebSocket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 *
 * @author Quicksort
 */
public class WebSocket extends WebSocketServlet {
    
    private WebSocketManager webSocketManager;
    
    @Override
    public void init() throws ServletException {
	super.init();
	webSocketManager = new WebSocketManager();
    }
    
    @Override
    public void configure(WebSocketServletFactory wssf) {
	wssf.getPolicy().setIdleTimeout(10000);
	wssf.setCreator(new WebSocketFactory(webSocketManager));
    }
  
}
