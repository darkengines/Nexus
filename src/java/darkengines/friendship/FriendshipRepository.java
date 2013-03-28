/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.friendship;

import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Quicksort
 */
public class FriendshipRepository extends Repository<Friendship> {

    @Override
    public void install() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("create_friendship_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
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
	    String query = getQuery("drop_friendship_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public void clear() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("truncate_friendship_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public Friendship map(ResultSet resultSet) throws SQLException {
	Friendship friendship = new Friendship(resultSet.getLong("id"), resultSet.getLong("owner"), resultSet.getLong("target"));
	return friendship;
    }

    public Iterable<Friendship> getFriendshipsByOwnerId(long id) {
	Collection<Friendship> friendships = new ArrayList<Friendship>();
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_user_by_owner_id.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, id);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			friendships.add(map(resultSet));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return friendships;
    }

    public Friendship getFriendshipsByOnwerAndTargetId(long oid, long tid) {
	Friendship friendship = null;
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_friendship_by_owner_and_target_id.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, oid);
		ps.setObject(2, tid);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			friendship = map(resultSet);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return friendship;
    }

    public Iterable<Friendship> getFriendshipsByTargetId(long id) {
	Collection<Friendship> friendships = new ArrayList<Friendship>();
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_user_by_target_id.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, id);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			friendships.add(map(resultSet));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return friendships;
    }

    public Friendship getFriendshipById(Long id) {
	Friendship friendship = null;
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_friendship_by_id.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, id);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			friendship = map(resultSet);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return friendship;
    }

    public boolean areFriends(Long a, Long b) {
	boolean result = false;
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("are_friends.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setLong(1, a);
		ps.setLong(2, b);
		ps.setLong(3, b);
		ps.setLong(4, a);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			result = resultSet.getBoolean("are_friends");
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return result;
    }

    public Friendship insertFriendship(Friendship friendship) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("insert_friendship.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setObject(1, friendship.getOwner());
		ps.setObject(2, friendship.getTarget());
		ps.executeUpdate();
		try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		    if (generatedKeys.next()) {
			friendship.setId(generatedKeys.getLong(1));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return friendship;
    }

    public void deleteFriendshipById(long id) {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("delete_friendship_by_id.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setObject(1, id);
		ps.executeUpdate();
	    }
	} catch (Exception e) {
	    Logger.getLogger(FriendshipRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}
