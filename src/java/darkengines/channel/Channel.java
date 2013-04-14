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
public class Channel {

    public static Channel Map(ResultSet result) throws SQLException {
	Channel channel = new Channel();
	channel.id = result.getLong("id");
	channel.name = result.getString("name");
	return channel;
    }
    private long id;
    private String name;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
