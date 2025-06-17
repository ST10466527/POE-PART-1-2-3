package org.example;

import javax.swing.*;
import java.util.Random;

import static org.example.sendMessagesFile.sendMessages;

class Message {
    private String messageID;
    private String recipient;
    private String content;
    private String messageHash;
    private int messageNumber;
    private static int totalMessages = 0;

    public Message(String recipient, String content) {
        this.recipient = recipient;
        this.content = content;
        this.messageNumber = ++totalMessages;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Validation methods
    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
        return recipient.startsWith("+27") && recipient.length() == 12;
    }

    public String createMessageHash() {
        String[] words = content.split(" ");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + (first + last).toUpperCase();
    }

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid choice";
        }
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    private String generateMessageID() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1000000000));
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public void storeMessage() {
        // Placeholder for future storage logic
    }
}



public class ChatApplication {
    public static void main(String[] args) {
        Login loginSystem = new Login();
        loginSystem.registerAndLoginUser();

        while (true) {
            String choice = JOptionPane.showInputDialog(
                    "QuickChat Menu:\n" +
                            "1. Send Messages\n" +
                            "2. Show Recently Sent Messages\n" +
                            "3. Quit"
            );

            switch (choice) {
                case "1":
                    sendMessagesFile sendMessage = new sendMessagesFile();
                    sendMessage.registerAndLoginUser();
                    sendMessages();
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon");
                    break;
                case "3":
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice");
            }
        }
    }

}

