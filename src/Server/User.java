package Server;

import java.io.*;
import java.util.ArrayList;

public class User{
    private String username;
    private UserConnection userCon;
    private boolean connected;
    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<Integer> chatIds = new ArrayList<>();

    public void addChat(int id){
        chatIds.add(id);
    }

    public boolean isChat(int id){
        return chatIds.contains(id);
    }
    public ArrayList<Integer> getChatIds(){
        if(chatIds.isEmpty()){
            return null;
        }
        return chatIds;
    }
    public void setUserCon(UserConnection userCon){
        this.userCon = userCon;
    }
    public UserConnection getUserCon(){
        return userCon;
    }
    public void sendMessage(String msg) throws IOException{
        userCon.sendMessage(msg);
    }
    public String receiveMessage() throws IOException{
        return messages.getLast();
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setConnected(boolean connected){
        this.connected = connected;
    }
    public boolean isConnected(){
        return connected;
    }
    public void addMessage(String msg){
        messages.add(msg);
        System.out.println("Messages + " + messages.getLast());
    }
}
