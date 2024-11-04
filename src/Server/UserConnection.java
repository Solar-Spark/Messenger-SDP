package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class UserConnection{
    private BufferedReader in;
    private BufferedWriter out;
    private User user;
    private Socket socket;
    ReadMsg readMsg;

    public UserConnection(Socket socket, User user) throws IOException {
        this.user = user;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        user.setUserCon(this);
        readMsg = new ReadMsg();
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
                sendMessage("status;connected");
                break;
            }
            else{
                if(userFromList.isConnected()){
                    sendMessage("status;busy");
                    break;
                }
                else{
                    userFromList.setUserCon(this);
                    userFromList.setConnected(true);
                    sendMessage("status;connected");
                    System.out.println("User " + username + " is logged in");
                    ArrayList<Integer> chatIds = userFromList.getChatIds();
                    if(!chatIds.isEmpty()){
                        for(int chatId : chatIds){
                            ChatsController.getChat(chatId).getMessages(userFromList);
                        }
                    }
                    break;
                }
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
    public void close() throws IOException {
        readMsg.interrupt();
        user.setConnected(false);
        in.close();
        out.close();
        socket.close();
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
            }
            catch (IOException e) {
                user.setConnected(false);
                System.out.println(e.getMessage());
                String username = user.getUsername();
                System.out.println((username == null ? "User" : user.getUsername()) + " has logged out");
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
                interrupt();
            } catch (IOException e) {
                System.err.println("Out disconnected");
            }
        }
    }
}