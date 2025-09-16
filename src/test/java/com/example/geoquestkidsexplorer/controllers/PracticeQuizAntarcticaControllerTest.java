package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

// PractiseQuizAntarctica Unit Testing ----------
    /*
    No DB Calls only from data package

     Assertions used ---------------
    * assertEquals = Asserts that the expected value is equal to the actual value
    * assertTrue = Asserts that the given boolean is correct
    * assertFalse = Assert that the given boolean is false
    */

class PracticeQuizAntarcticaControllerTest {

    // Controller we are testing
    private PracticeQuizAntarcticaController controller;

    //Fake Sample Questions used by tests
    private List<QuizQuestions> sampleQs;

    private static QuizQuestions q(String code, String text, List<String> choices, String correct, String fact) {
        return new QuizQuestions(code, text, choices, correct, fact);
    }

    //Questions within OceaniaController
    private static List<QuizQuestions> sample() {
        return List.of(
                q("AQ", "What is the largest land animal in Antarctica?",
                        Arrays.asList("Weddell Seal", "Emperor Penguin", "Antarctic Midge", "Leopard Seal"), "Antarctic Midge",
                        "The Antarctic Midge is the largest purely terrestrial animal on the continent, but it is just a tiny, wingless fly."),
                q("AQ", "What is Antarctica's highest point?",
                        Arrays.asList("Mount Erebus", "Mount Vinson", "Mount Kirkpatrick", "Mount Minto"), "Mount Vinson",
                        "Mount Vinson is part of the Ellsworth Mountains and is located on the Sentinel Range."),
                q("AQ", "Antarctica is home to what percentage of the world's ice?",
                        Arrays.asList("10%", "50%", "70%", "90%"), "90%",
                        "If all of Antarctica's ice were to melt, global sea levels would rise by about 60 meters.")
        );
    }
    @BeforeEach
    void setup() {
        controller = new PracticeQuizAntarcticaController();
        sampleQs = sample();
    }

    // tiny helper so tests are picked by index and evaluate the given user choice
    private PracticeQuizAntarcticaController.EvalResult eval(int index, String selected) {
        return controller.evaluateSelection(selected, sampleQs.get(index));
    }

    //Test if no selection is made
    @Test
    void testNoSelectionMade(){
        var r = eval(0, null); // Q0 is correct
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta());
        assertEquals("Antarctic Midge", r.correctAnswer());
    }

    // test for correct choices returning the correct answer/ point
    @Test
    void testCorrectSelectionAddsScore(){
        var r = eval(1, "Mount Vinson"); // Q1
        assertTrue(r.correct());
        assertEquals(1, r.scoreDelta());
        assertEquals("Mount Vinson", r.correctAnswer());
    }

    //Test when a wrong answer is chosen
    @Test
    void testWrongAnswerSelection(){
        var r = eval(2, "10%"); // Q2
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta()); // Score should remain 0, no points added
        assertEquals("90%", r.correctAnswer());
    }

    // Country code should be exactly what we set
    //Length 2 Letters
    @Test
    void testCodeIsCorrect(){
        var q0 = sampleQs.get(0);
        assertEquals("AQ", q0.getCountryCode());     // exact code
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
        assertTrue(q1.getQuestionText().contains("Antarctica's highest point"));
    }
}