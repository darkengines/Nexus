/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.channel;

/**
 *
 * @author Quicksort
 */
public class ChannelInvitation {
    private long id;
    private long userId;
    private long channelId;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public long getChannelId() {
	return channelId;
    }

    public void setChannelId(long channelId) {
	this.channelId = channelId;
    }
}
