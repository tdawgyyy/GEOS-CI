package com.example.geoquestkidsexplorer.models;

import javafx.scene.image.Image;
import java.util.List;

public record PracticeQuizQuestions(
        String questionText,
        List<String> choices,
        String correctAnswer,
        String funFact,
        Image countryImage
) {
    public List<String> getChoices() {
        return choices;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public String getFunFact() {
        return funFact;
    }
}
