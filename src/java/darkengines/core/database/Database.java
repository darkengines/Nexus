/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Quicksort
 */
public class Database {

    private static DataSource dataSource = null;
    private static Database instance = null;

    private Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/db");
            } catch (NamingException e) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Cannot find datasource", e);
            }
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Cannot load driver", e);
        }
    }

    public static Connection getConnection() {
	try {
            if (instance == null) {
                instance = new Database();
            }
            return Database.dataSource.getConnection();
        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Cannot open database connection", e);
            return null;
        }
    }
    
    public static void CloseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Cannot close database connection", e);
            }
        }
    }
    public static void CloseStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Cannot close statement", e);
            }
        }
    }
    public static void CloseResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Cannot close resultSet", e);
            }
        }
    }
}
