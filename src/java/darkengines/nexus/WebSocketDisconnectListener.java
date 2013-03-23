/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import darkengines.core.event.IListener;
import darkengines.core.websocket.DisconnectEventArgs;

/**
 *
 * @author Quicksort
 */
class WebSocketDisconnectListener implements IListener<DisconnectEventArgs> {

    public WebSocketDisconnectListener() {
    }

    @Override
    public void callback(Object sender, DisconnectEventArgs eventArgs) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
