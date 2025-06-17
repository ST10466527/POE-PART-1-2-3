package org.example;

import javax.swing.*;
import java.util.Random;

public class sendMessagesFile
{
    public static void sendMessages() {
        String numInput = JOptionPane.showInputDialog("How many messages would you like to send?");
        int numMessages = Integer.parseInt(numInput);

        for (int i = 0; i < numMessages; i++) {
            String recipient = JOptionPane.showInputDialog("Enter recipient number:");
            String content = JOptionPane.showInputDialog("Enter your message (max 250 chars):");

            if (content.length() > 250) {
                JOptionPane.showMessageDialog(null, "Message too long");
                i--;
                continue;
            }

            Message message = new Message(recipient, content);

            if (!message.checkRecipientCell()) {
                JOptionPane.showMessageDialog(null, "Invalid recipient number");
                i--;
                continue;
            }

            JOptionPane.showMessageDialog(null, "Message ID generated: " + message.getMessageID());
            JOptionPane.showMessageDialog(null, "Message hash: " + message.getMessageHash());

            String[] options = {"Send", "Disregard", "Store"};
            int action = JOptionPane.showOptionDialog(
                    null,
                    "Message Options",
                    "Choose action:",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            JOptionPane.showMessageDialog(null, message.sentMessage(action + 1));
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());
    }

    public static String processMessage(String recipient, String content, int choice) {
        return recipient;
    }

    public void registerAndLoginUser() {

    }
}

