package Server;

import java.io.*;
import java.net.Socket;

public class User extends Thread {
    private String username;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public User(String username, Socket socket) throws IOException {
        this.username = username;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
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
