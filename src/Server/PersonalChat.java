package Server;

import java.io.IOException;
import java.util.ArrayList;

public class PersonalChat implements Chat {
    protected ArrayList<User> users = new ArrayList<>();
    protected ArrayList<Message> messages = new ArrayList<>();
    protected static int lastId = 1;
    protected int id;

    public PersonalChat() {
        id = lastId++;
    }
    public void subscribe(User user){
        users.add(user);
        user.addChat(id);
    }
    public void unsubscribe(User user) {
        users.remove(user);
    }

    public void sendMessage(Message msg, String param) throws IOException {
        if(param.equals("message")) {
            messages.add(msg);
            for (User user : users) {
                if (user != msg.getUser()) {
                    if (user.hasChat(id)) {
                        user.sendMessage("chat;" + id + ";" + msg.getUser().getUsername() + ";" + msg.getMessageText().split(";")[2]);
                    }
                }
            }
        }
        else if (param.equals("create")) {
            for(User user : users){
                if(user.hasChat(id)){
                    user.sendMessage(msg.getMessageText() + getName(user));
                }
            }
        }
    }
    public void getMessages(User user) throws IOException {
        if(!messages.isEmpty()) {
            for (Message msg : messages) {
                user.sendMessage("chat;" + id + ";" + msg.getUser().getUsername() + ";" + msg.getMessageText().split(";")[2]);
            }
        }
    }

    @Override
    public ArrayList<Message> getMessagesHistory() throws IOException {
        return messages;
    }

    @Override
    public ArrayList<User> getUsers() throws IOException {
        return users;
    }

    public int getId(){
        return id;
    }

    @Override
    public String getName(User user){
        for(User usr : users){
            if(!usr.getUsername().equals(user.getUsername())){
                return usr.getUsername();
            }
        }
        return null;
    }
}
