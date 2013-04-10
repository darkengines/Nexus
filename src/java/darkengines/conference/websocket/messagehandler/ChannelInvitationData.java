/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Quicksort
 */
public class ChannelInvitationData {
    private long id;
    private long channelId;
    private String channelName;

    public ChannelInvitationData(long id, long channelId, String channelName) {
	this.id = id;
	this.channelId = channelId;
	this.channelName = channelName;
    }
    
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getChannelId() {
	return channelId;
    }

    public void setChannelId(long channelId) {
	this.channelId = channelId;
    }

    public String getChannelName() {
	return channelName;
    }

    public void setChannelName(String channelName) {
	this.channelName = channelName;
    }
    
    public static ChannelInvitationData map(ResultSet result) throws SQLException {
	return new ChannelInvitationData(result.getLong("id"), result.getLong("channel_id"), result.getString("channel_name"));
    }
}
