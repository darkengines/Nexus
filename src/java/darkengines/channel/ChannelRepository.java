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
public class ChannelRepository extends Repository<Channel> {

    @Override
    public void install() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("create_channel_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelRepository.class.getName()).log(Level.SEVERE, null, e);
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
	    String query = getQuery("drop_channel_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public void clear() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("truncate_channel_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public Channel map(ResultSet resultSet) throws SQLException {
	Channel channel = new Channel();
	channel.setId(resultSet.getLong("id"));
	channel.setName(resultSet.getString("name"));
	return channel;
    }
    
    public Channel insertChannel(Channel channel) {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("insert_channel.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setObject(1, channel.getName());
		ps.executeUpdate();
		try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		    if (generatedKeys.next()) {
			channel.setId(generatedKeys.getLong(1));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return channel;
    }

    public Channel getChannelById(long channelId) {
	Channel channel = null;
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_channel_by_id.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, channelId);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			channel = map(resultSet);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(ChannelRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return channel;
    }
    
}
