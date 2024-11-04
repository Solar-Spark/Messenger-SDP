package Server;

import java.util.HashMap;
import java.util.Map;

public class ChatsController {
    private static Map<Integer, Chat> chats = new HashMap<Integer, Chat>();
    private static Map<Integer, Group> groups = new HashMap<Integer, Group>();

    public static int create(Chat chat) {
        chats.put(chat.getId(), chat);
        return chat.getId();
    }
    public static int create(Group group) {
        chats.put(group.getId(), group);
        return group.getId();
    }
    public static Group getGroup(int id) {
        return groups.get(id);
    }
    public static Chat getChat(int id) {
        return chats.get(id);
    }
}
