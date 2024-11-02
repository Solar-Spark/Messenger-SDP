package Server;

import java.util.ArrayList;

public class ChatsController {
    ArrayList<Chat> chats;
    ArrayList<Group> groups;

    public void createChat(Chat chat) {
        chats.add(chat);
    }

    public void createGroup(Group group) {
        groups.add(group);
    }

}
