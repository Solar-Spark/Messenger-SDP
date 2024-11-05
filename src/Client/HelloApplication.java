package Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the main UI components for IP input
        TextField ipTextField = new TextField();
        ipTextField.setPromptText("Enter IP address (e.g., 192.168.1.1)");

        // Bind the TextField's preferred width to 60% of the window width
        ipTextField.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.6));

        Button validateButton = new Button("Validate IP");
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Layout for the main window
        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);

        // Centering input area using HBox
        HBox inputContainer = new HBox();
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.getChildren().add(ipTextField);

        mainLayout.getChildren().addAll(inputContainer, validateButton, errorLabel);

        // Event handler for validating IP address (button always clickable)
        validateButton.setOnAction(event -> {
            String ip = ipTextField.getText().trim(); // Get the text and remove leading/trailing spaces
            if (isValidIpFormat(ip)) {
                if (isValidIPAddress(ip)) {
                    // IP is valid, proceed to next window
                    System.out.println("IP Address: " + ip);
                    primaryStage.close();
                    openNicknameWindow();
                } else {
                    // Invalid IP address according to InetAddress
                    errorLabel.setText("Invalid IP address format!");
                }
            } else {
                // If the format is invalid, show the appropriate error message
                errorLabel.setText("Invalid IP address format!");
            }
        });

        // Input handler to validate IP as user types (real-time validation)
        ipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                errorLabel.setText("IP address cannot be empty!");
            } else if (isValidIpFormat(newValue)) {
                errorLabel.setText("");  // Clear error if format is valid
            } else {
                errorLabel.setText("Invalid IP address format!");
            }
        });

        // Setup the primary stage with consistent size (400x200)
        Scene mainScene = new Scene(mainLayout, 400, 200); // Window size 400x200
        primaryStage.setTitle("IP Address Validator");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        // Do not set focus to the IP TextField
        // (So that the mouse must be clicked to focus on the input)
    }

    // Method to check if the IP address is valid (using InetAddress)
    private boolean isValidIPAddress(String ip) {
        try {
            InetAddress.getByName(ip); // Will throw UnknownHostException if the IP is invalid
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    // Method to check if the IP address is in valid format (X.X.X.X)
    private boolean isValidIpFormat(String ip) {
        // Regex to match the format: four numbers (0-255), separated by periods.
        Pattern pattern = Pattern.compile("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
        Matcher matcher = pattern.matcher(ip);

        if (!matcher.matches()) {
            return false; // Doesn't match the pattern
        }

        // Split by period and check if each number is between 0 and 255
        String[] parts = ip.split("\\.");
        for (String part : parts) {
            int num;
            try {
                num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                    return false; // If any part is outside the valid range
                }
            } catch (NumberFormatException e) {
                return false; // If any part is not a valid integer
            }
        }

        return true; // All checks passed
    }

    // Method to create a new window for nickname input
    private void openNicknameWindow() {
        Stage nicknameStage = new Stage();
        nicknameStage.setTitle("Enter Nickname");

        // UI components for nickname input
        TextField nicknameTextField = new TextField();
        nicknameTextField.setPromptText("Enter your nickname (e.g., JohnDoe)");

        // Bind the TextField's preferred width to 60% of the window width
        nicknameTextField.prefWidthProperty().bind(nicknameStage.widthProperty().multiply(0.6));

        Button submitButton = new Button("Submit");

        // Layout for the nickname window
        VBox nicknameLayout = new VBox(10);
        nicknameLayout.setAlignment(Pos.CENTER);

        // Centering input area using HBox
        HBox nicknameInputContainer = new HBox();
        nicknameInputContainer.setAlignment(Pos.CENTER);
        nicknameInputContainer.getChildren().add(nicknameTextField);

        nicknameLayout.getChildren().addAll(nicknameInputContainer, submitButton);

        // Event handler for submitting nickname
        submitButton.setOnAction(event -> {
            String nickname = nicknameTextField.getText();
            if (!nickname.trim().isEmpty()) {
                System.out.println("User's nickname: " + nickname);
                nicknameStage.close(); // Close nickname window
                // Open the chat interface
                openChatWindow(nickname);
            } else {
                // If nickname is empty, show error message
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nickname cannot be empty!", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Setup the nickname stage with consistent size (400x200)
        Scene nicknameScene = new Scene(nicknameLayout, 400, 200); // Window size 400x200
        nicknameStage.setScene(nicknameScene);
        nicknameStage.show();

        // Set focus to the Nickname TextField automatically when the window opens
        nicknameTextField.requestFocus();
    }

    // Method to create a new window for the chat interface
    private void openChatWindow(String nickname) {
        Stage chatStage = new Stage();
        chatStage.setTitle("Chat - " + nickname);

        // Create the chat list on the left (fixed width)
        ListView<String> chatList = new ListView<>();
        chatList.setPrefWidth(250);
        chatList.setMaxHeight(300);

        // Set cell factory to adjust the height and font size of each chat item
        chatList.setCellFactory(param -> {
            ListCell<String> cell = new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setFont(Font.font("Arial", 14)); // Set font size for each item
                        setStyle(" -fx-padding: 10px;"); // Style each item
                    } else {
                        setText(null);
                    }
                }
            };
            return cell;
        });

        chatList.getItems().addAll("Chat 1", "Chat 2", "Chat 3", "Chat 4", "Chat 5", "Chat 6");

        // Create the chat area on the right (taking more space)
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false); // User shouldn't edit chat messages
        chatArea.setPrefHeight(300);

        // Create message input area at the bottom of the chat area
        TextArea messageInput = new TextArea();
        messageInput.setPromptText("Type your message...");
        messageInput.setWrapText(true);  // Enable multi-line input
        messageInput.setPrefHeight(80); // Limit the height of the message input area
        messageInput.prefWidthProperty().bind(chatStage.widthProperty().multiply(0.6)); // Adjust width to fit

        // Apply padding and spacing for the message input area
        messageInput.setPadding(new Insets(1));
        messageInput.setStyle("-fx-border-color: #ccc; -fx-border-width: 1;");

        Button sendButton = new Button("Send");

        // Create 'Create Chat' and 'Create Group Chat' buttons
        Button createChatButton = new Button("Create Chat");
        createChatButton.setPrefWidth(160);

        Button createGroupChatButton = new Button("Create Group Chat");
        createGroupChatButton.setPrefWidth(160);


       // Layout for the buttons next to the message input
        HBox chatButtonLayout = new HBox(10, createChatButton);
        HBox groupChatButtonLayout = new HBox(10, createGroupChatButton);
        chatButtonLayout.setAlignment(Pos.CENTER);
        groupChatButtonLayout.setAlignment(Pos.CENTER);

        VBox leftLayout = new VBox(10);
        leftLayout.setSpacing(10);
        leftLayout.getChildren().addAll(chatList, chatButtonLayout, groupChatButtonLayout);


        // Layout for the chat interface (setting no space between the left and right side)
        VBox rightLayout = new VBox(10);
        rightLayout.setSpacing(10);
        rightLayout.getChildren().addAll(chatArea, new HBox(10, messageInput, sendButton));

        // Use a HBox to place the chat list and right layout side by side
        HBox chatLayout = new HBox();
        chatLayout.getChildren().addAll(leftLayout, rightLayout);
        chatLayout.setSpacing(5); // Remove the strip (spacing) between the left and right side

        // Set the chat area to take more space using HBox.setHgrow() for dynamic resizing
        HBox.setHgrow(chatArea, Priority.ALWAYS);

        // Setup the chat stage with larger size
        Scene chatScene = new Scene(chatLayout, 600, 400); // Window size 600x400
        chatStage.setScene(chatScene);
        chatStage.show();


        sendButton.setOnAction(event -> {
            String message = messageInput.getText().trim();
            if (!message.isEmpty()) {
                chatArea.appendText(nickname + ": " + message + "\n");
                messageInput.clear(); // Clear the message input field after sending
            }
        });


        createChatButton.setOnAction(event -> {
            // Placeholder: Create a new individual chat
            System.out.println("Creating a new individual chat...");
            // You could add a dialog or window to enter the new chat details
        });

        createGroupChatButton.setOnAction(event -> {
            // Placeholder: Create a new group chat
            System.out.println("Creating a new group chat...");
            // Similar to individual chat, you could add a dialog for group creation
        });

        // Event listener for selecting a chat from the list
        chatList.setOnMouseClicked(event -> {
            // Get the selected item from the list
            String selectedChat = chatList.getSelectionModel().getSelectedItem();

            if (selectedChat != null) {
                // Display the selected chat's history or open chat window
                System.out.println("Selected chat: " + selectedChat);

                // For now, we can show the history of the selected chat in the chat area
                chatArea.setText(getChatHistory(selectedChat)); // Populate chat area with history of the selected chat
            }
        });
    }


    // Method to simulate retrieving chat history for a specific chat
    private String getChatHistory(String chatName) {
        // Placeholder chat history for each chat
        String[] histories = {
                "Chat 1 history: Welcome to Chat 1.",
                "Chat 2 history: Welcome to Chat 2.",
                "Chat 3 history: Welcome to Chat 3.",
                "Chat 4 history: Welcome to Chat 4.",
                "Chat 5 history: Welcome to Chat 5.",
                "Chat 6 history: Welcome to Chat 6."
        };

        // Return mock chat history based on the selected chat name
        return histories[Arrays.asList("Chat 1", "Chat 2", "Chat 3", "Chat 4", "Chat 5", "Chat 6").indexOf(chatName)];
    }
}
