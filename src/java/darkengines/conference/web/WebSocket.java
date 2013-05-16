/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.conference.websocket.messagehandler.getfriendrequests.GetFriendRequests;
import darkengines.conference.websocket.eventlistener.UserConnectedEventListener;
import darkengines.conference.websocket.eventlistener.UserDisconnectedEventListener;
import darkengines.conference.websocket.messagehandler.AcceptFriendRequest;
import darkengines.conference.websocket.messagehandler.Answer;
import darkengines.conference.websocket.messagehandler.Call;
import darkengines.conference.websocket.messagehandler.ChannelAnswer;
import darkengines.conference.websocket.messagehandler.ChannelCall;
import darkengines.conference.websocket.messagehandler.ChannelIceCandidate;
import darkengines.conference.websocket.messagehandler.ChannelInvitationHandler;
import darkengines.conference.websocket.messagehandler.ChannelOffer;
import darkengines.conference.websocket.messagehandler.CreateChannel;
import darkengines.conference.websocket.messagehandler.HangUp;
import darkengines.conference.websocket.messagehandler.IceCandidate;
import darkengines.conference.websocket.messagehandler.SendChatMessage;
import darkengines.conference.websocket.messagehandler.getfriends.GetFriends;
import darkengines.conference.websocket.messagehandler.getrequestedfriends.GetRequestedFriends;
import darkengines.conference.websocket.messagehandler.getuserdata.GetUserData;
import darkengines.conference.websocket.messagehandler.Init;
import darkengines.conference.websocket.messagehandler.JoinChannel;
import darkengines.conference.websocket.messagehandler.MakeFriend;
import darkengines.conference.websocket.messagehandler.Offer;
import darkengines.conference.websocket.messagehandler.SearchUser;
import darkengines.conference.websocket.messagehandler.RejectFriendRequest;
import darkengines.conference.websocket.messagehandler.SendChannelChatMessage;
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
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.OFFER, new Offer(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.ANSWER, new Answer(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.ICE_CANDIDATE, new IceCandidate(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CREATE_CHANNEL, new CreateChannel());
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CHANNEL_INVITATION, new ChannelInvitationHandler(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.ACCEPT_CHANNEL_INVITATION, new JoinChannel(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CHANNEL_CHAT_MESSAGE, new SendChannelChatMessage(webSocketManager));
        webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CALL, new Call(webSocketManager));
        webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CHANNEL_CALL, new ChannelCall(webSocketManager));
        webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CHANNEL_OFFER, new ChannelOffer(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CHANNEL_ANSWER, new ChannelAnswer(webSocketManager));
        webSocketMessageManager.registerMessageHandler(WebSocketMessageType.CHANNEL_ICE_CANDIDATE, new ChannelIceCandidate(webSocketManager));
	webSocketMessageManager.registerMessageHandler(WebSocketMessageType.HANGUP, new HangUp(webSocketManager));
    }   
    
    @Override
    public void configure(WebSocketServletFactory wssf) {
	wssf.getPolicy().setIdleTimeout(10000);
	wssf.setCreator(new WebSocketFactory(webSocketManager, webSocketMessageManager));
    }
  
}
