package Client;

public class DisconnectedState implements ClientState {
    @Override
    public String registerUser(Client client, String username) {
        throw new IllegalStateException("User is not connected");
    }
    @Override
    public String createChat(Client client, String receiverUsername) {
        throw new IllegalStateException("User is not connected");
    }
    @Override
    public String createGroup(Client client, String groupName, String... members) {
        throw new IllegalStateException("User is not connected");
    }
    @Override
    public String sendMessage(Client client, int chatid, String message) {
        throw new IllegalStateException("User is not connected");
    }
    @Override
    public String getChatName(Client client, int chatid) {
        throw new IllegalStateException("User is not connected");
    }
    @Override
    public String getChatIds(Client client) {
        throw new IllegalStateException("User is not connected");
    }
    @Override
    public void disconnect(Client client) {
        System.out.println("Already disconnected");
    }
}
