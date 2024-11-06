package Server;

public class MessageFactory implements MessageFactoryInterface{
    @Override
    public Message createMessage(User user, String message) {
        return new Message(user, message);
    }
}
