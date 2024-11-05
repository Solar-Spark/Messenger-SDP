package Client;

public class ConnectedState implements ClientState{
    @Override
    public String registerUser(String username) {
        return "reg;" + username;
    }
    @Override
    public String createChat(String receiverUsername) {
        return "createChat;" + receiverUsername;
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
        return "chat;" + chatid + ";" + message;
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
    @Override
    public void receiveMessage(int chatid, String message) {
        ClientViewModel.receiveMessage();
    }
}
