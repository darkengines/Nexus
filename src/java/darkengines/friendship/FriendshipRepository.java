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
import javax.naming.NamingException;

/**
 *
 * @author Quicksort
 */
public class FriendshipRepository extends Repository<Friendship> {

    @Override
    public void install() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException {
	String query = getQuery("create_friendship_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public void reinstall() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException {
	uninstall();
	install();
    }

    @Override
    public void uninstall() throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("drop_friendship_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public void clear() throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("truncate_friendship_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public Friendship map(ResultSet resultSet) throws SQLException {
	Friendship friendship = new Friendship(resultSet.getLong("id"), resultSet.getLong("owner"), resultSet.getLong("target"));
	return friendship;
    }
    
    public Iterable<Friendship> getFriendshipsByOwnerId(long id) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	Collection<Friendship> friendships = new ArrayList<Friendship>();
	String query = getQuery("get_user_by_owner_id.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setObject(1, id);
	ResultSet resultSet = ps.executeQuery();
	if (resultSet.next()) {
	    friendships.add(map(resultSet));
	}
	resultSet.close();
	ps.close();
	connection.close();
	return friendships;
    }
    
    public Friendship getFriendshipsByOnwerAndTargetId(long oid, long tid) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	Friendship friendship = null;
	String query = getQuery("get_friendship_by_owner_and_target_id.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setObject(1, oid);
	ps.setObject(2, tid);
	ResultSet resultSet = ps.executeQuery();
	if (resultSet.next()) {
	    friendship = map(resultSet);
	}
	resultSet.close();
	ps.close();
	connection.close();
	return friendship;
    }
    
    public Iterable<Friendship> getFriendshipsByTargetId(long id) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	Collection<Friendship> friendships = new ArrayList<Friendship>();
	String query = getQuery("get_user_by_target_id.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setObject(1, id);
	ResultSet resultSet = ps.executeQuery();
	if (resultSet.next()) {
	    friendships.add(map(resultSet));
	}
	resultSet.close();
	ps.close();
	connection.close();
	return friendships;
    }
    
     public Friendship getFriendshipById(Long id) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	Friendship friendship = null;
	String query = getQuery("get_friendship_by_id.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setObject(1, id);
	ResultSet resultSet = ps.executeQuery();
	if (resultSet.next()) {
	    friendship = map(resultSet);
	}
	resultSet.close();
	ps.close();
	connection.close();
	return friendship;
    }
    
     public boolean areFriends(Long a, Long b) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("are_friends.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setLong(1, a);
        ps.setLong(2, b);
	ps.setLong(3, b);
        ps.setLong(4, a);
	ResultSet resultSet = ps.executeQuery();
        boolean result = false;
	if (resultSet.next()) {
	    result = resultSet.getBoolean("are_friends");
	}
	resultSet.close();
	ps.close();
	connection.close();
	return result;
    }
     
    public Friendship insertFriendship(Friendship friendship) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("insert_friendship.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
	ps.setObject(1, friendship.getOwner());
	ps.setObject(2, friendship.getTarget());
	ps.executeUpdate();
	ResultSet generatedKeys = ps.getGeneratedKeys();
	if (generatedKeys.next()) {
	    friendship.setId(generatedKeys.getLong(1));
	}
	generatedKeys.close();
	ps.close();
	connection.close();
	return friendship;
    }
    public void deleteFriendshipById(long id) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("delete_friendship_by_id.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
	ps.setObject(1, id);
	ps.executeUpdate();
	ps.close();
	connection.close();
    }
}
