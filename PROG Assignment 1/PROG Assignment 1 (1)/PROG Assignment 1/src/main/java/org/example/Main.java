package org.example;

import java.util.Scanner;

class User {
    String username;
    String password;
    String cellphone;
    String firstName;
    String surname;

    public User(String username, String password, String cellphone, String firstName, String surname) {
        this.username = username;
        this.password = password;
        this.cellphone = cellphone;
        this.firstName = firstName;
        this.surname = surname;
    }
}

public class Main {
    static User registeredUser;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter a username: ");
        String username = sc.next();

        System.out.println("Please enter a password: ");
        String password = sc.next();

        System.out.println("Please enter your cellphone number: ");
        String cellNumber = sc.next();

        System.out.println("Please enter your first name: ");
        String firstName = sc.next();

        System.out.println("Please enter your surname: ");
        String surname = sc.next();

        String registrationMessage = registerUser(username, password, cellNumber, firstName, surname);
        System.out.println(registrationMessage);

        if (registrationMessage.equals("User has been successfully registered.")) {
            System.out.println("\n=== LOGIN ===");
            boolean loginSuccess = loginUser();
            String loginStatusMessage = returnLoginStatus(loginSuccess);
            System.out.println(loginStatusMessage);
        }
    }

    public static boolean CheckUsername(String Username) {
        if (Username.contains("_") && Username.length() > 5) {
            System.out.println("Username has been successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is not longer than 5 characters in length.");
            return false;
        }
    }

    public static boolean CheckPasswordComplexity(String Password) {
        boolean hasUppercase = !Password.equals(Password.toLowerCase());
        boolean hasDigit = Password.matches(".*\\d.*");
        boolean hasSpecialChar = Password.matches(".*[!@#$%^&*()-+=<>?/{}~|].*");
        boolean longEnough = Password.length() >= 8;

        if (hasUppercase && hasDigit && hasSpecialChar && longEnough) {
            System.out.println("Password is successfully captured.");
            return true;
        } else {
            System.out.println("Password is not correctly formatted. It must contain at least one uppercase letter, one digit, one special character, and be at least 8 characters long.");
            return false;
        }
    }

    public static boolean CheckCellphoneNumberComplexity(String cellphoneNumber) {
        if (cellphoneNumber.startsWith("+27") && cellphoneNumber.length() == 12) {
            System.out.println("Your number is correct.");
            return true;
        } else {
            System.out.println("Cellphone number is incorrectly formatted or does not contain international code.");
            return false;
        }
    }

    public static String registerUser(String username, String password, String cellphone, String firstName, String surname) {
        boolean usernameValid = CheckUsername(username);
        boolean passwordValid = CheckPasswordComplexity(password);
        boolean cellphoneValid = CheckCellphoneNumberComplexity(cellphone);

        if (!usernameValid) {
            return "Username is incorrectly formatted.";
        } else if (!passwordValid) {
            return "Password does not meet the complexity requirements.";
        } else if (!cellphoneValid) {
            return "Cellphone number is incorrectly formatted.";
        } else {
            registeredUser = new User(username, password, cellphone, firstName, surname);
            return "User has been successfully registered.";
        }
    }

    public static boolean loginUser() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String loginUsername = sc.next();

        System.out.print("Enter your password: ");
        String loginPassword = sc.next();

        return loginUsername.equals(registeredUser.username) && loginPassword.equals(registeredUser.password);
    }

    public static String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + registeredUser.firstName + " " + registeredUser.surname + ", it is great to see you again!";
        } else {
            return "Login failed. Please check your username and password.";
        }
    }
}