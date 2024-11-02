package Server;

import java.util.ArrayList;

public class UsersController {
    private static ArrayList<User> users;

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }
}
