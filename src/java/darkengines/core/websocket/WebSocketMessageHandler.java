/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.websocket;

import com.google.gson.Gson;

/**
 *
 * @author Quicksort
 */
public abstract class WebSocketMessageHandler {
    private Gson gson;
    public WebSocketMessageHandler() {
	gson = new Gson();
    }
}
