package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {

    private Message message;

    @Before
    public void setup() {
        message = new Message("+27123456789", "Hello world");
    }

    @Test
    public void testMessageIDLength() {
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testValidRecipientCell() {
        assertTrue(message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipientCell() {
        Message invalidMessage = new Message("0834567890", "Test content");
        assertFalse(invalidMessage.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHash() {
        String hash = message.createMessageHash();
        assertTrue(hash.matches("^\\d{2}:\\d+:[A-Z]+$"));
    }

    @Test
    public void testSentMessageOption1() {
        assertEquals("Message successfully sent.", message.sentMessage(1));
    }

    @Test
    public void testSentMessageOption2() {
        assertEquals("Press 0 to delete message.", message.sentMessage(2));
    }

    @Test
    public void testSentMessageOption3() {
        assertEquals("Message successfully stored.", message.sentMessage(3));
    }

    @Test
    public void testSentMessageInvalidOption() {
        assertEquals("Invalid choice", message.sentMessage(99));
    }

    @Test
    public void testTotalMessagesIncrements() {
        int before = Message.returnTotalMessages();
        new Message("+27123456780", "New message");
        int after = Message.returnTotalMessages();
        assertEquals(before + 1, after);
    }
}