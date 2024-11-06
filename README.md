# Messenger 

This project is messenger which is designed to establish communication via text messages between 2 or more users 

## Featrues 

- Choosing your own nickname by typing it in "Nickname Selecter" window 
- Create chats by clicking "Create chat" button in main widonw and typing user's nickname in "Create chats" window
- Create group chats by clikcing "Create group chat" button in main window and typing user's nickname as well as group chat's name in "Create group chat" window 
- Move between different chats and group chats by cliking to each one individually on main window 
- Send messages to each group chat and chat 

## Establishing connection 
To establistion connection with a server user has to fill out IP address of that particular server.
Program, firslty, will ask user for an IP address in by opening "IP address validation" window 


## Methods 
```
start(Stage primaryStage)
```
The start method initializes the JavaFX UI for IP address input, validates its format, and provides real-time error feedback. If valid, it creates a client model with the IP and opens a window for nickname input. If invalid, it displays an error message.

```
handle(WindowEvent event)
```
This method ensures that when the window is closed, the client disconnects from any network resources and the application exits cleanly.

```
isValidIPAddress(String ip) 
```
This method checks whether a given IP address is valid by attempting to resolve it using the InetAddress.getByName() method. If the IP address can be resolved, it returns true; otherwise, it returns false.

```
isValidIpFormat(String ip)
```
This method validates the format of an IP address by first checking if it matches the standard IPv4 pattern (e.g., xxx.xxx.xxx.xxx), and then verifying that each segment of the address is within the valid range of 0-255.

```
openNicknameWindow()
```
This method creates and displays a new window where the user can input their nickname. It validates the input to ensure the nickname is not empty and attempts to register the user. Upon successful registration, it opens the chat interface window. If the input is invalid, it shows an error alert.

```
openChatWindow(String nickname)
```
This method creates and displays a new window for the chat interface. It initializes a ListView for displaying chat messages, sets the appropriate cell formatting for each message, and customizes the appearance of the list items.

```
openCreateChatWindow()
```
This method creates and displays a new window for creating a chat. It allows the user to input a nickname, validate the input, and then call the ClientViewModel.createChat(nickname) method to create the chat. If the input is valid, it closes the window; otherwise, it shows a warning.

```
openCreateGroupChatWindow()
```
This method creates and displays a window for creating a new group chat. It allows the user to input the group chat name and members, validates the input, and then calls ClientViewModel.createGroup(groupChatDetails) to create the group chat. If successful, it closes the window; otherwise, it shows a warning

```
create(PersonalChat personalChat)
```
This method creates a new personal chat by adding it to the chats map.

```
create(GroupDecorator group)
```
This method creates a new group chat by adding it to the chats map.

```
getChat(int id)
```
This method retrieves a chat by its ID from the chats map.

```
subscribe(User user)
```
This method subscribes a user to the group chat by delegating the action to the wrapped Chat object.

```
unsubscribe(User user)
```
This method unsubscribes a user from the group chat by delegating the action to the wrapped Chat object.

```
getMessages(User user)
```
This method retrieves the chat messages for a specific user. It formats and sends each message in the chat's history to the user, prefixed with "group;", group ID, username, and message text.

```
getMessagesHistory()
```
This method retrieves the message history of the wrapped chat.

```
getUsers()
```
This method retrieves the list of users currently subscribed to the chat.

```
getId()
```
This method retrieves the ID of the group chat.

```
setGroupName(String groupName)
```
This method sets the name of the group chat.

```
getGroupName()
```
This method retrieves the name of the group chat.

```
sendMessage(Message msg)
```
This method sends a message to all users in the group chat (except the sender), by delegating the action to the wrapped Chat object.

```
sendCreateMessage(String msg)
```
This method sends a "create" message to all users in the group chat

```
getName(User user)
```
This method retrieves the name of the group chat for a specific user.
