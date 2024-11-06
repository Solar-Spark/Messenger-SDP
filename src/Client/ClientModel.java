package Client;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientModel {

    private static Socket clientSocket;
    private static BufferedReader consoleReader;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static ReadMsg readMsg;
    private static ClientState state = new DisconnectedState();
    private static int currentChatId;
    private static String username;
    private static Map<Integer, String> chatList = new HashMap<>();

    public static void clientModelInit(String host, int port) {
        try {
            // Создаем сокетное соединение с сервером
            clientSocket = new Socket(host, port);
            System.out.println("Подключение к серверу на " + host + ":" + port);

            // Инициализируем потоки для чтения и записи
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            state = new ConnectedState();

            // Запускаем потоки для отправки и приема сообщений
            readMsg = new ReadMsg();
            readMsg.start();

        } catch (IOException e) {
            System.err.println("Ошибка при подключении к серверу: " + e.getMessage());
            closeResources();
        }
    }

    public static void setState(ClientState clientState) {
        state = clientState;
    }

    public static void sendCommand(String command) throws IOException {
        out.write(command + "\n");
        out.flush();
    }
    public static void getMessages() throws IOException {
        sendCommand(state.getMessages(currentChatId));
    }
    public static void registerUser(String nickName) throws IOException{
        username = nickName;
        sendCommand(state.registerUser(username));

    }
    public static void createChat(String receiverUsername) throws IOException {
        sendCommand(state.createChat(receiverUsername));
    }
    public static void createGroup(String groupChat) throws IOException {
       sendCommand(state.createGroup(groupChat));
    }
    public static void requestMessage(String message) throws IOException {
        sendCommand(state.sendMessage(currentChatId, message));
    }
    public static void requestChatName(int chatId) throws IOException {
        sendCommand(state.getChatName(chatId));
    }
    public static void  getChatIds() throws IOException {
        sendCommand(state.getChatIds());
    }
    public static void disconnect() throws IOException {
        state.disconnect();
    }
    public static void setChatId(int chatId) throws IOException {
        currentChatId = chatId;
    }
    public static ClientState getState(){
        return state;
    }
    public static void addChat(int chatId, String chatNameParam) throws IOException {
        currentChatId = chatId;
        setChatName(currentChatId, chatNameParam);
    }
    public static void receiveMessage(int chatId, String senderUserName, String message) throws IOException {
        ClientViewModel.receiveMessage(senderUserName + ": " + message + "\n");
    }
    public static void receiveGroupMessage(String username, String message) throws IOException {
        ClientViewModel.receiveMessage(username + ": " + message + "\n");
    }
    public static void setChatName(int chatId, String name) throws IOException {
        chatList.put(chatId, name);
        ClientViewModel.addChat(chatId, name);
    }
    public static String getUsername(){
        return username;
    }
    public static String getChatName(int chatId) throws IOException {
        requestChatName(chatId);
        String chatNameReturn = chatList.get(chatId);
        System.out.println("Chat Name: " + chatNameReturn);
        return chatNameReturn;
    }
    // Поток для чтения сообщений от сервера
    private static class ReadMsg extends Thread {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    ServerMessages.parse(message);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении сообщения от сервера: " + e.getMessage());
            } finally {
                closeResources();
            }
        }
    }

    // Метод для закрытия всех ресурсов
    public static void closeResources() {
        try {
            if (consoleReader != null) consoleReader.close();
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            System.out.println("Клиент завершил работу.");
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии ресурсов: " + e.getMessage());
        }
    }
}
