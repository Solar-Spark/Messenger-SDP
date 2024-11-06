package Server;

public interface MessageInterface {
    User getUser();
    void setUser(User user);
    String getMessageText();
    void setMessageText(String messageText);
}
