package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

// PractiseQuizOceania Unit Testing ----------
    /*
    No DB Calls only from data package

     Assertions used ---------------
    * assertEquals = Asserts that the expected value is equal to the actual value
    * assertTrue = Asserts that the given boolean is correct
    * assertFalse = Assert that the given boolean is false
    */

class PracticeQuizOceaniaControllerTest {

    // Controller we are testing
    private PracticeQuizOceaniaController controller;

    //Fake Sample Questions used by tests
    private List<QuizQuestions> sampleQs;

    private static QuizQuestions q(String code, String text, List<String> choices, String correct, String fact) {
        return new QuizQuestions(code, text, choices, correct, fact);
    }

    //Questions within OceaniaController
    private static List<QuizQuestions> sample() {
        return List.of(
                q("AU", "What is the capital city of Australia?",
                        Arrays.asList("Sydney", "Melbourne", "Canberra", "Brisbane"), "Canberra",
                        "Canberra is a planned city, not just a large metropolis that grew over time."),
                q("AU", "Which country is home to the Great Barrier Reef?",
                        Arrays.asList("New Zealand", "Fiji", "Australia", "Papua New Guinea"), "Australia",
                        "The Great Barrier Reef is so large it can be seen from outer space."),
                q("PG", "What is the largest island in Oceania?",
                        Arrays.asList("Tasmania", "New Guinea", "South Island", "Borneo"), "New Guinea",
                        "New Guinea is the world's second-largest island, after Greenland.")
        );
    }
    @BeforeEach
    void setup() {
        controller = new PracticeQuizOceaniaController();
        sampleQs = sample();
    }

    // tiny helper so tests are picked by index and evaluate the given user choice
    private PracticeQuizOceaniaController.EvalResult eval(int index, String selected) {
        return controller.evaluateSelection(selected, sampleQs.get(index));
    }


    //Test if no selection is made
    @Test
    void testNoSelectionMade(){
        var r = eval(0, null); // Q0: Canberra is correct
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta());
        assertEquals("Canberra", r.correctAnswer());
    }

    // test for correct choices returning the correct answer/ point
    @Test
    void testCorrectSelectionAddsScore(){
        var r = eval(1, "Australia"); // Q1
        assertTrue(r.correct());
        assertEquals(1, r.scoreDelta());
        assertEquals("Australia", r.correctAnswer());
    }

    //Test when a wrong answer is chosen
    @Test
    void testWrongAnswerSelection(){
        var r = eval(2, "Tasmania"); // Q2
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta()); // Score should remain 0, no points added
        assertEquals("New Guinea", r.correctAnswer());
    }

    // Country code should be exactly what we set
    //Length 2 Letters
    @Test
    void testCodeIsCorrect(){
        var q0 = sampleQs.get(0);
        assertEquals("AU", q0.getCountryCode());     // exact code
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
        assertTrue(q1.getQuestionText().contains("Great Barrier Reef"));
    }

    @Test
    void testFunFactDisplays(){
        // Correct path
        var rCorrect = eval(1, "Australia");
        assertNotNull(rCorrect.funFact());
        assertFalse(rCorrect.funFact().isBlank());

        // Wrong path still returns the fun fact
        var rWrong = eval(2, "Tasmania");
        assertNotNull(rWrong.funFact());
        assertFalse(rWrong.funFact().isBlank());
    }
}