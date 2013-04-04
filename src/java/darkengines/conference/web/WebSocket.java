/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.conference.websocket.messagehandler.getfriendrequests.GetFriendRequests;
import darkengines.conference.websocket.eventlistener.UserConnectedEventListener;
import darkengines.conference.websocket.eventlistener.UserDisconnectedEventListener;
import darkengines.conference.websocket.messagehandler.AcceptFriendRequest;
import darkengines.conference.websocket.messagehandler.SendChatMessage;
import darkengines.conference.websocket.messagehandler.getfriends.GetFriends;
import darkengines.conference.websocket.messagehandler.getrequestedfriends.GetRequestedFriends;
import darkengines.conference.websocket.messagehandler.getuserdata.GetUserData;
import darkengines.conference.websocket.messagehandler.Init;
import darkengines.conference.websocket.messagehandler.MakeFriend;
import darkengines.conference.websocket.messagehandler.SearchUser;
import darkengines.conference.websocket.messagehandler.RejectFriendRequest;
import darkengines.core.websocket.WebSocketFactory;
import darkengines.core.websocket.WebSocketManager;
import darkengines.core.websocket.WebSocketMessageManager;
import darkengines.core.websocket.WebSocketMessageType;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 *
 * @author Quicksort
 */
public class WebSocket extends WebSocketServlet {
    
    private WebSocketManager webSocketManager;
    private WebSocketMessageManager webSocketMessageManager;
    private UserConnectedEventListener userConnectedEventListener;
    private UserDisconnectedEventListener userDisconnectedEventListener;
    
    public WebSocket() {
	super();
	webSocketManager = new WebSocketManager();
        webSocketMessageManager = new WebSocketMessageManager();
	
	userConnectedEventListener = new UserConnectedEventListener(webSocketManager);
	userDisconnectedEventListener = new UserDisconnectedEventListener(webSocketManager);
	
	webSocketManager.connectedUser.addListener(userConnectedEventListener);
	webSocketManager.disconnectedUser.addListener(userDisconnectedEventListener);
	
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.MAKE_FRIEND, new MakeFriend(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.SEARCH, new SearchUser(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.ACCEPT_FRIEND_REQUEST, new AcceptFriendRequest(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.REJECT_FRIEND_REQUEST, new RejectFriendRequest(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CHAT_MESSAGE, new SendChatMessage(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.INIT, new Init(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.USER_DATA, new GetUserData(webSocketManager));
    }   
    
    @Override
    public void configure(WebSocketServletFactory wssf) {
	wssf.getPolicy().setIdleTimeout(10000);
	wssf.setCreator(new WebSocketFactory(webSocketManager, webSocketMessageManager));
    }
  
}
