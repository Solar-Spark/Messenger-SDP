package Client;

import java.io.IOException;

public interface ClientState {
    String registerUser(Client client, String username) throws IOException;
    String createChat(Client client, String receiverUsername) throws IOException;
    String createGroup(Client client, String groupName, String... members) throws IOException;
    String sendMessage(Client client, int chatId, String message) throws IOException;
    String getChatName(Client client, int chatId) throws IOException;
    String getChatIds(Client client) throws IOException;
    void disconnect(Client client) throws IOException;
}
