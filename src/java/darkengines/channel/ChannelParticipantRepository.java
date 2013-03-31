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
public class ChannelParticipantRepository extends Repository<ChannelParticipant> {

    @Override
    public void install() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("create_channel_participant_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelParticipantRepository.class.getName()).log(Level.SEVERE, null, e);
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
	    String query = getQuery("drop_channel_participant_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelParticipantRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public void clear() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("truncate_channel_participant_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelParticipantRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public ChannelParticipant map(ResultSet resultSet) throws SQLException {
	ChannelParticipant channelParticipant = new ChannelParticipant();
	channelParticipant.setUserId(resultSet.getLong("user_id"));
	channelParticipant.setChannelId(resultSet.getLong("channel_id"));
	return channelParticipant;
    }
    
    public ChannelParticipant insertChannelParticipant(ChannelParticipant channelParticipant) {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("insert_channel_participant.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setObject(1, channelParticipant.getChannelId());
		ps.setObject(2, channelParticipant.getUserId());
		ps.executeUpdate();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelParticipantRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return channelParticipant;
    }
}
