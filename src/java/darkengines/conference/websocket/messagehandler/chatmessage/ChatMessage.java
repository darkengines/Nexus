/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.websocket.messagehandler.chatmessage;

/**
 *
 * @author Quicksort
 */
public class ChatMessage {
    private Long authorId;
    private Long recipientId;
    private String content;
    
    public ChatMessage(Long authorId, Long recipientId, String content) {
        this.authorId = authorId;
        this.recipientId = recipientId;
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
