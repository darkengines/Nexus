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
    private ArrayList<ChannelData> channels;
    private ArrayList<Long> friends;
    private ArrayList<ClientFriendRequest> friendRequests;
    private ArrayList<ClientFriendRequest> requestedFriends;
    private ArrayList<Long> channelInvitations;
    
    public InitializationData() {
	users = new HashMap<>();
	friends = new ArrayList<>();
	channels = new ArrayList<>();
	friendRequests = new ArrayList<>();
	requestedFriends = new ArrayList<>();
	channelInvitations = new ArrayList<>();
    }

    public HashMap<Long, UserData> getUsers() {
	return users;
    }

    public void setUsers(HashMap<Long, UserData> users) {
	this.users = users;
    }

    public ArrayList<ChannelData> getChannels() {
	return channels;
    }

    public void setChannels(ArrayList<ChannelData> channels) {
	this.channels = channels;
    }

    public ArrayList<Long> getFriends() {
	return friends;
    }

    public void setFriends(ArrayList<Long> friends) {
	this.friends = friends;
    }

    public ArrayList<ClientFriendRequest> getFriendRequests() {
	return friendRequests;
    }

    public void setFriendRequests(ArrayList<ClientFriendRequest> friendRequests) {
	this.friendRequests = friendRequests;
    }

    public ArrayList<ClientFriendRequest> getRequestedFriends() {
	return requestedFriends;
    }

    public void setRequestedFriends(ArrayList<ClientFriendRequest> requestedFriends) {
	this.requestedFriends = requestedFriends;
    }

    public ArrayList<Long> getChannelInvitations() {
	return channelInvitations;
    }

    public void setChannelInvitations(ArrayList<Long> channelInvitations) {
	this.channelInvitations = channelInvitations;
    }
    
}
