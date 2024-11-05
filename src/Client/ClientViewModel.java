package Client;

import java.io.IOException;

public class ClientViewModel {
    //register user's nickname
    public static void registerUser(String username) throws IOException {
        ClientModel.registerUser(username);
    }
    //creating chat with other user using their nickname
    public static void createChat(String receiverUsername) throws IOException {
        ClientModel.createChat(receiverUsername);
    }
    //creating group chat with certain number of members using their nicknames and groupName in format like "groupName;member1;member2;..."
    public static void createGroup(String groupChat) throws IOException {
        ClientModel.createGroup(groupChat);
    }
    //to select current chat's id that were selected
    public static void setChatId(int chatId) throws IOException {
        ClientModel.setChatId(chatId);
    }
    //sendimg message from user's input to the server
    public static void sendMessage(String message) throws IOException {
        ClientModel.requestMessage(message);
    }
    //receives message from server into chat area(into the chat on group)
    public static void receiveMessage(String message) throws IOException {
        ClientApp.getChatArea().appendText(message);
    }
    public static void initClientModel(String host) throws IOException {
        ClientModel.clientModelInit(host, 8080);
    }
    public static void addChat(int chatId, String chatName) throws IOException {
        ClientApp.getChatMap().put(chatName, chatId);
        ClientApp.getChatList().getItems().addAll(chatName);
    }
}
