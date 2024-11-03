package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionController extends Thread {
    ArrayList<UserConnection> userConnections = new ArrayList<>();
    private static ServerSocket server;

    public ConnectionController(ServerSocket server) {
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
                try {
                    System.out.println("New User");
                    userConnections.add(new UserConnection(new User(socket)));
                } catch (IOException e) {
                    socket.close();
                }
            }
        }
        finally {
            server.close();
        }
    }
    public UserConnection getUserCon(String username){
        for (UserConnection userCon : userConnections) {
            if(userCon.getUser().getUsername().equals(username)){
                return userCon;
            }
        }
        return null;
    }
}
