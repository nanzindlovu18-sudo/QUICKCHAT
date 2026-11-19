import java.util.Scanner;

public class Main {

    private static final String SENDER = "+27838968976";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Login user = new Login(
            "Kyle",
            "Smith",
            "KY1_1",
            "Ch&&sec@ke99!",
            SENDER
        );

        MessageStorage storage = new MessageStorage();

        System.out.println("===== REGISTRATION TEST =====");
        System.out.println("Username valid: " + user.checkUserName());
        System.out.println("Password valid: " + user.checkPasswordComplexity());
        System.out.println("Cell phone valid: " + user.checkCellPhoneNumber());
        System.out.println(user.registerUser());

        System.out.println();
        System.out.println("===== LOGIN TEST =====");

        boolean result = user.loginUser("KY1_1", "Ch&&sec@ke99!");

        System.out.println("Login successful: " + result);
        System.out.println(user.returnLoginStatus());

        if (!result) {
            input.close();
            return;
        }

        // Load messages from JSON file
        storage.loadMessagesFromJSON("messages.json");

        String choice;

        do {
            System.out.println();
            System.out.println("===== QUICKCHAT MENU =====");
            System.out.println("1. Send a message");
            System.out.println("2. Display sent messages");
            System.out.println("3. Stored Messages");
            System.out.println("4. Quit");
            System.out.print("Choose an option: ");

            choice = input.nextLine();

            switch (choice) {

                case "1":
                    sendMessage(input, storage);
                    break;

                case "2":
                    displaySentMessages(storage);
                    break;

                case "3":
                    storedMessagesMenu(input, storage);
                    break;

                case "4":
                    System.out.println(
                        "Thank you for using QuickChat!"
                    );
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (!choice.equals("4"));

        input.close();
    }

    // Send a new message
    private static void sendMessage(
            Scanner input, MessageStorage storage) {

        System.out.print("Enter message ID: ");
        String id = input.nextLine();

        System.out.print("Enter recipient cellphone number: ");
        String recipient = input.nextLine();

        System.out.print("Enter your message: ");
        String text = input.nextLine();

        Message message = new Message(
            id, SENDER, recipient, text
        );

        if (!message.checkMessageLength()) {
            System.out.println("Message exceeds 250 characters.");
            return;
        }

        if (!message.checkRecipientCell()) {
            System.out.println("Invalid recipient cellphone number.");
            return;
        }

        System.out.println("1. Send");
        System.out.println("2. Disregard");
        System.out.println("3. Store");
        System.out.print("Choose an action: ");

        String action = input.nextLine();

        switch (action) {

            case "1":
                message.sendMessage();
                storage.addMessage(message);
                System.out.println("Message successfully sent.");
                break;

            case "2":
                message.disregardMessage();
                storage.addMessage(message);
                System.out.println("Message disregarded.");
                break;

            case "3":
                message.storeMessage();
                storage.addMessage(message);
                System.out.println("Message successfully stored.");
                break;

            default:
                System.out.println("Invalid action.");
                return;
        }

        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Message Status: " + message.getMessageStatus());
    }

    // Display all sent messages
    private static void displaySentMessages(
            MessageStorage storage) {

        Message[] messages = storage.getSentMessages();

        if (messages.length == 0) {
            System.out.println("No sent messages found.");
            return;
        }

        System.out.println("===== SENT MESSAGES =====");

        for (Message message : messages) {
            System.out.println(message.getMessageText());
        }
    }

    // Stored messages menu
    private static void storedMessagesMenu(
            Scanner input, MessageStorage storage) {

        String choice;

        do {
            System.out.println();
            System.out.println("===== STORED MESSAGES =====");
            System.out.println("1. Display senders and recipients");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search message by ID");
            System.out.println("4. Search messages by recipient");
            System.out.println("5. Delete message using hash");
            System.out.println("6. Display full message report");
            System.out.println("7. Return to main menu");
            System.out.print("Choose an option: ");

            choice = input.nextLine();

            switch (choice) {

                case "1":
                    storage.displayStoredSendersAndRecipients();
                    break;

                case "2":
                    Message longest =
                        storage.getLongestStoredMessage();

                    if (longest == null) {
                        System.out.println("No stored messages found.");
                    } else {
                        System.out.println(
                            "Longest message: "
                            + longest.getMessageText()
                        );
                    }
                    break;

                case "3":
                    System.out.print("Enter message ID: ");
                    String id = input.nextLine();

                    Message found = storage.searchByID(id);

                    if (found == null) {
                        System.out.println("Message not found.");
                    } else {
                        System.out.println(
                            "Recipient: " + found.getRecipient()
                        );
                        System.out.println(
                            "Message: " + found.getMessageText()
                        );
                    }
                    break;

                case "4":
                    System.out.print("Enter recipient number: ");
                    String recipient = input.nextLine();

                    Message[] results =
                        storage.searchByRecipient(recipient);

                    if (results.length == 0) {
                        System.out.println("No messages found.");
                    } else {
                        for (Message message : results) {
                            System.out.println(
                                message.getMessageText()
                            );
                        }
                    }
                    break;

                case "5":
                    System.out.print("Enter message hash: ");
                    String hash = input.nextLine();

                    if (storage.deleteByHash(hash)) {
                        System.out.println("Message deleted successfully.");
                    } else {
                        System.out.println("Message hash not found.");
                    }
                    break;

                case "6":
                    storage.displayReport();
                    break;

                case "7":
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (!choice.equals("7"));
    }
}