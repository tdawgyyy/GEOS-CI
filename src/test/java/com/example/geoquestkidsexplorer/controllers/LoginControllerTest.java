package com.example.geoquestkidsexplorer.controllers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LoginControllerTest {

    // Registration Unit Testing ----------
    /*
    No DB Calls
    * Verify empty fields -> "Please fill in all fields"
    * Verify Password Mismatch -> "Passwords do not match"
    * Avatar not chosen -> "Please pick an avatar"

     Assertions used ---------------
    * assertEquals = Asserts that the expected value is equal to the actual value
    * assertNull = Asserts that the object is null
    */

    private final LoginController controller = new LoginController();

    // valid username = no error
    @Test
    void testValidUsername(){
        // Calls the public registrationInput from Login controller into error
        String user = controller.validateRegistrationInputs(
                "wizard01","wizard01@domain.com","Student","wizard",
                "wizard","👧 Explorer Girl"
        );
        assertNull(user); // Asserts condition is null
    }

    //Blank username counts as missing field
    @Test
    void  testInvalidUsername(){
        String err = controller.validateRegistrationInputs( "","user@domain.com",
                "student","pass","pass","👧 Explorer Girl");
        assertEquals("Username is blank or invalid", err);
    }

    // Valid email with valid everything means no error
    @Test
    void testCorrectEmail(){
        String err = controller.validateRegistrationInputs(
                "alice","alice@example.com","Student",
                "pass123","pass123","👦 Explorer Boy"
        );
        assertNull(err);
    }

    // Blank email shows missing fields message
    @Test
    void testInvalidEmail(){
            String err = controller.validateRegistrationInputs(
                    "alice","", "Student",
                    "pass123","pass123","👦 Explorer Boy"
            );
            assertEquals("Email is blank or invalid", err);
    }

    // No error shows for correct password matches
    @Test
    void testPasswordMatches(){
        // Calls the public registrationInput from Login controller into error
        String error = controller.validateRegistrationInputs("alice","a@example.com","Student",
                "password123","password123","👧 Explorer Girl");
        assertNull(error);
    }

    // Shows errors when passwords do not match
    @Test
    void showsErrorWhenPasswordsDoNotMatch(){
        String error = controller.validateRegistrationInputs("alice","a@example.com","Student",
                "password123","differrent","👧 Explorer Girl");
        assertEquals("Passwords do not match", error);
    }

    // Test for Errors when Fields are Blank
    @Test
    void showErrorWhenFieldsAreBlank(){
        // Calls the public registrationInput from Login controller into error
        String error = controller.validateRegistrationInputs(
                "","","","","",""
        );
        assertEquals("Please fill in all the fields", error);
    }

    //In the registration, show error when an Avatar is not chosen
    @Test
    void showErrorWhenAvatarNotChosen(){
        String error = controller.validateRegistrationInputs(
                "bob","b@example.com","Teacher","password123",
                "password123", ""
        );
        assertEquals("Please pick an avatar", error);
    }

    //Asserts when an object is null when all inputs are valid
    //If everything is filled in correctly , the system does not complain
    @Test
    void returnsNullWhenAllInputsValid(){
        String error = controller.validateRegistrationInputs(
                "carol","c@example.com","Teacher","password123",
                "password123","👦 Explorer Boy"
        );
        assertNull(error, "Expected no validation error for valid inputs");
    }

}