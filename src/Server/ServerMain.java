package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        final int PORT = 8080;

        ServerSocket server = null;
        server = new ServerSocket(PORT);

        ConnectionController connectionController = new ConnectionController(server);
    }
}
