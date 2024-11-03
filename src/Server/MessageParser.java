package Server;

public class MessageParser {
    public static String parseMessage(String message, UserConnection userCon) {
        String[] parameters = message.split(";");
        if (parameters.length == 2) {
            switch (parameters[0]) {
                case "reg":
                    return parameters[1];
            }
        }
        return null;
    }
}
