/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import darkengines.core.event.IListener;
import darkengines.core.websocket.ConnectEventArgs;

/**
 *
 * @author Quicksort
 */
class WebSocketMessageListener implements IListener<ConnectEventArgs> {

    public WebSocketMessageListener() {
    }

    @Override
    public void callback(Object sender, ConnectEventArgs eventArgs) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
