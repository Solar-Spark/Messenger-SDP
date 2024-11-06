package Server;

import java.io.Serializable;
import java.util.Date;

public class Message implements MessageInterface{
    private User user;
    private String messageText;

    public Message(User user, String message) {
        this.user = user;
        this.messageText = message;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
