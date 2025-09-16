package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.database.DatabaseManager.CountryQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class QuizController {

    @FXML private Label titleLabel;
    @FXML private Label scoreLabel;
    @FXML private Label feedbackLabel;
    @FXML private ImageView countryImageView;
    @FXML private TextField answerField;

    private String continent = "Oceania";  // default; set via setContinent(...)
    private String currentAnswer;          // country name to check against
    private int totalAsked = 0;
    private int totalCorrect = 0;
    private Stage stage;                   // optional: for Back

    /** Call this immediately after loading the FXML. */
    public void setContinent(String continent) {
        this.continent = continent;
        if (titleLabel != null) titleLabel.setText(continent + " Quiz");
        loadNextQuestion();
    }

    /** Optional: if you want Back button to close the quiz window. */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleSubmit(ActionEvent e) {
        if (currentAnswer == null || currentAnswer.isBlank()) return;

        String user = DatabaseManager.normalize(answerField.getText());
        String target = DatabaseManager.normalize(currentAnswer);

        if (user.equals(target)) {
            totalCorrect++;
            feedbackLabel.setStyle("-fx-text-fill: #16a34a;");
            feedbackLabel.setText("✅ Correct! It is " + currentAnswer + ".");
        } else {
            feedbackLabel.setStyle("-fx-text-fill: #dc2626;");
            feedbackLabel.setText("❌ Not quite. It was " + currentAnswer + ".");
        }
        updateScore();
    }

    @FXML
    private void handleNext(ActionEvent e) {
        loadNextQuestion();
    }

    @FXML
    private void handleBack(ActionEvent e) throws IOException {
        // If this quiz is in a new Stage, just close:
        if (stage != null) {
            stage.close();
            return;
        }
        // Or go back to the homepage in the same window:
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
        Stage s = (Stage) scoreLabel.getScene().getWindow();
        s.getScene().setRoot(root);
        s.setTitle("GeoQuest – Home");
        s.show();
    }

    private void loadNextQuestion() {
        feedbackLabel.setText("");
        answerField.clear();

        CountryQuestion q = DatabaseManager.getRandomCountryByContinent(continent);
        totalAsked++;

        if (q == null) {
            currentAnswer = null;
            countryImageView.setImage(null);
            feedbackLabel.setStyle("-fx-text-fill: #dc2626;");
            feedbackLabel.setText("No image data found for " + continent + ". Populate the BLOBs first.");
            updateScore();
            return;
        }

        currentAnswer = q.countryName;
        countryImageView.setImage(q.image);
        updateScore();
    }

    private void updateScore() {
        scoreLabel.setText(totalCorrect + "/" + totalAsked);
    }
    @FXML
    private void backToGameModes(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/southamerica.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
