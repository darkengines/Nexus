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
import java.util.ArrayList;
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
    
    public boolean isParticipant(long channelId, long user_id) {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("is_participant.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, channelId);
		ps.setObject(2, user_id);
		try (ResultSet result = ps.executeQuery()) {
		    if (result.next()) {
			return result.getBoolean("is_participant");
		    }
		    return false;
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelParticipantRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return false;
    }
    
    public ArrayList<Long> getChannelParticipants(long channelId) {
	ArrayList<Long> ids = new ArrayList<>();
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_channel_participants.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, channelId);
		try (ResultSet result = ps.executeQuery()) {
		    while (result.next()) {
			ids.add(result.getLong("user_id"));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelParticipantRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return ids;
    }
}
