package Server;

import java.io.*;

public class UserConnection{
    private BufferedReader in;
    private BufferedWriter out;
    private User user;

    public UserConnection(User user) throws IOException {
        this.user = user;
        in = new BufferedReader(new InputStreamReader(user.getSocket().getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(user.getSocket().getOutputStream()));
        String msg;
        try {
            msg = in.readLine();
            if(msg.split(";")[0].equals("reg")){
                user.setUsername(msg.split(";")[1]);
            }
            System.out.println(user.getUsername());
        }
        catch(IOException e){
            throw new IOException(e);
        }
    }

    public void sendMessage(String msg) throws IOException {
        out.write(msg);
    }

    public User getUser() {
        return user;
    }
}