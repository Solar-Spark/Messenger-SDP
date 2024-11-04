package Server;

import java.util.HashMap;
import java.util.Map;

public class ChatsController {
    private static Map<Integer, Chat> chats = new HashMap<Integer, Chat>();

    public static int create(PersonalChat personalChat) {
        chats.put(personalChat.getId(), personalChat);
        return personalChat.getId();
    }
    public static int create(GroupDecorator group) {
        chats.put(group.getId(), group);
        return group.getId();
    }
    public static Chat getChat(int id) {
        return chats.get(id);
    }
}
