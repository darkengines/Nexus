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
class WebSocketMessageEventListener implements IListener<WebSocketMessageEventArgs> {
    private final WebSocketManager webSocketManager;
    private final WebSocketMessageManager messageManager;
    public WebSocketMessageEventListener(WebSocketManager webSocketManager, WebSocketMessageManager webSocketMessageManager) {
	this.webSocketManager = webSocketManager;
	this.messageManager = webSocketMessageManager;
    }

    @Override
    public void callback(Object sender, WebSocketMessageEventArgs eventArgs) {
	messageManager.processMessage(eventArgs.getUser(), eventArgs.getSession(), eventArgs.getMessage());
    }
}
