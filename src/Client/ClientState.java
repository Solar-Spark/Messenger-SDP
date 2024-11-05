package Client;

import java.io.IOException;

public interface ClientState {
    String registerUser(String username) throws IOException;
    String createChat(String receiverUsername) throws IOException;
    String createGroup(String groupChat) throws IOException;
    String sendMessage(int chatId, String message) throws IOException;
    String getChatName(int chatId) throws IOException;
    String getChatIds() throws IOException;
    void disconnect() throws IOException;
    String getMessages(int chatId) throws IOException;
}
