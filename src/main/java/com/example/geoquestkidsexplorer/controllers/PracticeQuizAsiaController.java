package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.data.AsiaQuizData;
import com.example.geoquestkidsexplorer.models.QuizQuestions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PracticeQuizAsiaController {

    public static record EvalResult(boolean correct, int scoreDelta, String correctAnswer, String funFact){}

    @FXML private Label questionCounterLabel;
    @FXML private Label scoreLabel;
    @FXML private Label countryCodeLabel;
    @FXML private Label questionLabel;
    @FXML private VBox quizOptionsContainer;
    @FXML private Label feedbackLabel;
    @FXML private Label funFactLabel;
    @FXML private Button nextQuestionButton;

    private List<QuizQuestions> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private ToggleGroup answerToggleGroup;

    @FXML
    public void initialize() {
        questions = AsiaQuizData.getPracticeQuestions();
        Collections.shuffle(questions);

        answerToggleGroup = new ToggleGroup();

        loadQuestion();
    }

    // FOR UNIT TESTING -------
    // I Only added this method to help with my uni testing and I didn't change anything in your code :)
    public EvalResult evaluateSelection(String selectedAnswer, QuizQuestions q){
        if(q == null) return new EvalResult(false, 0, "", "");
        String correct = q.getCorrectAnswer();
        String fact = q.getFunFact() == null? "": q.getFunFact();
        boolean isCorrect = selectedAnswer != null && selectedAnswer.equals(correct);
        int delta = isCorrect ? 1 : 0;
        return new EvalResult(isCorrect, delta, correct, fact);
    }
    // Helper for testing
    public static String formatCounter(int index0Based, int total){
        return String.format("Question %d of %d", index0Based + 1, total);
    }
    //--------------------

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            QuizQuestions currentQuestion = questions.get(currentQuestionIndex);

            // Update labels
            questionCounterLabel.setText(String.format("Question %d of %d", currentQuestionIndex + 1, questions.size()));
            countryCodeLabel.setText(currentQuestion.getCountryCode());
            questionLabel.setText(currentQuestion.getQuestionText());

            // Clear previous state and hide fun facts
            quizOptionsContainer.getChildren().clear();
            feedbackLabel.setText("");
            funFactLabel.setText("");
            funFactLabel.setVisible(false); // Hide fun facts initially
            nextQuestionButton.setVisible(false);

            // Create and populate quiz option tiles (HBoxes)
            for (String choice : currentQuestion.getChoices()) {
                HBox tile = new HBox(10); // Spacing of 10
                tile.getStyleClass().add("quiz-option-tile");

                RadioButton radioButton = new RadioButton();
                radioButton.setToggleGroup(answerToggleGroup);
                radioButton.setDisable(true); // Disable the radio button itself

                Label choiceLabel = new Label(choice);

                tile.getChildren().addAll(radioButton, choiceLabel);

                // Add event handler to the tile itself
                tile.setOnMouseClicked(event -> handleAnswerSelection(tile, radioButton));

                quizOptionsContainer.getChildren().add(tile);
            }
        } else {
            // Quiz is finished
            questionCounterLabel.setText("Quiz Complete!");
            questionLabel.setText("You have finished the practice quiz!");
            quizOptionsContainer.getChildren().clear();
            nextQuestionButton.setVisible(false);
            feedbackLabel.setText("Final Score: " + score);
            funFactLabel.setText("You can now try the Test Mode Quiz!");
            funFactLabel.setVisible(true); // Show fun facts on quiz complete
        }
    }

    private void handleAnswerSelection(HBox selectedTile, RadioButton selectedRadioButton) {
        // Find the selected answer text from the Label in the selected HBox
        Label selectedLabel = (Label) selectedTile.getChildren().get(1);
        String selectedAnswer = selectedLabel.getText();

        String correctAnswer = questions.get(currentQuestionIndex).getCorrectAnswer();
        String funFact = questions.get(currentQuestionIndex).getFunFact();

        // Disable click events on all tiles
        quizOptionsContainer.getChildren().forEach(node -> node.setDisable(true));

        // Show fun fact now that an answer has been selected
        funFactLabel.setText(funFact);
        funFactLabel.setVisible(true);

        // Check if the selected answer is correct and apply styling
        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            scoreLabel.setText(String.valueOf(score));
            feedbackLabel.setText("Awesome! That's correct. 😊");
            feedbackLabel.setTextFill(Color.web("#4caf50"));
            selectedTile.getStyleClass().add("correct-answer");
            selectedRadioButton.setSelected(true);
        } else {
            feedbackLabel.setText("Good try! Keep exploring. 😟");
            feedbackLabel.setTextFill(Color.web("#f44336"));
            selectedTile.getStyleClass().add("incorrect-answer");
            selectedRadioButton.setSelected(true); // Select the incorrect radio button

            // Find and highlight the correct tile as well
            for (Node node : quizOptionsContainer.getChildren()) {
                HBox tile = (HBox) node;
                Label label = (Label) tile.getChildren().get(1);
                if (label.getText().equals(correctAnswer)) {
                    tile.getStyleClass().add("correct-answer");
                    RadioButton correctRb = (RadioButton) tile.getChildren().get(0);
                    correctRb.setSelected(true);
                }
            }
        }
        nextQuestionButton.setVisible(true);
    }

    @FXML
    private void nextQuestion(ActionEvent event) {
        currentQuestionIndex++;
        loadQuestion();
    }

    @FXML
    private void backToGameModes(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/asia.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}