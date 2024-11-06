package Client;

import java.io.IOException;

public class ClientProxy {
    private static ClientModel clientModel;
    private static boolean isInitialized = false;

    public static void clientModelInit(String host, int port) throws IOException {
        if (!isInitialized) {
            clientModel = new ClientModel();
            clientModel.clientModelInit(host, port);
            isInitialized = true;
        }
    }

    public static void registerUser(String username) throws IOException {
        clientModel.registerUser(username);
    }

    public static void createChat(String receiverUsername) throws IOException {
        clientModel.createChat(receiverUsername);
    }

    public static void createGroup(String groupChat) throws IOException {
        clientModel.createGroup(groupChat);
    }

    public static void setChatId(int chatId) throws IOException {
        clientModel.setChatId(chatId);
    }

    public static void requestMessage(String message) throws IOException {
        clientModel.requestMessage(message);
    }

    public static String getUsername() {
        return clientModel.getUsername();
    }

    public static void getMessages() throws IOException {
        clientModel.getMessages();
    }

    public static void disconnect() throws IOException {
        clientModel.disconnect();
        isInitialized = false;
    }
}
