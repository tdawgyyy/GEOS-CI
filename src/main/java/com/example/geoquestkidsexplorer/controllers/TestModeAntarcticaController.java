//Import packages:
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

public class TestModeAntarcticaController {

    //Goes back to homepage with all the continents.
    @FXML
    private void backToContinents(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml")
            );
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    @FXML
    private void handleGameModeClick(MouseEvent event) {
        // Be robust if a child node inside the tile was clicked
        Node n = (Node) event.getTarget();
        while (n != null && n.getId() == null) n = n.getParent();
        String tileId = (n != null) ? n.getId() : null;
        if (tileId == null) {
            System.out.println("handleGameModeClick: no tile id found");
            return;
        }

        try {
            if ("practiceModeTile".equals(tileId)) {
                loadScene("/com/example/geoquestkidsexplorer/practicequizoceania.fxml", event);

            } else if ("testModeTile".equals(tileId)) {
                // 👉 Open the quiz in the SAME window
                openQuiz(event, "Oceania");
            } else {
                System.out.println("handleGameModeClick: unknown tile id " + tileId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Open quiz_view.fxml in the SAME window (no new Stage) */
    private void openQuiz(Event event, String continent) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/geoquestkidsexplorer/quiz_view.fxml")
        );
        Parent root = loader.load();

        // Reuse the existing window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root, 800, 600));
        } else {
            stage.getScene().setRoot(root);
        }
        stage.setTitle(continent + " Quiz");

        // Pass data into the quiz controller (optional setStage if your controller uses it)
        QuizController controller = loader.getController();
        try {
            controller.setStage(stage);   // keep Back actions working if your controller expects a Stage
        } catch (NoSuchMethodError | Exception ignore) { /* ok if not present */ }
        controller.setContinent(continent); // loads the first question inside controller

        stage.show();
    }

    // (unchanged) Opens country test page in a new window — not used by quiz branch
    private void openTestPage(String continent, String country) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/geoquestkidsexplorer/country_image.fxml")
        );
        Parent root = loader.load();

        CountryImageController controller = loader.getController();
        Stage testStage = new Stage();
        testStage.setTitle(continent + " – " + country);
        testStage.setScene(new Scene(root, 600, 400));
        controller.setCountry(country, testStage);

        testStage.show();
    }

    @FXML
    private void handleFlashcards(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/geoquestkidsexplorer/FlashcardsPage.fxml")
        );
        Parent root = loader.load();

        FlashcardsController controller = loader.getController();
        controller.setRegion("Oceania");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

    /** Helper: load an FXML into the current window */
    private void loadScene(String fxmlPath, Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }




}
