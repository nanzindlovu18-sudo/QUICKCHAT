public class Message {

    private String messageID;
    private String sender;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String messageStatus;

    // Four-argument constructor for Part 3
    public Message(String messageID, String sender,
                   String recipient, String messageText) {

        this.messageID = messageID;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageStatus = "Pending";
        this.messageHash = createMessageHash();
    }

    // Three-argument constructor for existing Part 1 and Part 2 tests
    public Message(String messageID, String recipient,
                   String messageText) {

        this(messageID, "Unknown sender", recipient, messageText);
    }

    public String getMessageID() {
        return messageID;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public String getMessageHash() {
        return messageHash;
    }

    private String createMessageHash() {

        String[] words = messageText.trim().split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0
                ? words[words.length - 1] : "";

        return messageID + ":" + firstWord + ":" + lastWord;
    }

    public boolean checkMessageLength() {
        return messageText != null
                && messageText.length() <= 250;
    }

    public boolean checkRecipientCell() {
        return recipient != null
                && recipient.matches("^\\+\\d{1,3}\\d{7,10}$");
    }

    public void sendMessage() {
        messageStatus = "Sent";
    }

    public void disregardMessage() {
        messageStatus = "Disregarded";
    }

    public void storeMessage() {
        messageStatus = "Stored";
    }
}