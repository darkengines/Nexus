/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.channel;

/**
 *
 * @author Quicksort
 */
public class ChannelParticipant {
    private long userId;
    private long channelId;

    public ChannelParticipant(long channelId, long userId) {
	this.userId = userId;
	this.channelId = channelId;
    }

    public ChannelParticipant() {
	
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
