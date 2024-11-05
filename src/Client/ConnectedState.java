package Client;

public class ConnectedState implements ClientState{
    @Override
    public String registerUser(Client client, String username) {
        String command = "reg;" + username;
        return command;
    }
    @Override
    public String createChat(Client client, String receiverUsername) {
        String command = "createChat;" + receiverUsername;
        return command;
    }
    @Override
    public String createGroup(Client client, String groupName, String... members) {
        String command = "createGroup;" + groupName + ";" + String.join(";", members);
        return command;
    }
    @Override
    public String sendMessage(Client client, int chatid, String message) {
        String command = "chat;" + chatid + ";" + message;
        return command;
    }
    @Override
    public String getChatName(Client client, int chatid) {
        String command = "getChatName;" + chatid;
        return command;
    }
    @Override
    public String getChatIds(Client client) {
        String command = "getChatIds;";
        return command;
    }
    @Override
    public void disconnect(Client client) {
        client.closeResources();
    }
}
