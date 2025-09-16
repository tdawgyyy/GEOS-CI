package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

// PractiseQuizAsia Unit Testing ----------
    /*
    No DB Calls only from data package
    * Verify empty fields -> "Please fill in all fields"

     Assertions used ---------------
    * assertEquals = Asserts that the expected value is equal to the actual value
    * assertTrue = Asserts that the given boolean is correct
    * assertFalse = Assert that the given boolean is false
    */

class PracticeQuizAsiaControllerTest {

    // Controller we are testing
    private PracticeQuizAsiaController controller;

    //Fake Sample Questions used by tests
    private List<QuizQuestions> sampleQs;

    private static QuizQuestions q(String code, String text, List<String> choices, String correct, String fact) {
        return new QuizQuestions(code, text, choices, correct, fact);
    }

    //Questions within SouthAmerica
    private static List<QuizQuestions> sample() {
        return List.of(
                q("IN", "What is the capital of India?",
                        Arrays.asList("Mumbai", "New Delhi", "Kolkata", "Chennai"), "New Delhi",
                        "India is the world's largest democracy, with over 1.4 billion people."),
                q("CN", "The Great Wall of China was built to protect against what?",
                        Arrays.asList("Flooding", "Invaders", "Wild animals", "Earthquakes"), "Invaders",
                        "The Great Wall of China is a series of fortifications that stretches for thousands of miles across the northern part of the country."),
                q("JP", "Which city is the capital of Japan?",
                        Arrays.asList("Kyoto", "Osaka", "Tokyo", "Hiroshima"), "Tokyo",
                        "Tokyo is the most populous metropolitan area in the world.")
        );
    }
    @BeforeEach
    void setup() {
        controller = new PracticeQuizAsiaController();
        sampleQs = sample();
    }

    // tiny helper so tests are picked by index and evaluate the given user choice
    private PracticeQuizAsiaController.EvalResult eval(int index, String selected) {
        return controller.evaluateSelection(selected, sampleQs.get(index));
    }


    //Test if no selection is made
    @Test
    void testNoSelectionMade(){
        var r = eval(0, null); // Q0 is correct
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta());
        assertEquals("New Delhi", r.correctAnswer());
    }

    // test for correct choices returning the correct answer/ point
    @Test
    void testCorrectSelectionAddsScore(){
        var r = eval(1, "Invaders"); // Q1
        assertTrue(r.correct());
        assertEquals(1, r.scoreDelta());
        assertEquals("Invaders", r.correctAnswer());
    }

    //Test when a wrong answer is chosen
    @Test
    void testWrongAnswerSelection(){
        var r = eval(2, "Osaka"); // Q2
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta()); // Score should remain 0, no points added
        assertEquals("Tokyo", r.correctAnswer());
    }

    // Country code should be exactly what we set
    //Length 2 Letters
    @Test
    void testCodeIsCorrect(){
        var q0 = sampleQs.get(0);
        assertEquals("IN", q0.getCountryCode());     // exact code
        assertEquals(2, q0.getCountryCode().length());
        assertEquals(q0.getCountryCode(), q0.getCountryCode().toUpperCase()); // uppercase
    }


    //Test that the test box exist and says the correct data
    @Test
    void testTextBoxQuestionWorks(){
        var q1 = sampleQs.get(1);
        assertNotNull(q1.getQuestionText());
        assertFalse(q1.getQuestionText().isBlank());
        //Fun fact contains a key phrase
        assertTrue(q1.getQuestionText().contains("Great Wall of China was built"));
    }

}