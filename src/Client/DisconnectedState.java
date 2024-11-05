package Client;

public class DisconnectedState implements ClientState {
    @Override
    public String registerUser(Client client, String username) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String createChat(Client client, String receiverUsername) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String createGroup(Client client, String groupName, String... members) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String sendMessage(Client client, int chatid, String message) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String getChatName(Client client, int chatid) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String getChatIds(Client client) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public void disconnect(Client client) {
        System.out.println("Already disconnected");
    }
}
