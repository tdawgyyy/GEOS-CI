package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/* Note
This controller is for learning mode (practice, unlimited tries).

For test mode (your testPage), you’d need similar logic:

Track score.

Display questions (but maybe limit number of questions).

Possibly add a timer or end-of-test summary.

Maybe disable Next button until the user submits.
*/

/*
Test Mode (modified):

Has a fixed number of questions (questionLimit).

Ends with a summary.

Disables input after the test is over.*/

public class TestPageControllerDraft {

    //FXML fields to fetch display elements
    @FXML
    private Label regionLabel;
    //    @FXML
//    private Label scoreLabel;
//    @FXML
//    private Label feedbackLabel;
    @FXML
    private ImageView countryImageView;
//    @FXML
//    private TextField answerField;
//    @FXML
//    private Button nextButton;

    //Store reference to window
    private Stage stage;

    //Private fields to keep track of internal state logic
    private String continent = "Oceania";
    private String currentAnswer;
    private int totalAsked = 0;
    private int totalCorrect = 0;
    private int questionLimit = 10;
    private boolean finished = false;
    private List<DatabaseManager.CountryQuestion> continentList = new ArrayList<>();


    public void setRegion(String continent) {
        this.continent = continent;
        if (regionLabel != null) regionLabel.setText(continent + " Quiz");
    }

    //Events
    //Return back to homepage
    @FXML
    private void handleBackToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/mainapp.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Swap scene to existing homepage and keeping the same scene
        stage.getScene().setRoot(root);
        stage.setFullScreen(true);
        stage.setTitle("GeoScope - HomePage");
        stage.show();
    }

    @FXML
    private void CheckAnswer(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("You are correct!");
    }

    //private void
    //Generate values for 4 buttons
    //Check answer if == correct answer
    // Display feedback on answer selection, "Right" and "Wrong", hide option buttons
    //getRandomCountryByContinent: make a list to store this if correct answer not in list call this again:
    private void GetAnswer() {

    }

    public void loadNextQuestion() {
//        feedbackLabel.setText("");
//        answerField.clear();

        DatabaseManager.CountryQuestion q = DatabaseManager.getRandomCountryByContinent(continent);
        totalAsked++;

        if (q == null) {
            currentAnswer = null;
            countryImageView.setImage(null);
//            feedbackLabel.setStyle("-fx-text-fill: #dc2626;");
//            feedbackLabel.setText("No image data found for " + continent + ". Populate the BLOBs first.");
//            updateScore();
            return;
        }

        currentAnswer = q.countryName;
        countryImageView.setImage(q.image);
//        updateScore();
    }

}

//
//    @FXML
//    private void handleSubmitButton(ActionEvent e) {
//        if (currentAnswer == null || currentAnswer.isBlank()) return;
//
//        String user = DatabaseManager.normalize(answerField.getText());
//        String target = DatabaseManager.normalize(currentAnswer);
//
//        if (user.equals(target)) {
//            totalCorrect++;
//            feedbackLabel.setStyle("-fx-text-fill: #16a34a;");
//            feedbackLabel.setText("✅ Correct! It is " + currentAnswer + ".");
//        } else {
//            feedbackLabel.setStyle("-fx-text-fill: #dc2626;");
//            feedbackLabel.setText("❌ Not quite. It was " + currentAnswer + ".");
//        }
//        //updateScore();
//    }





//    public void loadNextQuestion() {
////        feedbackLabel.setText("");
////        answerField.clear();
//
//        DatabaseManager.CountryQuestion randomContinent = null;
//
//        //Get 4 countries
//        while (continentList.size() < 4){
//
//            randomContinent = DatabaseManager.getRandomCountryByContinent(continent);
//
//            if (!continentList.contains(randomContinent)){
//                continentList.add(DatabaseManager.getRandomCountryByContinent(randomContinent.toString()));
//            }
//
//        }
//
//        //Choose random answer
//        // Pick a random index
//        int randomIndex = ThreadLocalRandom.current().nextInt(continentList.size());
//
//        // Get the element
//        String randomItem = continentList.get(randomIndex).toString();
//
//        //System.out.println("Random pick: " + randomItem);
//
//
//        //Assign buttons
//        List<ValueButton> buttons = new ArrayList<>();
//
//        for (DatabaseManager.CountryQuestion country : continentList) {
//            ValueButton button = new ValueButton(country.toString(), country.toString()); // text and value
//            buttons.add(button);
//
//            // Debugging
//            button.setOnAction(e -> {
//                System.out.println("Selected country: " + button.getValue());
//            });
//
//
//
//        if (randomContinent == null) {
//            currentAnswer = null;
//            countryImageView.setImage(null);
////            feedbackLabel.setStyle("-fx-text-fill: #dc2626;");
////            feedbackLabel.setText("No image data found for " + continent + ". Populate the BLOBs first.");
//            //updateScore();
//            return;
//        }
//
//        currentAnswer = randomContinent.countryName;
//        countryImageView.setImage(randomContinent.image);
//        //updateScore();
//        //totalAsked++;
//    }








//public class TestPageController {
//    public void setRegion(String region) {
//        regionLabel.setText("Let's test your knowledge on " + region);
//    }
//
//
//    // Return back to homepage
//    @FXML
//    private void handleBackToHome(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/mainapp.fxml"));
//
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        // Swap scene to existing homepage and keeping the same scene
//        stage.getScene().setRoot(root);
//        stage.setTitle("GeoScope - HomePage");
//        stage.show();
//    }
//
//
//
//    @FXML
//    private Label regionLabel;
//
//
//
//
//}
