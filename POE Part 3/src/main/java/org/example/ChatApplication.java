package org.example;

import javax.swing.*;
import java.io.FileWriter;
import java.util.*;
import org.json.JSONObject;

public class ChatApplication {
    static class Message {
        private static int totalMessages = 0;
        private final String sender;
        private final String recipient;
        private final String content;
        private final String messageID;
        private final String messageHash;

        public Message(String sender, String recipient, String content) {
            this.sender = sender;
            this.recipient = recipient;
            this.content = content.trim();
            this.messageID = generateMessageID();
            this.messageHash = generateHash();
        }

        private String generateMessageID() {
            int number = ++totalMessages;
            Random rand = new Random();
            return String.format("MSG%02d%c", number, (char) (rand.nextInt(26) + 'A'));
        }

        private String generateHash() {
            if (content.isEmpty()) {
                return messageID + ":empty";
            }
            String[] words = content.split(" ");
            String first = words.length > 0 ? words[0] : "";
            String last = words.length > 1 ? words[words.length - 1] : first;
            return messageID.substring(0, 2) + ":" + totalMessages + ":" + (first + last).toUpperCase();
        }

        public String getMessageID() { return messageID; }
        public String getMessageHash() { return messageHash; }
        public String getRecipient() { return recipient; }
        public String getContent() { return content; }
        public String getSender() { return sender; }
    }

    static class MessageStorage {
        static List<String> sentMessages = new ArrayList<>();
        static List<String> disregardedMessages = new ArrayList<>();
        static List<String> storedMessages = new ArrayList<>();
        static List<String> messageHashes = new ArrayList<>();
        static List<String> messageIDs = new ArrayList<>();
        static List<String> recipients = new ArrayList<>();
        static List<String> senders = new ArrayList<>();

        public static void storeToJSON() {
            try {
                JSONObject json = new JSONObject();
                json.put("sentMessages", sentMessages.toArray());
                json.put("disregardedMessages", disregardedMessages.toArray());
                json.put("storedMessages", storedMessages.toArray());
                json.put("messageHashes", messageHashes.toArray());
                json.put("messageIDs", messageIDs.toArray());
                json.put("recipients", recipients.toArray());
                json.put("senders", senders.toArray());

                FileWriter file = new FileWriter("messages.json");
                file.write(json.toString(4));
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MessageReporter {
        public static void displaySentMessages() {
            if (MessageStorage.sentMessages.isEmpty()) {
                System.out.println("No sent messages.");
                return;
            }
            for (int i = 0; i < MessageStorage.sentMessages.size(); i++) {
                System.out.println("From: " + MessageStorage.senders.get(i) +
                        " | To: " + MessageStorage.recipients.get(i) +
                        " | Message: " + MessageStorage.sentMessages.get(i));
            }
        }

        public static void displayLongestSentMessage() {
            String longest = "";
            for (String msg : MessageStorage.sentMessages) {
                if (msg != null && msg.length() > longest.length()) {
                    longest = msg;
                }
            }
            System.out.println("Longest Sent Message: " + longest);
        }

        public static void searchByMessageID(String id) {
            for (int i = 0; i < MessageStorage.messageIDs.size(); i++) {
                if (MessageStorage.messageIDs.get(i).equals(id)) {
                    System.out.println("Found: " + MessageStorage.recipients.get(i) +
                            " | " + MessageStorage.sentMessages.get(i));
                    return;
                }
            }
            System.out.println("Message ID not found.");
        }

        public static void searchByRecipient(String recipient) {
            System.out.println("Messages sent to " + recipient + ":");
            for (int i = 0; i < MessageStorage.recipients.size(); i++) {
                if (MessageStorage.recipients.get(i).equals(recipient)) {
                    if (i < MessageStorage.sentMessages.size()) {
                        System.out.println(MessageStorage.sentMessages.get(i));
                    }
                }
            }
        }

        public static void deleteByHash(String hash) {
            for (int i = 0; i < MessageStorage.messageHashes.size(); i++) {
                if (MessageStorage.messageHashes.get(i) != null && MessageStorage.messageHashes.get(i).equals(hash)) {
                    MessageStorage.sentMessages.set(i, null);
                    MessageStorage.messageHashes.set(i, null);
                    System.out.println("Message deleted.");
                    return;
                }
            }
            System.out.println("Hash not found.");
        }

        public static void generateReport() {
            System.out.println("---- Sent Message Report ----");
            for (int i = 0; i < MessageStorage.sentMessages.size(); i++) {
                String msg = MessageStorage.sentMessages.get(i);
                if (msg != null) {
                    System.out.println("ID: " + MessageStorage.messageIDs.get(i) +
                            " | Hash: " + MessageStorage.messageHashes.get(i) +
                            " | To: " + MessageStorage.recipients.get(i) +
                            " | From: " + MessageStorage.senders.get(i) +
                            " | Msg: " + msg);
                }
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            String option = JOptionPane.showInputDialog("QuickChat Menu:\n" +
                    "1. Send Message\n2. Show Sent Messages\n3. Longest Sent Message\n" +
                    "4. Search by Message ID\n5. Search by Recipient\n6. Delete by Hash\n" +
                    "7. Generate Report\n8. Save to JSON\n9. Quit");

            if (option == null) break;

            switch (option) {
                case "1":
                    String sender = JOptionPane.showInputDialog("Enter sender name:");
                    String recipient = JOptionPane.showInputDialog("Enter recipient (+27831234567):");
                    String content = JOptionPane.showInputDialog("Enter your message:");

                    if (content == null || content.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Message cannot be empty.");
                        continue;
                    }

                    Message msg = new Message(sender, recipient, content);

                    Object[] actions = {"Send", "Disregard", "Store"};
                    int action = JOptionPane.showOptionDialog(null, "Choose message action", "Action",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, actions, actions[0]);

                    MessageStorage.messageIDs.add(msg.getMessageID());
                    MessageStorage.messageHashes.add(msg.getMessageHash());
                    MessageStorage.recipients.add(recipient);
                    MessageStorage.senders.add(sender);

                    switch (action) {
                        case 0:
                            MessageStorage.sentMessages.add(content);
                            JOptionPane.showMessageDialog(null, "Message sent successfully.");
                            break;
                        case 1:
                            MessageStorage.disregardedMessages.add(content);
                            JOptionPane.showMessageDialog(null, "Message disregarded.");
                            break;
                        case 2:
                            MessageStorage.storedMessages.add(content);
                            JOptionPane.showMessageDialog(null, "Message stored successfully.");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid message action.");
                    }
                    break;

                case "2":
                    MessageReporter.displaySentMessages();
                    break;
                case "3":
                    MessageReporter.displayLongestSentMessage();
                    break;
                case "4":
                    String id = JOptionPane.showInputDialog("Enter Message ID to search:");
                    MessageReporter.searchByMessageID(id);
                    break;
                case "5":
                    String r = JOptionPane.showInputDialog("Enter recipient to search:");
                    MessageReporter.searchByRecipient(r);
                    break;
                case "6":
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                    MessageReporter.deleteByHash(hash);
                    break;
                case "7":
                    MessageReporter.generateReport();
                    break;
                case "8":
                    MessageStorage.storeToJSON();
                    JOptionPane.showMessageDialog(null, "Messages saved to messages.json");
                    break;
                case "9":
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }
}