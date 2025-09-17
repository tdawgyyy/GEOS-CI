package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.controllers.HomePageController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class OceaniaController {

    @FXML
    private void backToContinents(ActionEvent event) {
        try {
            // Load the StartAdventure.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGameModeClick(MouseEvent event) {
        // Get the source of the click, which is the VBox tile.
        Node clickedTile = (Node) event.getSource();
        String tileId = clickedTile.getId();

/*<<<<<<< HEAD
        TestPageController controller = loader.getController();
        controller.setRegion("Oceania");
        controller.loadNextQuestion();
=======*/
        try {
            // Use the ID to determine which game mode was selected and load the corresponding scene.
            if ("practiceModeTile".equals(tileId)) {
                loadScene("/com/example/geoquestkidsexplorer/practicequizoceania.fxml", event);
            } else if ("testModeTile".equals(tileId)) {
                System.out.println("Test Mode Quiz selected!");
                //loadScene("/com/example/geoquestkidsexplorer/quiz_view.fxml", event);
                //Note: Nikki this is an example reference on how you can link your test quiz page for other continent
                openQuiz("Oceania");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//>>>>>>> fef85a8158a0a260cc1e24e61721080b4d9854d4

    //Note: this is Aaliyah's method reference on how you can link your .fxml page for your test quiz pages for other countries.
    //Always create this method to other continent controller and adjust the name refences to call your correct .fxml for the continent.
    private void openQuiz(String continent) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/geoquestkidsexplorer/quiz_view.fxml")); //For Oceania, it quiz_view.fxml
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
    private void handleFlashcards(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/FlashcardsPage.fxml"));
        Parent root = loader.load();

        FlashcardsController controller = loader.getController();
        controller.setRegion("Oceania");  // Pass the selected region dynamically here

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

    /**
     * A private helper method to load a new FXML scene and transition to it.
     * This version is more flexible and can accept any type of Event.
     *
     * @param fxmlPath The path to the FXML file to load.
     * @param event The event that triggered the action.
     * @throws IOException If the FXML file cannot be loaded.
     */
    private void loadScene(String fxmlPath, Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
