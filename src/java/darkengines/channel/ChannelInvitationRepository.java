/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.channel;

import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class ChannelInvitationRepository extends Repository<ChannelInvitation>{

    @Override
    public void install() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("create_channel_invitation_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelInvitationRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public void reinstall() {
	uninstall();
	install();
    }

    @Override
    public void uninstall() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("drop_channel_invitation_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelInvitationRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public void clear() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("truncate_channel_invitation_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelInvitationRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public ChannelInvitation map(ResultSet resultSet) throws SQLException {
	ChannelInvitation channelInvitation = new ChannelInvitation();
	channelInvitation.setId(resultSet.getLong("id"));
	channelInvitation.setUserId(resultSet.getLong("user_id"));
	channelInvitation.setChannelId(resultSet.getLong("channel_id"));
	return channelInvitation;
    }
    
    public ChannelInvitation insertChannel(ChannelInvitation channelInvitation) {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("insert_channel_invitation.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setObject(1, channelInvitation.getChannelId());
		ps.setObject(2, channelInvitation.getUserId());
		ps.executeUpdate();
		try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		    if (generatedKeys.next()) {
			channelInvitation.setId(generatedKeys.getLong(1));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelInvitationRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return channelInvitation;
    }
    
}
