package Client;

import java.io.IOException;

public class ServerMessages {
    public static void parse(String message) throws IOException{
        String[] params = message.split(";");
        int chatId;
        switch (params[0]) {
            case "chatId":
                chatId = Integer.parseInt(params[1]);
                ClientModel.addChat(chatId);
                break;
            case "chatName":
                ClientModel.setChatName(params[1]);
                break;
            case "chat":
                chatId = Integer.parseInt(params[1]);
                ClientModel.receiveMessage(chatId, params[2]);
                break;
            case "group":
                chatId = Integer.parseInt(params[1]);
                ClientModel.receiveGroupMessage(params[2], params[3]);
                break;
            default:
                break;
        }
    }
}
