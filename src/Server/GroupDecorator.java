package Server;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class GroupDecorator implements Chat{
    private String groupName;
    private Chat chat;
    public GroupDecorator(Chat chat, String groupName) {
        this.chat = chat;
        this.groupName = groupName;
    }
    @Override
    public void subscribe(User user) throws IOException {
        chat.subscribe(user);
    }

    @Override
    public void unsubscribe(User user) throws IOException {
        chat.unsubscribe(user);
    }

    @Override
    public void getMessages(User user) throws IOException {
        chat.getMessages(user);
    }

    @Override
    public ArrayList<Message> getMessagesHistory() throws IOException {
        return chat.getMessagesHistory();
    }

    @Override
    public ArrayList<User> getUsers() throws IOException {
        return chat.getUsers();
    }

    @Override
    public int getId() {
        return chat.getId();
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public void sendMessage(Message msg) throws IOException {
        chat.getMessagesHistory().add(msg);
        for(User user : chat.getUsers()){
            if(user != msg.getUser()){
                if(user.isChat(chat.getId())){
                    user.sendMessage("group;" + chat.getId() + ";" + msg.getUser().getUsername() + ";" + msg.getMessageText().split(";")[2]);
                }
            }
        }
    }
}