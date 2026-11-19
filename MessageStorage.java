import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageStorage {

    private Message[] sentMessages = new Message[0];
    private Message[] disregardedMessages = new Message[0];
    private Message[] storedMessages = new Message[0];

    private String[] messageHashes = new String[0];
    private String[] messageIDs = new String[0];

    public void addMessage(Message message) {
        if (message == null) return;

        String status = message.getMessageStatus();

        if (status.equals("Sent")) {
            sentMessages = addToArray(sentMessages, message);
        } else if (status.equals("Disregarded")) {
            disregardedMessages = addToArray(disregardedMessages, message);
        } else if (status.equals("Stored")) {
            storedMessages = addToArray(storedMessages, message);
        } else {
            return;
        }

        messageHashes = addToArray(messageHashes, message.getMessageHash());
        messageIDs = addToArray(messageIDs, message.getMessageID());
    }

    private Message[] addToArray(Message[] array, Message message) {
        Message[] result = new Message[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = message;
        return result;
    }

    private String[] addToArray(String[] array, String value) {
        String[] result = new String[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = value;
        return result;
    }

    public void loadMessagesFromJSON(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder json = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            Pattern objectPattern = Pattern.compile("\\{(.*?)\\}");
            Matcher objectMatcher = objectPattern.matcher(json.toString());

            while (objectMatcher.find()) {
                String object = objectMatcher.group(1);

                String id = getJsonValue(object, "messageID");
                String sender = getJsonValue(object, "sender");
                String recipient = getJsonValue(object, "recipient");
                String messageText = getJsonValue(object, "messageText");
                String status = getJsonValue(object, "messageStatus");

                if (id == null || recipient == null ||
                    messageText == null || status == null) {
                    continue;
                }

                if (sender == null || sender.isEmpty()) {
                    sender = "Unknown sender";
                }

                Message message = new Message(id, sender, recipient, messageText);

                if (status.equalsIgnoreCase("Sent")) {
                    message.sendMessage();
                } else if (status.equalsIgnoreCase("Stored")) {
                    message.storeMessage();
                } else if (status.equalsIgnoreCase("Disregarded")) {
                    message.disregardMessage();
                } else {
                    continue;
                }

                addMessage(message);
            }

            System.out.println("JSON messages loaded successfully.");

        } catch (IOException e) {
            System.out.println("Could not read JSON file: " + e.getMessage());
        }
    }

    private String getJsonValue(String object, String key) {
        Pattern pattern = Pattern.compile(
            "\"" + Pattern.quote(key) +
            "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\""
        );

        Matcher matcher = pattern.matcher(object);

        if (matcher.find()) {
            return matcher.group(1)
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\n", "\n");
        }

        return null;
    }

    public Message[] getSentMessages() {
        return sentMessages;
    }

    public Message[] getDisregardedMessages() {
        return disregardedMessages;
    }

    public Message[] getStoredMessages() {
        return storedMessages;
    }

    public String[] getMessageHashes() {
        return messageHashes;
    }

    public String[] getMessageIDs() {
        return messageIDs;
    }

    public void displayStoredSendersAndRecipients() {
        System.out.println("===== STORED SENDERS AND RECIPIENTS =====");

        if (storedMessages.length == 0) {
            System.out.println("No stored messages found.");
            return;
        }

        for (Message message : storedMessages) {
            System.out.println("Sender: " + message.getSender());
            System.out.println("Recipient: " + message.getRecipient());
            System.out.println("----------------------");
        }
    }

    public Message searchByID(String id) {
        for (Message message : storedMessages) {
            if (message.getMessageID().equals(id)) return message;
        }

        for (Message message : sentMessages) {
            if (message.getMessageID().equals(id)) return message;
        }

        return null;
    }

    public Message[] searchByRecipient(String recipient) {
        Message[] results = new Message[0];

        for (Message message : storedMessages) {
            if (message.getRecipient().equals(recipient)) {
                results = addToArray(results, message);
            }
        }

        return results;
    }

    public Message getLongestStoredMessage() {
        Message longest = null;

        for (Message message : storedMessages) {
            if (longest == null ||
                message.getMessageText().length() >
                longest.getMessageText().length()) {
                longest = message;
            }
        }

        return longest;
    }

    public boolean deleteByHash(String hash) {
        for (int i = 0; i < storedMessages.length; i++) {
            if (storedMessages[i].getMessageHash().equals(hash)) {
                Message[] result = new Message[storedMessages.length - 1];

                for (int j = 0, k = 0; j < storedMessages.length; j++) {
                    if (j != i) result[k++] = storedMessages[j];
                }

                storedMessages = result;
                rebuildHashAndIDArrays();
                return true;
            }
        }

        return false;
    }

    private void rebuildHashAndIDArrays() {
        messageHashes = new String[0];
        messageIDs = new String[0];

        for (Message message : sentMessages) addToIndexes(message);
        for (Message message : disregardedMessages) addToIndexes(message);
        for (Message message : storedMessages) addToIndexes(message);
    }

    private void addToIndexes(Message message) {
        messageHashes = addToArray(messageHashes, message.getMessageHash());
        messageIDs = addToArray(messageIDs, message.getMessageID());
    }

    public void displayReport() {
        System.out.println("===== MESSAGE REPORT =====");

        if (storedMessages.length == 0) {
            System.out.println("No stored messages found.");
            return;
        }

        for (Message message : storedMessages) {
            System.out.println("Message ID: " + message.getMessageID());
            System.out.println("Message Hash: " + message.getMessageHash());
            System.out.println("Sender: " + message.getSender());
            System.out.println("Recipient: " + message.getRecipient());
            System.out.println("Message: " + message.getMessageText());
            System.out.println("Status: " + message.getMessageStatus());
            System.out.println("-------------------------");
        }
    }
}