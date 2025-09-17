package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.MockFlashcardDB;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FlashCardsTest {

    private FlashcardsController controller;

    // Run javafx application thread/toolkit
    @BeforeAll
    static void startUp() {
        // catch Illegal exception can be ignored
        try { Platform.startup(() -> {}); } catch (IllegalStateException ignored) {}
    }

    @BeforeEach
    void setup() {
        controller = new FlashcardsController();
        controller.testAttachNodes(new Label(), new ImageView(), new Label(), new StackPane());
    }

    // tiny 1x1 image using pure JavaFX (no AWT/ImageIO)
    private static Image tinyImage() {
        return new WritableImage(1, 1);
    }

    // testing if CorrectImage Loads
    @Test
    void testCorrectImageLoads() {
        var mock = new MockFlashcardDB().add("Australia", tinyImage());
        mock.fillInternalCountries(controller);
        ImageView imgView = (ImageView) getField(controller, "countryImageView");

        assertNotNull(imgView.getImage(), "Image should be set for front side");
        assertTrue(imgView.isVisible());
    }

    // testing correct image loading through using MockFlashCard
    @Test
    void testCorrectImageForNewZealandLoad() {
        var mock = new MockFlashcardDB()
                .add("New Zealand", tinyImage());
        mock.fillInternalCountries(controller);

        // Flip AU to back shows country name
        controller.testNext();
        Label back = (Label) getField(controller, "backTextLabel");
        // Advance to NZ to front with image
        controller.testNext();
        ImageView imgView = (ImageView) getField(controller, "countryImageView");
        assertNotNull(imgView.getImage()); //Image should not be null
        assertTrue(imgView.isVisible()); // Country Image is Visible
        assertFalse(back.isVisible()); //Back label is not visible
    }

    @Test
    void testCorrectImageForAustraliaLoad() {
        var mock = new MockFlashcardDB()
                .add("Australia", null);
        mock.fillInternalCountries(controller);

        // Flip AU to back shows country name
        controller.testNext();
        Label back = (Label) getField(controller, "backTextLabel");
        //assert true if condition starts with ...
        assertTrue(back.getText().startsWith("This is Australia"));

    }

    //Test if the no countries message display
    @Test
    void testIfItShowsNoCountriesMessage() {
        new MockFlashcardDB().fillInternalCountries(controller);
        Label back = (Label) getField(controller, "backTextLabel");

        //Assert if expected message equals to Label message in controller
        assertEquals("Sorry, no countries yet.", back.getText());
    }

    //helper to read private fields within Flash card
    private static Object getField(FlashcardsController target, String name) {
        try {
            //Retrieves a private field
            var field = target.getClass().getDeclaredField(name);
            //Allows access to private members of the class
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }
}