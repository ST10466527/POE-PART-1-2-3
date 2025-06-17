package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void testCheckUsername_Valid() {
        assertTrue(Main.CheckUsername("user_1"));
    }

    @Test
    void testCheckUsername_Invalid() {
        assertFalse(Main.CheckUsername("user"));
    }

    @Test
    void testCheckPasswordComplexity_Valid() {
        assertTrue(Main.CheckPasswordComplexity("Passw0rd!"));
    }

    @Test
    void testCheckPasswordComplexity_Invalid() {
        assertFalse(Main.CheckPasswordComplexity("password"));
    }

    @Test
    void testCheckCellphoneNumber_Valid() {
        assertTrue(Main.CheckCellphoneNumberComplexity("+27821234567"));
    }

    @Test
    void testCheckCellphoneNumber_Invalid() {
        assertFalse(Main.CheckCellphoneNumberComplexity("0821234567"));
    }

    @Test
    void testRegisterUser_Success() {
        String result = Main.registerUser("user_1", "Passw0rd!", "+27821234567", "John", "Doe");
        assertEquals("User has been successfully registered.", result);
    }

    @Test
    void testRegisterUser_InvalidUsername() {
        String result = Main.registerUser("user", "Passw0rd!", "+27821234567", "John", "Doe");
        assertEquals("Username is incorrectly formatted.", result);
    }
}