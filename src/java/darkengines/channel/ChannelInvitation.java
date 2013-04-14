/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.channel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Quicksort
 */
public class ChannelInvitation {

    public static ChannelInvitation map(ResultSet result) throws SQLException {
	ChannelInvitation invitation = new ChannelInvitation();
	invitation.setId(result.getLong("id"));
	invitation.setChannelId(result.getLong("channel_id"));
	invitation.setUserId(result.getLong("user_id"));
	return invitation;
    }
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
