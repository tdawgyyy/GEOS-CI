package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HomePageController {

    @FXML
    private Label avatarLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label subWelcomeLabel;

    @FXML
    private SidebarController mySidebarController; // FXML loader automatically injects this.
    // A private field to hold the explorer's avatar string
    private String explorerAvatar;
    private String explorerName;

    /**
     * The initialize method should not contain business logic.
     * It's only for setting up UI elements.
     */
    @FXML
    public void initialize() {
        // Get user data directly from the UserSession
        String explorerName = UserSession.getUsername();
        String explorerAvatar = UserSession.getAvatar();

        if (explorerName != null && !explorerName.isEmpty()) {
            welcomeLabel.setText("Welcome back, " + explorerName + "!");
            avatarLabel.setText(explorerAvatar != null ? explorerAvatar : "");
            subWelcomeLabel.setText("Ready to continue your adventure?");

            // Ensure the sidebar also gets the data
            if (mySidebarController != null) {
                mySidebarController.setProfileData(explorerName, explorerAvatar);
            }
        } else {
            // This handles cases where no one is logged in
            welcomeLabel.setText("Welcome, Explorer!");
            avatarLabel.setText("🙂");
            subWelcomeLabel.setText("Ready for a new adventure!");
        }
    }

    /**
     * This is the new, centralized method for setting all user data.
     * It is called by the LoginController after a successful login.
     */
    public void setProfileData(String username, String avatar) {
        // Set the labels for the HomePageController
        this.welcomeLabel.setText("Welcome back, " + username + "!");
        this.avatarLabel.setText(avatar);
        this.subWelcomeLabel.setText("Ready to continue your adventure?");

        // Now, pass the same data to the SidebarController.
        // The FXML loader ensures mySidebarController is not null here.
        if (mySidebarController != null) {
            mySidebarController.setProfileData(username, avatar);
        }
    }

    /**
     * Handles the "Start Your New Adventure!" button action.
     * Note: This method should be called from the HomePageController itself.
     */
    private void openQuiz(String continent) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/geoquestkidsexplorer/quiz_view.fxml"));
            Parent root = loader.load();

            // Show quiz in a new window (keeps Home open)
            Stage quizStage = new Stage();
            quizStage.setTitle(continent + " Quiz");
            quizStage.setScene(new Scene(root, 800, 600));

            // Pass data into the quiz controller
            QuizController controller = loader.getController();
            controller.setStage(quizStage);       // so Back can close this window
            controller.setContinent(continent);   // loads the first question

            quizStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startNewAdventure(ActionEvent event) {
        openQuiz("South America"); // or choose based on the user’s level/last choice
    }


    @FXML
    private void handleOceaniaClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource(
                "/com/example/geoquestkidsexplorer/oceania.fxml")));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }


    @FXML
    private void handleAntarcticaClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource(
                "/com/example/geoquestkidsexplorer/antarctica.fxml")));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // no resize, CSS stays the same
        stage.getScene().setRoot(root);
        stage.show();
    }

    @FXML
    private void handleAfricaClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource(
                "/com/example/geoquestkidsexplorer/africa.fxml")));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // no resize, CSS stays the same
        stage.getScene().setRoot(root);
        stage.show();
    }

    @FXML
    private void handleAsiaClick(ActionEvent event) throws  IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource(
                "/com/example/geoquestkidsexplorer/asia.fxml")));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

    @FXML
    private void handleSouthAmericaClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource(
                "/com/example/geoquestkidsexplorer/southamerica.fxml")));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
        System.out.println("South America has been clicked");
    }

    @FXML
    private void handleNorthAmericaClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource(
            "/com/example/geoquestkidsexplorer/northamerica.fxml" )));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
        System.out.println("North America has been clicked");
    }

    @FXML
    private void handleEuropeClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader ((getClass().getResource(
                "/com/example/geoquestkidsexplorer/europe.fxml")));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
        System.out.println("Europe has been clicked");
    }

}