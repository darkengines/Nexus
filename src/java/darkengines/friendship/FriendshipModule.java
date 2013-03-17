/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.friendship;

import darkengines.user.*;

/**
 *
 * @author Quicksort
 */
public class FriendshipModule {
    private static FriendshipRepository friendshipRepository = null;
    public static FriendshipRepository getFriendshipRepository() {
	if (friendshipRepository == null) {
	    friendshipRepository = new FriendshipRepository();
	}
	return friendshipRepository;
    }
}
