/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import darkengines.core.event.IListener;

/**
 *
 * @author Quicksort
 */
class WebSocketConnectedEventListener implements IListener<WebSocketConnectedEventArgs> {
    private final WebSocketManager webSocketManager;
    public WebSocketConnectedEventListener(WebSocketManager webSocketManager) {
	this.webSocketManager = webSocketManager;
    }

    @Override
    public void callback(Object sender, WebSocketConnectedEventArgs eventArgs) {
	webSocketManager.addSocket(eventArgs.getUser(), eventArgs.getSession());
    }
        
}
