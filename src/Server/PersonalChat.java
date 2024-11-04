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
    public void sendMessage(Message msg) throws IOException {
        messages.add(msg);
        for(User user : users){
            if(user != msg.getUser()){
                if(user.isChat(id)){
                    user.sendMessage("chat;" + id + ";" + msg.getMessageText().split(";")[2]);
                }
            }
        }
    }
    public void getMessages(User user) throws IOException {
        if(!messages.isEmpty()) {
            for (Message msg : messages) {
                user.sendMessage("chat;" + id + ";" + msg.getMessageText().split(";")[2]);
            }
        }
    }
    public int getId(){
        return id;
    }
}
