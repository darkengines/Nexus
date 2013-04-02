/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.init;

import darkengines.channel.ChannelInvitation;
import java.util.ArrayList;

/**
 *
 * @author Quicksort
 */
public class InitializationData {
    private ArrayList<UserData> users;
    private ArrayList<ChannelData> channels;
    private ArrayList<Long> friends;
    private ArrayList<ClientFriendRequest> friendRequests;
    private ArrayList<ClientFriendRequest> requestedFriends;
    private ArrayList<Long> channelInvitations;
    
    public InitializationData() {
	users = new ArrayList<>();
	friends = new ArrayList<>();
	channels = new ArrayList<>();
	friendRequests = new ArrayList<>();
	requestedFriends = new ArrayList<>();
	channelInvitations = new ArrayList<>();
    }

    public ArrayList<UserData> getUsers() {
	return users;
    }

    public void setUsers(ArrayList<UserData> users) {
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
