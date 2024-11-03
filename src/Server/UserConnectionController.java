package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UserConnectionController extends Thread {
    private static ServerSocket server;
    private static Socket socket;

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
                socket = server.accept();
                System.out.println("New connection");
            }
        }
        finally {
            server.close();
        }
    }

    public static void authentication(String username) throws IOException {
        try {
            User userFromList = UserController.getUser(username);
            if(userFromList != null){
                userFromList.setUserCon(new UserConnection(socket, userFromList));
                userFromList.setConnected(true);
                System.out.println(userFromList.getUsername() + " logged in");
                userFromList.sendMessage(userFromList.getUsername() + " logged in");
            }
            else{
                User tempUser = new User();
                tempUser.setUsername(username);
                tempUser.setUserCon(new UserConnection(socket, tempUser));
                tempUser.setConnected(true);
                UserController.addUser(tempUser);
                System.out.println(userFromList.getUsername() + " registered");
                tempUser.sendMessage(tempUser.getUsername() + " registered");
            }
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
