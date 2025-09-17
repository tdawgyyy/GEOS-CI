package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.QuizQuestions;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

// PractiseQuizSouthAmerica Unit Testing ----------
    /*
    No DB Calls only from data package
     Assertions used ---------------
    * assertEquals = Asserts that the expected value is equal to the actual value
    * assertTrue = Asserts that the given boolean is correct
    * assertFalse = Assert that the given boolean is false
    */

class PracticeQuizSouthAmericaControllerTest {

    // Controller we are testing
    private PracticeQuizSouthAmericaController controller;

    //Fake Sample Questions used by tests
    private List<QuizQuestions> sampleQs;

    private static QuizQuestions q(String code, String text, List<String> choices, String correct, String fact) {
        return new QuizQuestions(code, text, choices, correct, fact);
    }

    //Questions within SouthAmerica
    private static List<QuizQuestions> sample() {
        return List.of(
                q("BR", "Which country is home to the Amazon Rainforest, the largest tropical rainforest in the world?",
                        Arrays.asList("Argentina", "Peru", "Brazil", "Colombia"), "Brazil",
                        "The Amazon River, which flows through Brazil, is the world's largest river by discharge volume."),
                q("PE", "Which famous lost city of the Incas is located in the Andes Mountains of Peru?",
                        Arrays.asList("Cusco", "Chan Chan", "Lima", "Machu Picchu"), "Machu Picchu",
                        "Machu Picchu is considered one of the 'New Seven Wonders of the World' and was built in the 15th century."),
                q("AR", "What popular dance style originated in the city of Buenos Aires, Argentina?",
                        Arrays.asList("Salsa", "Tango", "Samba", "Flamenco"), "Tango",
                        "The tango is a beautiful and passionate dance that originated in the late 19th century in Argentina and Uruguay.")
        );
    }
    @BeforeEach
    void setup() {
        controller = new PracticeQuizSouthAmericaController();
        sampleQs = sample();
    }

    // tiny helper so tests are picked by index and evaluate the given user choice
    private PracticeQuizSouthAmericaController.EvalResult eval(int index, String selected) {
        return controller.evaluateSelection(selected, sampleQs.get(index));
    }


    //Test if no selection is made
    @Test
    void testNoSelectionMade(){
        var r = eval(0, null); // Q0: Canberra is correct
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta());
        assertEquals("Brazil", r.correctAnswer());
    }

    // test for correct choices returning the correct answer/ point
    @Test
    void testCorrectSelectionAddsScore(){
        var r = eval(1, "Machu Picchu"); // Q1
        assertTrue(r.correct());
        assertEquals(1, r.scoreDelta());
        assertEquals("Machu Picchu", r.correctAnswer());
    }

    //Test when a wrong answer is chosen
    @Test
    void testWrongAnswerSelection(){
        var r = eval(2, "Samba"); // Q2
        assertFalse(r.correct());
        assertEquals(0, r.scoreDelta()); // Score should remain 0, no points added
        assertEquals("Tango", r.correctAnswer());
    }

    // Country code should be exactly what we set
    //Length 2 Letters
    @Test
    void testCodeIsCorrect(){
        var q0 = sampleQs.get(0);
        assertEquals("BR", q0.getCountryCode());     // exact code
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
        assertTrue(q1.getQuestionText().contains("lost city of the Incas"));
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