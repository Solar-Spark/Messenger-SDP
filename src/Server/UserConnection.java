package Server;

import java.io.*;
import java.net.Socket;

public class UserConnection{
    private BufferedReader in;
    private BufferedWriter out;
    private User user;
    private Socket socket;

    public UserConnection(Socket socket, User user) throws IOException {
        this.user = user;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        user.setUserCon(this);
        ReadMsg readMsg = new ReadMsg();
        readMsg.start();
    }

    public void authenticate(String username) throws IOException {
        user.setUserCon(this);
        while (true) {
            User userFromList = UserController.getUser(username);
            if(userFromList == null){
                user.setUsername(username);
                user.setConnected(true);
                UserController.addUser(user);
                System.out.println("User " + username + " is registered");
                break;
            }
            else{
                userFromList.setUserCon(this);
                userFromList.setConnected(true);
                System.out.println("User " + username + " is logged in");
                for(int chatId : userFromList.getChatIds()){
                    ChatsController.getChat(chatId).getMessages(userFromList);
                }
                break;
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            try {
                Message msg;
                while ((msg = new Message(user, in.readLine())).getMessageText() != null) {
                    MessageListener.parse(msg);
                    //System.out.println(user.getUsername() + " printed " + msg.getMessageText());
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