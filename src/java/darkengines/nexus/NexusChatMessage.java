/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import darkengines.user.User;

/**
 *
 * @author Quicksort
 */
public class NexusChatMessage {
    private UserItem author;
    private UserItem recipient;
    private String content;

    public void setAuthor(UserItem author) {
	this.author = author;
    }

    public UserItem getAuthor() {
	return author;
    }

    public UserItem getRecipient() {
	return recipient;
    }

    public void setRecipient(UserItem recipient) {
	this.recipient = recipient;
    }
    
    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }
}
