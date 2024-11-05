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
    private static ClientState state;
    private static int currentChatId;
    private static Map<Integer, String> chatList = new HashMap<>();
    private static String chatName;

    public void clientModelInit(String host, int port) {
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
    public static String sendCommand(String command) throws IOException {
        out.write(command + "\n");
        out.flush();
        return in.readLine();
    }
    public static void getMessages() throws IOException {
        sendCommand(state.getMessages(currentChatId));
    }
    public static String registerUser(String username) throws IOException{
        return sendCommand(state.registerUser(username));
    }
    public static String createChat(String receiverUsername) throws IOException {
        return sendCommand(state.createChat(receiverUsername));
    }
    public static String createGroup(String groupChat) throws IOException {
        return sendCommand(state.createGroup(groupChat));
    }
    public static String requestMessage(String message) throws IOException {
        return sendCommand(state.sendMessage(currentChatId, message));
    }
    public static String requestChatName(int chatId) throws IOException {
        return sendCommand(state.getChatName(chatId));
    }
    public static String getChatIds() throws IOException {
        return sendCommand(state.getChatIds());
    }
    public static void disconnect() throws IOException {
        state.disconnect();
    }
    public static void setChatId(int chatId) throws IOException {
        currentChatId = chatId;
    }
    public static void addChat(int chatId) throws IOException {
        chatList.put(chatId, chatName);
    }
    public static void receiveMessage(int chatId, String message) throws IOException {
        ClientViewModel.receiveMessage(chatList.get(chatId) + ": " + message + "\n");
    }
    public static void receiveGroupMessage(String username, String message) throws IOException {
        ClientViewModel.receiveMessage(username + ": " + message + "\n");
    }
    public static void setChatName(String name) throws IOException {
        chatName = name;
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
