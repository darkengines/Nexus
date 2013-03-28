/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.session;

import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Quicksort
 */
public class SessionRepository extends Repository<Session> {

    @Override
    public void install() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("create_session_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(SessionRepository.class.getName()).log(Level.SEVERE, null, e);
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
	    String query = getQuery("drop_session_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(SessionRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public void clear() {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("truncate_session_table.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.execute();
	    }
	} catch (Exception e) {
	    Logger.getLogger(SessionRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    @Override
    public Session map(ResultSet resultSet) throws SQLException {
	Session session = new Session();
	session.setId(resultSet.getLong("id"));
	session.setUserId(resultSet.getLong("user_id"));
	session.setUuid(UUID.fromString(resultSet.getString("uuid")));
	return session;
    }

    public Session insertSession(Session session) {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("insert_session.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setObject(1, session.getUserId());
		ps.setObject(2, session.getUuid().toString());
		ps.executeUpdate();
		try (ResultSet generatedKeys = ps.getGeneratedKeys();) {
		    if (generatedKeys.next()) {
			session.setId(generatedKeys.getLong(1));
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(SessionRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return session;
    }

    public void deleteSessionByUuid(UUID uuid) {
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("delete_session_by_uuid.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
		ps.setObject(1, uuid.toString());
		ps.executeUpdate();
	    }
	} catch (Exception e) {
	    Logger.getLogger(SessionRepository.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    public Session getSessionByUuid(UUID uuid) {
	Session session = null;
	try (Connection connection = Database.getConnection()) {
	    String query = getQuery("get_session_by_uuid.sql", true);
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
		ps.setObject(1, uuid.toString());
		try (ResultSet resultSet = ps.executeQuery()) {
		    if (resultSet.next()) {
			session = map(resultSet);
		    }
		}
	    }
	} catch (Exception e) {
	    Logger.getLogger(SessionRepository.class.getName()).log(Level.SEVERE, null, e);
	}
	return session;
    }
}
