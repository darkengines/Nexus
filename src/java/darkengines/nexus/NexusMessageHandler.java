/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import com.google.gson.JsonElement;

/**
 *
 * @author Quicksort
 */
public abstract class NexusMessageHandler {
    
    private final Iterable<NexusWebSocket> sockets;
    
    public NexusMessageHandler(Iterable<NexusWebSocket> sockets) {
	this.sockets = sockets;
    }
    
    public void _processMessage(JsonElement data) {
	synchronized(sockets) {
	    processMessage(data);
	}
    }

    protected abstract void processMessage(JsonElement data);
    
}
