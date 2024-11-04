package Server;

import java.io.IOException;

public interface Chat {
    void subscribe(User user) throws IOException;
    void unsubscribe(User user) throws IOException;
    void sendMessage(Message msg) throws IOException;
    void getMessages(User user) throws IOException;
    int getId();
}
