package Client;

import java.awt.*;
import java.io.IOException;

public class ClientViewModel {
    public static void registerUser(String username) throws IOException {
        ClientModel.registerUser(username);
    }
    public static void createChat(String receiverUsername) throws IOException {
        ClientModel.createChat(receiverUsername);
    }
    public static void createGroup(String groupChat) throws IOException {
        ClientModel.createGroup(groupChat);
    }
    public static void setChatId(int chatId) throws IOException {
        ClientModel.setChatId(chatId);
    }
    public static void sendMessage(String message) throws IOException {
        ClientModel.sendMessage(message);
    }
    public static String messageHistory()  throws IOException {

    }
    public static void receiveMessage(String username, String message) throws IOException {

    }
}
