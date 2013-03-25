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
class WebSocketDisconnectedEventListener implements IListener<WebSocketDisconnectedEventArgs> {
    private final WebSocketManager webSocketManager;
    public WebSocketDisconnectedEventListener(WebSocketManager webSocketManager) {
	this.webSocketManager = webSocketManager;
    }

    @Override
    public void callback(Object sender, WebSocketDisconnectedEventArgs eventArgs) {
	webSocketManager.removeSocket(eventArgs.getUser(), eventArgs.getWebSocket());
    }
}
