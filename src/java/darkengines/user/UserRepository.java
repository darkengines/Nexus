/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.user;

import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Quicksort
 */
public class UserRepository extends Repository<User> {

    @Override
    public void install() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("create_user_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, e);
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
	    String query = getQuery("drop_user_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public void clear() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("truncate_user_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public User map(ResultSet resultSet) throws SQLException {
	User user = new User();
	user.setId(resultSet.getLong("id"));
	user.setEmail(resultSet.getString("email"));
	user.setPassword(resultSet.getString("password"));
	user.setDisplayName(resultSet.getString("display_name"));
	return user;
    }

    public User getUserByEmail(String email) {
	User user = null;
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_user_by_email.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, email);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			user = map(resultSet);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return user;
    }

    public User getUserById(Long id) {
	User user = null;
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_user_by_id.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, id);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			user = map(resultSet);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return user;
    }

    public User getUserByCredentials(String email, String password) {
	User user = null;
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_user_by_credentials.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, email);
		ps.setObject(2, password);
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			user = map(resultSet);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return user;
    }

    public User insertUser(User user) {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("insert_user.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setObject(1, user.getEmail());
		ps.setObject(2, user.getPassword());
		ps.setObject(3, user.getDisplayName());
		ps.executeUpdate();
		try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		    if (generatedKeys.next()) {
			user.setId(generatedKeys.getLong(1));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return user;
    }

    public ArrayList searchByEmail(String[] words) {
	ArrayList<User> users = new ArrayList();
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("search_by_email.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setString(1, StringUtils.join(words, "|"));
		try (ResultSet resultSet = ps.executeQuery()) {
		    while (resultSet.next()) {
			users.add(map(resultSet));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(UserRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return users;
    }
}
