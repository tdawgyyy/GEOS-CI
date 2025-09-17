package com.example.geoquestkidsexplorer.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class NorthAmericaController {
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

        try {
            // Use the ID to determine which game mode was selected and load the corresponding scene.
            if ("practiceModeTile".equals(tileId)) {
                loadScene("/com/example/geoquestkidsexplorer/practicequizoceania.fxml", event);
            } else if ("testModeTile".equals(tileId)) {
                // Nikki: insert testpage link and call openTestMethod
                loadScene("/com/example/geoquestkidsexplorer/testModeAntarctica.fxml", event);
                System.out.println("Test Mode Quiz selected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
