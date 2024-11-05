package Client;

public class ConnectedState implements ClientState{
    @Override
    public String registerUser(String username) {
        String command = "reg;" + username;
        return command;
    }
    @Override
    public String createChat(String receiverUsername) {
        String command = "createChat;" + receiverUsername;
        return command;
    }
    @Override
    public String createGroup(String groupChat) {
        String[] groupData = groupChat.split(",");
        String command = "createGroup;" + groupData[0];
        for (int i = 1; i < groupData.length; i++) {
            command += ";" + groupData[i];
        }
        return command;
    }
    @Override
    public String sendMessage(int chatid, String message) {
        String command = "chat;" + chatid + ";" + message;
        return command;
    }
    @Override
    public String getChatName(int chatid) {
        return "getChatName;" + chatid;
    }
    @Override
    public String getChatIds() {
        return "getChatIds";
    }
    @Override
    public void disconnect() {
        ClientModel.setState(new DisconnectedState());
        ClientModel.closeResources();
    }
}
