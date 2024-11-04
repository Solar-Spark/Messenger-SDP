package Server;

import java.io.IOException;

public class Group extends PersonalChat {
    private String groupName;

    public Group(String groupName) {
        super();
        this.groupName = groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public void sendMessage(Message msg) throws IOException {
        messages.add(msg);
        for(User user : users){
            if(user != msg.getUser()){
                if(user.isChat(id)){
                    user.sendMessage("group;" + id + ";" + msg.getUser().getUsername() + ";" + msg.getMessageText().split(";")[2]);
                }
            }
        }
    }
}
