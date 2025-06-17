package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.List;

public class ChatApplicationTest {

    @Before
    public void setUp() {
        // Clear all storage before each test
        ChatApplication.MessageStorage.sentMessages.clear();
        ChatApplication.MessageStorage.disregardedMessages.clear();
        ChatApplication.MessageStorage.storedMessages.clear();
        ChatApplication.MessageStorage.messageHashes.clear();
        ChatApplication.MessageStorage.messageIDs.clear();
        ChatApplication.MessageStorage.recipients.clear();
        ChatApplication.MessageStorage.senders.clear();
    }

    @After
    public void tearDown() {
        // Clear again just to ensure a clean slate
        setUp();
    }

    @Test
    public void testMessageCreation() {
        ChatApplication.Message msg = new ChatApplication.Message("Alice", "+27831234567", "Hello Bob");
        assertNotNull(msg.getMessageID());
        assertNotNull(msg.getMessageHash());
        assertEquals("Alice", msg.getSender());
        assertEquals("+27831234567", msg.getRecipient());
        assertEquals("Hello Bob", msg.getContent());
    }

    @Test
    public void testSendMessageStorage() {
        ChatApplication.Message msg = new ChatApplication.Message("Sender1", "Recipient1", "Test Message");
        ChatApplication.MessageStorage.sentMessages.add(msg.getContent());
        ChatApplication.MessageStorage.messageHashes.add(msg.getMessageHash());
        ChatApplication.MessageStorage.messageIDs.add(msg.getMessageID());
        ChatApplication.MessageStorage.senders.add(msg.getSender());
        ChatApplication.MessageStorage.recipients.add(msg.getRecipient());

        assertEquals(1, ChatApplication.MessageStorage.sentMessages.size());
        assertEquals("Test Message", ChatApplication.MessageStorage.sentMessages.get(0));
    }

    @Test
    public void testDeleteByHash() {
        ChatApplication.Message msg = new ChatApplication.Message("Sender", "Recipient", "Test Deletion");
        ChatApplication.MessageStorage.sentMessages.add(msg.getContent());
        ChatApplication.MessageStorage.messageHashes.add(msg.getMessageHash());
        ChatApplication.MessageStorage.messageIDs.add(msg.getMessageID());
        ChatApplication.MessageStorage.senders.add(msg.getSender());
        ChatApplication.MessageStorage.recipients.add(msg.getRecipient());

        ChatApplication.MessageReporter.deleteByHash(msg.getMessageHash());

        assertNull(ChatApplication.MessageStorage.sentMessages.get(0));
        assertNull(ChatApplication.MessageStorage.messageHashes.get(0));
    }

    @Test
    public void testLongestSentMessage() {
        ChatApplication.Message msg1 = new ChatApplication.Message("S1", "R1", "Hi");
        ChatApplication.Message msg2 = new ChatApplication.Message("S2", "R2", "This is the longest message");

        ChatApplication.MessageStorage.sentMessages.add(msg1.getContent());
        ChatApplication.MessageStorage.sentMessages.add(msg2.getContent());

        // We can't assert printed content, but we can assert the longest message manually
        String expected = "This is the longest message";
        String longest = "";
        for (String m : ChatApplication.MessageStorage.sentMessages) {
            if (m != null && m.length() > longest.length()) {
                longest = m;
            }
        }

        assertEquals(expected, longest);
    }

    @Test
    public void testSearchByRecipient() {
        ChatApplication.Message msg1 = new ChatApplication.Message("Sender1", "Bob", "Hi Bob");
        ChatApplication.Message msg2 = new ChatApplication.Message("Sender2", "Alice", "Hi Alice");

        ChatApplication.MessageStorage.sentMessages.add(msg1.getContent());
        ChatApplication.MessageStorage.messageIDs.add(msg1.getMessageID());
        ChatApplication.MessageStorage.messageHashes.add(msg1.getMessageHash());
        ChatApplication.MessageStorage.senders.add(msg1.getSender());
        ChatApplication.MessageStorage.recipients.add(msg1.getRecipient());

        ChatApplication.MessageStorage.sentMessages.add(msg2.getContent());
        ChatApplication.MessageStorage.messageIDs.add(msg2.getMessageID());
        ChatApplication.MessageStorage.messageHashes.add(msg2.getMessageHash());
        ChatApplication.MessageStorage.senders.add(msg2.getSender());
        ChatApplication.MessageStorage.recipients.add(msg2.getRecipient());

        List<String> matchedMessages = ChatApplication.MessageStorage.sentMessages;
        List<String> recipients = ChatApplication.MessageStorage.recipients;
        int count = 0;
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals("Bob")) {
                assertEquals("Hi Bob", matchedMessages.get(i));
                count++;
            }
        }
        assertEquals(1, count);
    }
}