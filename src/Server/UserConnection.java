package Server;

import java.io.*;
import java.net.Socket;

public class UserConnection extends Thread {
    private BufferedReader in;
    private BufferedWriter out;
    private User user;

    public UserConnection(Socket socket, User user) throws IOException {
        this.user = user;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ReadMsg readMsg = new ReadMsg();
        readMsg.start();
        start();
    }

    @Override
    public void run() {

    }
    private class ReadMsg extends Thread {
        @Override
        public void run() {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    MessageListener.parse(msg);
                }
            } catch (IOException e) {
                user.setConnected(false);
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    public void sendMessage(String msg) throws IOException {
        WriteMsg writeMsg = new WriteMsg(msg);
        writeMsg.start();
    }
    private class WriteMsg extends Thread {
        String msg;
        public WriteMsg(String msg){
            this.msg = msg;
        }
        @Override
        public void run() {
            try {
                out.write(msg + "\n");
                out.flush();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());

            }
        }
    }
}