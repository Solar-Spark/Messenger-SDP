package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UserConnectionController extends Thread {
    private static UserConnectionController instance = null;
    private static ServerSocket server;

    private UserConnectionController(ServerSocket server) {
        this.server = server;
        start();
    }
    public static UserConnectionController getInstance(ServerSocket server) {
        if (instance == null) {
            instance = new UserConnectionController(server);
        }
        return instance;
    }
    public void run() {
        try {
            monitorConnections();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void monitorConnections() throws IOException {
        try {
            while (true) {
                Socket socket = server.accept();
                new UserConnection(socket, new User());
                System.out.println("New connection");
            }
        }
        finally {
            server.close();
        }
    }
}
