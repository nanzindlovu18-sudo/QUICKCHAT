public class Message {

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String messageStatus;

    public Message(String messageID, String recipient,
                   String messageText) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageStatus = "Pending";
        this.messageHash = createMessageHash();
    }

    public String getMessageID() {
        return messageID;
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