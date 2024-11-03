package Server;

import java.io.IOException;

public class MessageListener {
        public static void parse(String msg) throws IOException {
            String[] params = msg.split(";");
            if(params[0].equals("reg")){
                System.out.println(params[1]);
                UserConnectionController.authentication(params[1]);
            }
        }
}
