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
public interface INexusMessageHandler {
    void processMessage(JsonElement data);
}
