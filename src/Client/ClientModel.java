package Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientModel {

    private static Socket clientSocket;
    private static BufferedReader consoleReader;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static ReadMsg readMsg;
    private static WriteMsg writeMsg;
    private static ClientState state;
    private static int currentChatId;
    private static ArrayList<Integer> chatIds = new ArrayList<>();

    public ClientModel(String host, int port) {
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
            writeMsg = new WriteMsg();
            writeMsg.start();

        } catch (IOException e) {
            System.err.println("Ошибка при подключении к серверу: " + e.getMessage());
            closeResources();
        }
    }

    public static void setState(ClientState clientState) {
        state = clientState;
    }

    //Methods which are sending commands to the server
    private static String sendCommand(String command) throws IOException {
        out.write(command + "\n");
        out.flush();
        return in.readLine();
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
    public static String sendMessage(String message) throws IOException {
        return sendCommand(state.sendMessage(currentChatId, message));
    }
    public static String getChatName(int chatid) throws IOException {
        return sendCommand(state.getChatName(chatid));
    }
    public static String getChatIds() throws IOException {
        return sendCommand(state.getChatIds());
    }
    public static void disconnect() throws IOException {
        state.disconnect();
    }
    public static void setChatId(int chatid) throws IOException {
        currentChatId = chatid;
    }
    public static void receiveMessage(int chatid, String message) throws IOException {

    }
//    public static void main(String[] args) {
//        // Запускаем клиента на указанном хосте и порту
//        new Client("localhost", 8080);
//    }

    // Поток для чтения сообщений от сервера
    private class ReadMsg extends Thread {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("stop")) {
                        System.out.println("Сервер завершил соединение.");
                        break;
                    }
                    System.out.println("Сообщение от сервера: " + message);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении сообщения от сервера: " + e.getMessage());
            } finally {
                closeResources();
            }
        }
    }

    // Поток для отправки сообщений на сервер
    private class WriteMsg extends Thread {
        @Override
        public void run() {
            try {
                String userInput;
                while ((userInput = consoleReader.readLine()) != null) {
                    if (userInput.equalsIgnoreCase("stop")) {
                        out.write("stop\n");
                        out.flush();
                        break;
                    }
                    out.write(userInput + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
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
