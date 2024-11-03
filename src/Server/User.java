package Server;

import java.io.*;
import java.net.Socket;

public class User{
    private String username;
    private Socket socket;

    public User(Socket socket) throws IOException {
        this.socket = socket;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
