//Import packages:
package com.example.geoquestkidsexplorer.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TestModeAntarcticaController {

//FXML variables, linked to FXML file
    @FXML public Label timerLabel;
    @FXML public Label scoreLabel;
    @FXML public ImageView countryImageView;
    @FXML public Label questionLabel;
    @FXML public VBox quizOptionsContainer;
    @FXML public TextField answerField;
    @FXML public Button nextQuestionButton;


    public void backToGameModes(ActionEvent actionEvent) {

    }

    public void nextQuestion(ActionEvent actionEvent) {

    }
}
