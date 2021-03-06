/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import darkengines.channel.ChannelInvitation;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Quicksort
 */
public class InitializationData {
    private HashMap<Long, UserData> users;
    private HashMap<Long, ChannelData> channels;
    private HashMap<Long, Long> friends;
    private HashMap<Long, Long> availableChannels;
    private HashMap<Long, ClientFriendRequest> friendRequests;
    private HashMap<Long, ClientFriendRequest> requestedFriends;
    private HashMap<Long, ChannelInvitation> channelInvitations;
    private int uniqueId;

    public int getUniqueId() {
	return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
	this.uniqueId = uniqueId;
    }
    
    public InitializationData() {
	users = new HashMap<>();
	friends = new HashMap<>();
	channels = new HashMap<>();
	friendRequests = new HashMap<>();
	requestedFriends = new HashMap<>();
	channelInvitations = new HashMap<>();
	availableChannels = new HashMap<>();
    }

    public HashMap<Long, UserData> getUsers() {
	return users;
    }

    public void setUsers(HashMap<Long, UserData> users) {
	this.users = users;
    }

    public HashMap<Long, ChannelData> getChannels() {
	return channels;
    }

    public void setChannels(HashMap<Long, ChannelData> channels) {
	this.channels = channels;
    }

    public HashMap<Long, Long> getFriends() {
	return friends;
    }

    public void setFriends(HashMap<Long, Long> friends) {
	this.friends = friends;
    }

    public HashMap<Long, ClientFriendRequest> getFriendRequests() {
	return friendRequests;
    }

    public void setFriendRequests(HashMap<Long, ClientFriendRequest> friendRequests) {
	this.friendRequests = friendRequests;
    }

    public HashMap<Long, ClientFriendRequest> getRequestedFriends() {
	return requestedFriends;
    }

    public void setRequestedFriends(HashMap<Long, ClientFriendRequest> requestedFriends) {
	this.requestedFriends = requestedFriends;
    }

    public HashMap<Long, ChannelInvitation> getChannelInvitations() {
	return channelInvitations;
    }

    public void setChannelInvitations(HashMap<Long, ChannelInvitation> channelInvitations) {
	this.channelInvitations = channelInvitations;
    }

    public HashMap<Long, Long> getAvailableChannels() {
	return availableChannels;
    }

    public void setAvailableChannels(HashMap<Long, Long> availableChannels) {
	this.availableChannels = availableChannels;
    }
    
}
