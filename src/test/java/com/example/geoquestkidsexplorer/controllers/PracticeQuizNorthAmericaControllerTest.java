package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

// PractiseQuizNorthAmerica Unit Testing ----------
    /*
    No DB Calls only from data package

     Assertions used ---------------
    * assertEquals = Asserts that the expected value is equal to the actual value
    * assertTrue = Asserts that the given boolean is correct
    * assertFalse = Assert that the given boolean is false
    */

class PracticeQuizNorthAmericaControllerTest {

    // Controller we are testing
    private PracticeQuizNorthAmericaController controller;

    //Fake Sample Questions used by tests
    private List<QuizQuestions> sampleQs;

    private static QuizQuestions q(String code, String text, List<String> choices, String correct, String fact) {
        return new QuizQuestions(code, text, choices, correct, fact);
    }

    //Questions within SouthAmerica
    private static List<QuizQuestions> sample() {
        return List.of(
                q("US", "Which country is home to the Grand Canyon, one of the world's most impressive natural formations?",
                        Arrays.asList("Mexico", "Canada", "United States", "Cuba"), "United States",
                        "The Grand Canyon is over a mile deep and was carved by the Colorado River."),
                q("CA", "What is the largest country in North America by land area?",
                        Arrays.asList("United States", "Mexico", "Canada", "Greenland"), "Canada",
                        "Canada is the second-largest country in the world, with a vast and diverse landscape."),
                q("MX", "Which country is famous for its ancient Mayan and Aztec pyramids?",
                        Arrays.asList("United States", "Cuba", "Mexico", "Panama"), "Mexico",
                        "The Pyramid of Kukulcan in Chichen Itza is one of the most famous Mayan ruins in Mexico.")
        );
    }
    @BeforeEach
    void setup() {
        controller = new PracticeQuizNorthAmericaController();
        sampleQs = sample();
    }

    // tiny helper so tests are picked by index and evaluate the given user choice
    private PracticeQuizNorthAmericaController.EvalResult eval(int index, String selected) {
        return controller.evaluateSelection(selected, sampleQs.get(index));
    }


    //Test if no selection is made
    @Test
    void testNoSelectionMade(){
        var r = eval(0, null); // Q0 is correct
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta());
        assertEquals("United States", r.correctAnswer());
    }

    // test for correct choices returning the correct answer/ point
    @Test
    void testCorrectSelectionAddsScore(){
        var r = eval(1, "Canada"); // Q1
        assertTrue(r.correct());
        assertEquals(1, r.scoreDelta());
        assertEquals("Canada", r.correctAnswer());
    }

    //Test when a wrong answer is chosen
    @Test
    void testWrongAnswerSelection(){
        var r = eval(2, "Cuba"); // Q2
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta()); // Score should remain 0, no points added
        assertEquals("Mexico", r.correctAnswer());
    }

    // Country code should be exactly what we set
    //Length 2 Letters
    @Test
    void testCodeIsCorrect(){
        var q0 = sampleQs.get(0);
        assertEquals("US", q0.getCountryCode());     // exact code
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
        assertTrue(q1.getQuestionText().contains("largest country in North America"));
    }

}