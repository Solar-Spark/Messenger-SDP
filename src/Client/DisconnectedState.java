package Client;

public class DisconnectedState implements ClientState {
    @Override
    public String registerUser(String username) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String createChat(String receiverUsername) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String createGroup(String groupChat) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String sendMessage(int chatid, String message) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String getChatName(int chatid) {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public String getChatIds() {
        System.out.println("User is not connected");
        return "";
    }
    @Override
    public void disconnect() {
        System.out.println("Already disconnected");
    }

    @Override
    public String getMessages(int chatId) {
        System.out.println("User is not connected");
        return "";
    }
}
