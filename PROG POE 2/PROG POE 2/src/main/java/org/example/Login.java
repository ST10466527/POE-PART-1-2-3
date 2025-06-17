package org.example;

import javax.swing.*;

class Login {
    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String surname;

    public void registerAndLoginUser() {
        while (true) {
            String username = JOptionPane.showInputDialog("Enter username (must contain '_' and be less or equal to 5 chars):");
            if (!checkUsername(username)) continue;

            String password = JOptionPane.showInputDialog("Enter password (1 uppercase, 1 digit, 1 special char, > 8 chars):");
            if (!checkPassword(password)) continue;

            String cell = JOptionPane.showInputDialog("Enter cellphone number (must start with +27 and can be 12 digits):");
            if (!checkCell(cell)) continue;

            this.registeredUsername = username;
            this.registeredPassword = password;
            this.firstName = JOptionPane.showInputDialog("Enter your first name:");
            this.surname = JOptionPane.showInputDialog("Enter your surname:");

            JOptionPane.showMessageDialog(null, "User has been successfully registered.");
            break;
        }

        boolean loginSuccess = false;
        while (!loginSuccess) {
            String inputUser = JOptionPane.showInputDialog("Login - Enter username:");
            String inputPass = JOptionPane.showInputDialog("Login - Enter password:");

            loginSuccess = inputUser.equals(registeredUsername) && inputPass.equals(registeredPassword);

            if (loginSuccess) {
                JOptionPane.showMessageDialog(null, "Welcome " + firstName + " " + surname + ", it is great to see you again!");
            } else {
                JOptionPane.showMessageDialog(null, "Login failed. Try again.");
            }
        }
    }

    public boolean checkUsername(String username) {
        if (username.contains("_") && username.length() <= 5) {
            JOptionPane.showMessageDialog(null, "Username has been successfully captured.");
            return true;
        }
        JOptionPane.showMessageDialog(null, "Invalid username format.");
        return false;
    }

     public boolean checkPassword(String password) {
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()\\-+=<>?/{}~|].*");
        boolean longEnough = password.length() >= 8;

        if (hasUpper && hasDigit && hasSpecial && longEnough) {
            JOptionPane.showMessageDialog(null, "Password has been successfully captured.");
            return true;
        }
        JOptionPane.showMessageDialog(null, "Invalid password format.");
        return false;
    }

    public boolean checkCell(String cell) {
        if (cell.startsWith("+27") && cell.length() <= 12) {
            JOptionPane.showMessageDialog(null, "Cellphone number is correctly formatted.");
            return true;
        }
        JOptionPane.showMessageDialog(null, "Invalid cellphone number.");
        return false;
    }
}