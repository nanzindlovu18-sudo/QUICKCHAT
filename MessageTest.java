import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testMessageLengthValid() {
        Message message = new Message("01", "+27838968976", "Hello there");
        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageLengthOver250() {
        String longText = "a".repeat(251);
        Message message = new Message("02", "+27838968976", longText);
        assertFalse(message.checkMessageLength());
    }

    @Test
    public void testValidRecipient() {
        Message message = new Message("03", "+27838968976", "Hello");
        assertTrue(message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipient() {
        Message message = new Message("04", "12345", "Hello");
        assertFalse(message.checkRecipientCell());
    }

    @Test
    public void testMessageHashCreated() {
        Message message = new Message("05", "+27838968976", "Hello world");
        assertEquals("05:Hello:world", message.getMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message("06", "+27838968976", "Hello");
        message.sendMessage();
        assertEquals("Sent", message.getMessageStatus());
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message("07", "+27838968976", "Hello");
        message.disregardMessage();
        assertEquals("Disregarded", message.getMessageStatus());
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message("08", "+27838968976", "Hello");
        message.storeMessage();
        assertEquals("Stored", message.getMessageStatus());
    }

    @Test
    public void testSentMessagesArray() {
        MessageStorage storage = new MessageStorage();

        Message message = new Message("1", "Sender",
                "+27834557896", "Did you get the cake?");
        message.sendMessage();
        storage.addMessage(message);

        assertEquals(1, storage.getSentMessages().length);
        assertEquals("Did you get the cake?",
                storage.getSentMessages()[0].getMessageText());
    }

    @Test
    public void testDisregardedMessagesArray() {
        MessageStorage storage = new MessageStorage();

        Message message = new Message("3", "Sender",
                "+27834484567", "Yohoooo, I am at your gate.");
        message.disregardMessage();
        storage.addMessage(message);

        assertEquals(1, storage.getDisregardedMessages().length);
    }

    @Test
    public void testStoredMessagesArray() {
        MessageStorage storage = new MessageStorage();

        Message message = new Message("2", "Sender",
                "+27838884567", "Where are you?");
        message.storeMessage();
        storage.addMessage(message);

        assertEquals(1, storage.getStoredMessages().length);
    }

    @Test
    public void testLongestStoredMessage() {
        MessageStorage storage = new MessageStorage();

        Message shortMessage = new Message("1", "Sender",
                "+27838884567", "Hello");
        shortMessage.storeMessage();

        Message longMessage = new Message("2", "Sender",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        longMessage.storeMessage();

        storage.addMessage(shortMessage);
        storage.addMessage(longMessage);

        assertEquals(longMessage, storage.getLongestStoredMessage());
    }

    @Test
    public void testSearchMessageByID() {
        MessageStorage storage = new MessageStorage();

        Message message = new Message("2", "Sender",
                "+27838884567", "Where are you?");
        message.storeMessage();
        storage.addMessage(message);

        assertEquals(message, storage.searchByID("2"));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        MessageStorage storage = new MessageStorage();

        Message first = new Message("2", "Sender",
                "+27838884567", "Where are you?");
        first.storeMessage();

        Message second = new Message("5", "Sender",
                "+27838884567", "Ok, I am leaving without you.");
        second.storeMessage();

        storage.addMessage(first);
        storage.addMessage(second);

        assertEquals(2, storage.searchByRecipient("+27838884567").length);
    }

    @Test
    public void testDeleteMessageUsingHash() {
        MessageStorage storage = new MessageStorage();

        Message message = new Message("2", "Sender",
                "+27838884567", "Where are you?");
        message.storeMessage();
        storage.addMessage(message);

        assertTrue(storage.deleteByHash(message.getMessageHash()));
        assertEquals(0, storage.getStoredMessages().length);
    }
}