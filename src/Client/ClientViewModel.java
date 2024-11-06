package Client;

import java.io.IOException;

public class ClientViewModel {
    public static void registerUser(String username) throws IOException {
        ClientProxy.registerUser(username);
    }

    public static void createChat(String receiverUsername) throws IOException {
        if (ClientApp.getChatMap().get(receiverUsername) == null) {
            ClientProxy.createChat(receiverUsername);
        }
    }

    public static void createGroup(String groupChat) throws IOException {
        if (ClientApp.getChatMap().get(groupChat) == null) {
            ClientProxy.createGroup(groupChat);
        }
    }

    public static void setChatId(int chatId) throws IOException {
        ClientProxy.setChatId(chatId);
        ClientApp.getChatArea().clear();
        ClientProxy.getMessages();
    }

    public static void sendMessage(String message) throws IOException {
        ClientApp.getChatArea().appendText(ClientProxy.getUsername() + ": " + message + "\n");
        ClientProxy.requestMessage(message);
    }

    public static void receiveMessage(String message) {
        ClientApp.getChatArea().appendText(message);
    }

    public static void initClientModel(String host) throws IOException {
        ClientProxy.clientModelInit(host, 8080);
    }

    public static void addChat(int chatId, String chatName) {
        ClientApp.getChatMap().put(chatName, chatId);
        ClientApp.getChatList().getItems().add(chatName);
    }

    public static void disconnect() throws IOException {
        ClientProxy.disconnect();
    }
}
