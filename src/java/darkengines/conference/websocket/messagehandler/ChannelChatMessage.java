/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

/**
 *
 * @author Quicksort
 */
class ChannelChatMessage {
    long id;
    long authorId;
    long channelId;

    public long getChannelId() {
	return channelId;
    }

    public void setChannelId(long channelId) {
	this.channelId = channelId;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getAuthorId() {
	return authorId;
    }

    public void setAuthorId(long authorId) {
	this.authorId = authorId;
    }
        
}
