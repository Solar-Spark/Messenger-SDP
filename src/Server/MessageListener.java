package Server;

import java.io.IOException;

public class MessageListener {

        public static String parse(Message msg) throws IOException {
            String[] params = msg.getMessageText().split(";");
            if(params[0].equals("reg")){
                setUsername(msg.getUser(), params[1]);
                msg.getUser().getUserCon().authenticate(params[1]);
                return params[1];
            }
            else if(params[0].equals("createChat")){
                int id = ChatsController.create(new Chat());
                Chat chat = ChatsController.getChat(id);
                chat.subscribe(msg.getUser());
                chat.subscribe(UserController.getUser(params[1]));
                msg.getUser().sendMessage("chatId;" + id);
                UserController.getUser(params[1]).sendMessage("chatId;" + id);
            }
            /*else if(params[0].equals("createGroup")){
                int id = ChatsController.create(new Group(params[1]));
                Group group = ChatsController.getChat(id);
                chat.subscribe(msg.getUser());
                chat.subscribe(UserController.getUser(params[1]));
                msg.getUser().sendMessage("chatId;" + id);
                UserController.getUser(params[1]).sendMessage("chatId;" + id);
            }*/
            else if (params[0].equals("chat")){
                int chatId = Integer.parseInt(params[1]);
                ChatsController.getChat(chatId).sendMessage(msg);
            }
            return null;
        }
        public static void setUsername(User user, String username){
            user.setUsername(username);
        }
}
