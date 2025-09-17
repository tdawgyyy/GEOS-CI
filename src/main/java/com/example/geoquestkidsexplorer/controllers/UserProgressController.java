package com.example.geoquestkidsexplorer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UserProgressController {
    @FXML
    private Label avatarLabel;
    @FXML
    private Label continentsUnlockedLabel;
    @FXML
    private Label levelsCompletedLabel;
    @FXML
    private Label perfectScoresLabel;
    @FXML
    private Label correctAnswersLabel;
    @FXML
    private Label levelsCompletedTileLabel;
    @FXML
    private Label continentsUnlockedTileLabel;

    /**
     * Sets the profile data (avatar and stats) received from the main controller.
     * This method is called by the MainController when the user progress page is loaded.
     *
     * @param explorerAvatar The user's selected avatar emoji.
     * @param continentsUnlocked The number of continents unlocked.
     * @param perfectScores The number of perfect scores.
     * @param correctAnswers The number of 100% correct answers.
     */
    public void setProfileData(String explorerName, String explorerAvatar, int continentsUnlocked, int perfectScores, int correctAnswers) {
        // Update the avatar label with the user's selected avatar
        avatarLabel.setText(explorerAvatar);

        int totalLevelsCompleted = perfectScores + correctAnswers; // Placeholder logic
        continentsUnlockedLabel.setText("Continents Unlocked: " + continentsUnlocked + "/6");
        levelsCompletedLabel.setText("Total Levels Completed: " + totalLevelsCompleted + "/54");

        // Update the stats tiles with the provided data
        perfectScoresLabel.setText("" + perfectScores);
        correctAnswersLabel.setText("" + correctAnswers);
        levelsCompletedTileLabel.setText("" + totalLevelsCompleted);
        // Update the progress labels.
        continentsUnlockedTileLabel.setText("" + continentsUnlocked);
    }

    /**
     * Handles the logout action.
     * It switches the scene back to the main menu or login screen.
     *
     * @param event The ActionEvent from the button click.
     * @throws IOException if the FXML file cannot be loaded.
     */
    /*@FXML
    public void handleLogoutButtonAction(ActionEvent event) throws IOException {
        // Load the new FXML scene (e.g., Login.fxml)
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/loginview.fxml"));
        Scene newScene = new Scene(root);

        // Get the current stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene on the stage and show it
        stage.setScene(newScene);
        stage.show();
    }*/

    @FXML
    private void handleLogoutButtonAction (ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/loginview.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
