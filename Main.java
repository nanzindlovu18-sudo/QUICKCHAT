import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Login user = new Login(
            "Kyle",
            "Smith",
            "KY1_1",
            "Ch&&sec@ke99!",
            "+27838968976"
        );

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

        if (result) {
            Scanner input = new Scanner(System.in);
            String choice;

            do {
                System.out.println();
                System.out.println("===== QUICKCHAT MENU =====");
                System.out.println("1. Send a message");
                System.out.println("2. Coming Soon");
                System.out.println("3. Quit");
                System.out.print("Choose an option: ");

                choice = input.nextLine();

                switch (choice) {
                    case "1":
    Scanner messageInput = new Scanner(System.in);

    System.out.print("Enter message ID: ");
    String id = messageInput.nextLine();

    System.out.print("Enter recipient cellphone number: ");
    String recipient = messageInput.nextLine();

    System.out.print("Enter your message (maximum 250 characters): ");
    String text = messageInput.nextLine();

    Message message = new Message(id, recipient, text);

    if (!message.checkMessageLength()) {
        System.out.println("Message exceeds 250 characters.");
    } else if (!message.checkRecipientCell()) {
        System.out.println("Invalid recipient cellphone number.");
    } else {
        System.out.println("1. Send");
        System.out.println("2. Disregard");
        System.out.println("3. Store");
        System.out.print("Choose an action: ");

        String action = messageInput.nextLine();

       switch (action) {
    case "1":
        message.sendMessage();
        System.out.println("Message successfully sent.");
        break;
    case "2":
        message.disregardMessage();
        System.out.println("Press 0 to delete the message.");
        break;
    case "3":
        message.storeMessage();
        System.out.println("Message successfully stored.");
        break;
    default:
        System.out.println("Invalid action.");
}

        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message hash: " + message.getMessageHash());
        System.out.println("Message status: " + message.getMessageStatus());
    }
    break;
                        
                    case "2":
                        System.out.println("Coming Soon.");
                        break;
                    case "3":
                        System.out.println("Thank you for using QuickChat!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            } while (!choice.equals("3"));

            input.close();
        }
    }
}