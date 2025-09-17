package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.QuizQuestions;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Black-box Testing
// Checking that each question is valid for the practise test to run reliably
/*
 * Assertions Used ---------------
 * assertNotNull - Asserts that the object is not null
 * assertFalse - Asserts that the condition is false
 * assertTrue - Asserts that the condition is true
 * */

class AfricaQuizDataTest {

    @Test
    void testChoicesShouldNotBeEmpty(){
        //Get the practise quesations from AfricaQuizData and store it in list questions
        List<QuizQuestions> questions = AfricaQuizData.getPracticeQuestions();
        //Assert that the box/label should not be empty
        assertFalse(questions.isEmpty(), "Question box should not be empty");

        //For each question, validate structure expected by the Ui
        for (QuizQuestions q: questions) {
            assertNotNull(q.getChoices(), "Choices should not be empty");
        }
    }

    @Test
    void testIfFourMultipleChoiceQuestions(){
        //Get the practise quesations from AfricaQuizData and store it in list questions
        List<QuizQuestions> questions = AfricaQuizData.getPracticeQuestions();
        //Assert that the box/label should not be empty
        assertFalse(questions.isEmpty(), "Question box should not be empty");

        //For each question, validate structure expected by the Ui
        for (QuizQuestions q: questions) {
            assertEquals(4, q.getChoices().size(), "Each question must have 4 MCQ");

        }
    }

    @Test
    void testIfCorrectAnswerIsNotNull(){
        //Get the practise quesations from AfricaQuizData and store it in list questions
        List<QuizQuestions> questions = AfricaQuizData.getPracticeQuestions();
        //Assert that the box/label should not be empty
        assertFalse(questions.isEmpty(), "Question box should not be empty");

        //For each question, validate structure expected by the Ui
        for (QuizQuestions q: questions) {
            assertNotNull(q.getChoices(), "Choices should not be empty");
            assertNotNull(q.getCorrectAnswer(), "Correct answer must not be null");

        }
    }

    @Test
    void countryCodeIsValid(){
        //Get the practise quesations from AfricaQuizData and store it in list questions
        List<QuizQuestions> questions = AfricaQuizData.getPracticeQuestions();

        for (QuizQuestions q: questions){
            assertNotNull(q.getCountryCode(), "Country code must not be empty");
        }
    }

    @Test
    void questionTextIsNotEmpty(){
        //Get the practise quesations from AfricaQuizData and store it in list questions
        List<QuizQuestions> questions = AfricaQuizData.getPracticeQuestions();

        for (QuizQuestions q: questions){
            assertNotNull(q.getQuestionText(), "Question text should not be empty");
            assertTrue(q.getChoices().contains(q.getCorrectAnswer()), "Correct answer must be" +
                    "one of the choices for : " + q.getQuestionText());
        }
    }

    @Test
    void correctAnswerMustBeInTheChoices(){
        //Get the practise quesations from AfricaQuizData and store it in list questions
        List<QuizQuestions> questions = AfricaQuizData.getPracticeQuestions();

        for (QuizQuestions q: questions){
            assertTrue(q.getChoices().contains(q.getCorrectAnswer()), "Correct answer must be" +
                    "one of the choices for : " + q.getQuestionText());
        }
    }
}