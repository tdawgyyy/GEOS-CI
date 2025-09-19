package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.QuizQuestions;
import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView; // Import ImageView
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PracticeQuizOceaniaController {

    public static record EvalResult(boolean correct, int scoreDelta, String correctAnswer, String funFact){}
    @FXML private Label questionNumberLabel;
    @FXML private Label scoreLabel;
    @FXML private ImageView countryImageView; // Add this FXML annotation for the ImageView
    @FXML private Label questionLabel;
    @FXML private ToggleGroup answerGroup;
    @FXML private RadioButton option1, option2, option3, option4;
    @FXML private VBox feedbackContainer;
    @FXML private Label feedbackMessageLabel; //Change with feedbackMessageLabel.
    @FXML private Label funFactLabel;
    @FXML private Button nextQuestionButton;

    // Change the type to hold the new PracticeQuizQuestion objects
    private List<PracticeQuizQuestions> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    @FXML
    public void initialize() {
        // Attach listener to the ToggleGroup once, at the beginning.
        // It's already linked from the FXML via the @FXML annotation.
        answerGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !((RadioButton) newValue).isDisable()) {
                checkAnswer((RadioButton) newValue);
            }
        });

        // Generate and store all questions at the start
        final int numberOfQuestions = 25;
        for (int i = 0; i < numberOfQuestions; i++) {
            PracticeQuizQuestions q = DatabaseManager.getPracticeQuizQuestion("Oceania");
            if (q != null) {
                questions.add(q);
            }
        }

        // Hide feedback container and next question button initially
        feedbackContainer.setVisible(false);
        nextQuestionButton.setVisible(false);

        // Load the first question
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


    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {

            // Re-show radio buttons for the new question
            option1.setVisible(true);
            option2.setVisible(true);
            option3.setVisible(true);
            option4.setVisible(true);
            option1.setToggleGroup(answerGroup);
            option2.setToggleGroup(answerGroup);
            option3.setToggleGroup(answerGroup);
            option4.setToggleGroup(answerGroup);

            PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);

            // Update labels
            //questionCounterLabel.setText(String.format("Question %d of %d", currentQuestionIndex + 1, questions.size()));
            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            countryImageView.setImage(currentQuestion.countryImage());
            questionLabel.setText(currentQuestion.questionText());

            // Shuffle the answer options and set them to the radio buttons
            List<String> options = new java.util.ArrayList<>(currentQuestion.getChoices());
            Collections.shuffle(options);
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(options.get(3));

            // Reset radio button styles and selections
            resetStyles();
            answerGroup.selectToggle(null);
            disableRadioButtons(false);

            funFactLabel.setText("");
            feedbackContainer.setVisible(false);
            nextQuestionButton.setVisible(false);
        } else {
            // --- End of Quiz Logic ---

            // Hide the radio buttons and their tiles
            option1.setVisible(false);
            option2.setVisible(false);
            option3.setVisible(false);
            option4.setVisible(false);

            // Detach radio buttons from the toggle group to prevent selection issues
            option1.setToggleGroup(null);
            option2.setToggleGroup(null);
            option3.setToggleGroup(null);
            option4.setToggleGroup(null);

            // Update UI to show quiz complete message
            questionNumberLabel.setText("Quiz Complete!");
            questionLabel.setText("You have finished the practice quiz!");
            // The `feedbackContainer` is now where the final score is shown, so make it visible
            feedbackContainer.setVisible(true);
            feedbackMessageLabel.setText("Final Score: " + score);
            funFactLabel.setText("You can now try the Test Mode Quiz!");
            funFactLabel.setVisible(true); // Show fun facts on quiz complete
            nextQuestionButton.setVisible(false);

            resetStyles();
        }
    }

    // This method's ONLY job is to select the RadioButton when the user clicks the tile.
    // The ToggleGroup listener in to initialize() method will then handle the rest of the logic.
    @FXML
    private void handleTileSelection(MouseEvent event) {
        VBox clickedTile = (VBox) event.getSource();

        for (Node node : clickedTile.getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton selectedRadioButton = (RadioButton) node;
                // Check if the button is not already selected and not disabled
                if (!selectedRadioButton.isSelected() && !selectedRadioButton.isDisable()) {
                    selectedRadioButton.setSelected(true);
                    // The listener in initialize() will now call checkAnswer()
                }
                break;
            }
        }
    }

    private void checkAnswer(RadioButton selectedRadioButton) {
        // Find the selected answer text from the Label in the selected HBox
        PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String selectedAnswer = selectedRadioButton.getText();

        boolean isCorrect = selectedAnswer.equals(currentQuestion.getCorrectAnswer());

        if (isCorrect) {
            score++;
            scoreLabel.setText(String.valueOf(score));
            //scoreLabel.setText("Score: " + score);
            selectedRadioButton.setStyle("-fx-background-color: #a5d6a7; -fx-background-radius: 5;"); // Light green
            feedbackMessageLabel.setText("Awesome! That's a correct answer. 😊");
            feedbackMessageLabel.setTextFill(Color.web("#4caf50"));
        } else {
            // Highlight the incorrect answer in light red
            selectedRadioButton.setStyle("-fx-background-color: #ffccbc; -fx-background-radius: 5;"); // Light red

            // Find and highlight the correct answer in light green
            for (RadioButton rb : new RadioButton[]{option1, option2, option3, option4}) {
                if (rb.getText().equals(currentQuestion.getCorrectAnswer())) {
                    rb.setStyle("-fx-background-color: #a5d6a7; -fx-background-radius: 5;"); // Light green
                }
            }
            feedbackMessageLabel.setText("Good try! Keep exploring. 😟");
            feedbackMessageLabel.setTextFill(Color.web("#f44336"));
        }
        // Disable radio buttons after an answer is selected
        disableRadioButtons(true);

        // Display the fun fact for the current question
        funFactLabel.setText("Fun Facts about Africa!\n" + currentQuestion.getFunFact());
        String funFact = questions.get(currentQuestionIndex).getFunFact();
        // Show fun fact now that an answer has been selected
        funFactLabel.setText(funFact);
        funFactLabel.setVisible(true);

        // Show feedback container and next button
        feedbackContainer.setVisible(true);
        nextQuestionButton.setVisible(true);
    }

    private void resetStyles() {
        option1.setStyle("");
        option2.setStyle("");
        option3.setStyle("");
        option4.setStyle("");
    }

    private void disableRadioButtons(boolean disable) {
        option1.setDisable(disable);
        option2.setDisable(disable);
        option3.setDisable(disable);
        option4.setDisable(disable);
    }

    @FXML
    private void handleNextQuestion(ActionEvent event) {
        currentQuestionIndex++;
        loadQuestion();
    }
    private void loadScene(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void backToGameModes(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/oceania.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}