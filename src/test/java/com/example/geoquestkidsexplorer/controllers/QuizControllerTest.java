package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager.CountryQuestion;
import com.example.geoquestkidsexplorer.database.QuizDataSource;

// Import JavaFx controls in order to mimick logic controls
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// This test uses a mock that is created through mock objects
//That siimulates the behaviour of real object

@ExtendWith(MockitoExtension.class)
class QuizControllerTest {
/*
    private QuizController controller;

    //Mocks
    private QuizDataSource dataSource;
    private Label titleLabel;
    private Label scoreLabel;
    private Label feedbackLabel;
    //private ImageView countryImageView;
    private TextField answerField;

    @BeforeAll
    static void startUp(){

        try{
            Platform.startup(() ->{});
        }
        catch (IllegalStateException ignored){}
    }

    @BeforeEach
    void setup(){
        controller = new QuizController();
        dataSource = mock(QuizDataSource.class);

        titleLabel = new Label();
        scoreLabel = new Label();
        feedbackLabel = new Label();
        ImageView countryImageView = new ImageView();
        answerField = new TextField();

        controller.setDataSource(dataSource);
        controller.uiForTest(titleLabel, scoreLabel, feedbackLabel, countryImageView ,answerField);
    }

    // Next question and load
    private void loadQuestion(String continent, String countryOrNull){
        when(dataSource.getRandomCountryByContinent(continent)).thenReturn(countryOrNull == null ?
                null : new CountryQuestion(countryOrNull, null,null));
        controller.setContinent(continent);
    }

    // Answer and submit
    private void answer(String userText, String correctAnswer){

        answerField.setText(userText);
        // Normalise both user input and target to lowercase
        when(dataSource.normalise(userText)).thenReturn(userText.toLowerCase().trim());
        when(dataSource.normalise(correctAnswer)).thenReturn(correctAnswer.toLowerCase().trim());
        controller.handleSubmit(null);
    }

    @Test
    void testIncorrectAnswers(){
        loadQuestion("Oceania", "Fiji");
        answer("New Caledonia","Fiji");

        assertTrue(feedbackLabel.getText().contains("Not quite"));
    }

    @Test
    void testIncorrectAnswerDisplaysScore(){
        loadQuestion("Oceania", "Fiji");
        answer("New Caledonia","Fiji");

        assertEquals("0/1", scoreLabel.getText());
    }

    @Test
    void testCorrectAnswer(){
        loadQuestion("Oceania", "Fiji");
        answer("Fiji","Fiji");

        assertTrue(feedbackLabel.getText().contains("Correct"));
    }

    @Test
    void testScoresUpdate(){
        //Correct Answer
        loadQuestion("Oceania", "Fiji");
        answer("Fiji","Fiji");
        //Load next question and mark it incorrect
        loadQuestion("Oceania", "Fiji");
        answer("New Caledonia","Fiji");

        assertEquals("1/2", scoreLabel.getText());
    }

    @Test
    void testTitleDisplays(){
        loadQuestion("Oceania", "Vanuatu");

        assertEquals("Oceania Quiz", titleLabel.getText());
    }

    @Test
    void testNoImageData(){
        //DB returns no row for continent
        loadQuestion("Oceania", null);

        var feedback = org.mockito.ArgumentCaptor.forClass(String.class);
        assertTrue(feedbackLabel.getText().contains("No image data"));
    }
*/
}