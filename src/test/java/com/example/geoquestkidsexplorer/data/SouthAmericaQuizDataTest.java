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

class SouthAmericaQuizDataTest {

    @Test
    void questionsHasFourChoices(){
        //Get the practise quesations from SouthAmericaQuizData and store it in list questions
        List<QuizQuestions> questions = SouthAmericaQuizData.getPracticeQuestions();

        //Assert that the box/label should not be empty
        assertFalse(questions.isEmpty(), "Question box should not be empty");

        //For each question, validate structure expected by the Ui
        for (QuizQuestions q: questions) {

            assertNotNull(q.getChoices(), "Choices should not be empty");
            assertEquals(4, q.getChoices().size(), "Each question must have 4 MCQ");
            assertNotNull(q.getCorrectAnswer(), "Correct answer must not be null");

        }
    }

    @Test
    void questionsHasValidAnswers(){
        //Get the practise quesations from SouthAmericaQuizData and store it in list questions
        List<QuizQuestions> questions = SouthAmericaQuizData.getPracticeQuestions();

        for (QuizQuestions q: questions){
            assertNotNull(q.getCountryCode(), "Country code must not be empty");
            assertNotNull(q.getQuestionText(), "Question text should not be empty");
            assertTrue(q.getChoices().contains(q.getCorrectAnswer()), "Correct answer must be" +
                    "one of the choices for : " + q.getQuestionText());
        }
    }
}