package org.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    private Login login;

    @Before
    public void setUp() {
        login = new Login();
    }

    // Username Tests
    @Test
    public void testValidUsername() {
        assertTrue(login.checkUsername("ab_1"));
    }

    @Test
    public void testUsernameTooLong() {
        assertFalse(login.checkUsername("abc_123"));  // more than 5 characters
    }

    @Test
    public void testUsernameMissingUnderscore() {
        assertFalse(login.checkUsername("abcde"));
    }

    // Password Tests
    @Test
    public void testValidPassword() {
        assertTrue(login.checkPassword("Password1!"));
    }

    @Test
    public void testPasswordMissingUppercase() {
        assertFalse(login.checkPassword("password1!"));
    }

    @Test
    public void testPasswordMissingDigit() {
        assertFalse(login.checkPassword("Password!"));
    }

    @Test
    public void testPasswordMissingSpecialChar() {
        assertFalse(login.checkPassword("Password1"));
    }

    @Test
    public void testPasswordTooShort() {
        assertFalse(login.checkPassword("P1!a")); // < 8 characters
    }

    // Cellphone Tests
    @Test
    public void testValidCellNumber() {
        assertTrue(login.checkCell("+27123456789"));
    }

    @Test
    public void testCellMissingPlus27() {
        assertFalse(login.checkCell("0712345678"));
    }

    @Test
    public void testCellTooLong() {
        assertFalse(login.checkCell("+2712345678900"));  // more than 12 chars
    }
}