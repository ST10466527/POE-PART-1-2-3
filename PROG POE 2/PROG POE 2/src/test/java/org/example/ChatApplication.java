package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatApplication {

    private Message message;

    @Before
    public void setUp() {
        // Use valid recipient and short content for consistent hash testing
        message = new Message("+27123456789", "Hello world");
    }

    @Test
    public void testMessageIDLength() {
        String messageID = message.getMessageID();
        assertEquals(10, messageID.length());
    }

    @Test
    public void testRecipientValidation_Valid() {
        assertTrue(message.checkRecipientCell());
    }

    @Test
    public void testRecipientValidation_Invalid() {
        Message invalid = new Message("0812345678", "Test");
        assertFalse(invalid.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHashFormat() {
        String hash = message.createMessageHash();
        String[] parts = hash.split(":");
        assertEquals(3, parts.length);
        assertTrue(parts[2].startsWith("HELLO") && parts[2].endsWith("WORLD"));
    }

    @Test
    public void testSentMessage_Choice1() {
        assertEquals("Message successfully sent.", message.sentMessage(1));
    }

    @Test
    public void testSentMessage_Choice2() {
        assertEquals("Press 0 to delete message.", message.sentMessage(2));
    }

    @Test
    public void testSentMessage_Choice3() {
        assertEquals("Message successfully stored.", message.sentMessage(3));
    }

    @Test
    public void testSentMessage_InvalidChoice() {
        assertEquals("Invalid choice", message.sentMessage(99));
    }

    @Test
    public void testTotalMessagesCountIncrements() {
        int before = Message.returnTotalMessages();
        new Message("+27123456780", "Another message");
        int after = Message.returnTotalMessages();
        assertEquals(before + 1, after);
    }
}