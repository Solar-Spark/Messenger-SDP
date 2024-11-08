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
                int id = ChatsController.create(new PersonalChat());
                Chat personalChat = ChatsController.getChat(id);
                personalChat.subscribe(msg.getUser());
                try{
                    MessageFactoryInterface messageFactory = new MessageFactory();
                    personalChat.subscribe(UserController.getUser(params[1]));
                    personalChat.sendMessage(messageFactory.createMessage(msg.getUser(),"chatId;" + id + ";"), "create");
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                System.out.println("Chat created, User: " + params[1]);
            }
            else if(params[0].equals("createGroup")){
                int id = ChatsController.create(new GroupDecorator(new PersonalChat(), params[1]));
                Chat group = ChatsController.getChat(id);
                for(int i = 2; i < params.length; i++) {
                    group.subscribe(UserController.getUser(params[i]));
                }
                MessageFactoryInterface messageFactory = new MessageFactory();
                group.sendMessage(messageFactory.createMessage(msg.getUser(),"chatId;" + id + ";" + group.getName(msg.getUser())), "create");
            }
            else if (params[0].equals("chat")){
                int chatId = Integer.parseInt(params[1]);
                ChatsController.getChat(chatId).sendMessage(msg, "message");
            }
            else if (params[0].equals("getChatName")){
                int chatId = Integer.parseInt(params[1]);
                User user = msg.getUser();
                user.sendMessage("chatName;" + chatId + ";" + ChatsController.getChat(chatId).getName(user));
            }
            else if (params[0].equals("getChatIds")){
                User user = msg.getUser();
                String message = "chatIds";
                for(int id : user.getChatIds()){
                    message +=  ";" + id;
                }
                user.sendMessage(message);
            }
            else if (params[0].equals("disconnect")){
                msg.getUser().getUserCon().close();
            }
            else if (params[0].equals("getMessages")){
                int chatId = Integer.parseInt(params[1]);
                ChatsController.getChat(chatId).getMessages(msg.getUser());
            }
            return null;
        }
        public static void setUsername(User user, String username){
            user.setUsername(username);
        }
}
