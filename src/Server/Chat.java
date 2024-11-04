package Server;

import java.io.IOException;
import java.util.ArrayList;

public interface Chat {
    void subscribe(User user) throws IOException;
    void unsubscribe(User user) throws IOException;
    void sendMessage(Message msg) throws IOException;
    void getMessages(User user) throws IOException;
    ArrayList<Message> getMessagesHistory() throws IOException;
    ArrayList<User> getUsers() throws IOException;
    String getName(User user);
    int getId();
}
