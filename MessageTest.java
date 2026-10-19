import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testMessageLengthValid() {
        Message message = new Message(
            "01", "+27838968976", "Hello there"
        );
        assertTrue(message.checkMessageLength());
    }

    @Test
    public void testMessageLengthOver250() {
        String longText = "a".repeat(251);
        Message message = new Message(
            "02", "+27838968976", longText
        );
        assertFalse(message.checkMessageLength());
    }

    @Test
    public void testValidRecipient() {
        Message message = new Message(
            "03", "+27838968976", "Hello"
        );
        assertTrue(message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipient() {
        Message message = new Message(
            "04", "12345", "Hello"
        );
        assertFalse(message.checkRecipientCell());
    }

    @Test
    public void testMessageHashCreated() {
        Message message = new Message(
            "05", "+27838968976", "Hello world"
        );
        assertEquals("05:Hello:world", message.getMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(
            "06", "+27838968976", "Hello"
        );
        message.sendMessage();
        assertEquals("Sent", message.getMessageStatus());
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message(
            "07", "+27838968976", "Hello"
        );
        message.disregardMessage();
        assertEquals("Disregarded", message.getMessageStatus());
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message(
            "08", "+27838968976", "Hello"
        );
        message.storeMessage();
        assertEquals("Stored", message.getMessageStatus());
    }
}