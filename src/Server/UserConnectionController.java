package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UserConnectionController extends Thread {
    private static ServerSocket server;

    public UserConnectionController(ServerSocket server) {
        this.server = server;
        start();
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
