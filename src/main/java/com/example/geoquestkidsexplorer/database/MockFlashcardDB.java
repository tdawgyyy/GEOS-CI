package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.controllers.FlashcardsController;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

// Created a mock for unit testing in order to match the FlashController Logic
// Week 6.1 - 6.3 - Modules

public class MockFlashcardDB {
    private final List<String> names = new ArrayList<>();
    private final List<Image>  images = new ArrayList<>();

    public MockFlashcardDB add(String name, Image image) {
        names.add(name);
        images.add(image);
        return this;
    }

    public void fillInternalCountries(FlashcardsController controller) {
        controller.testSeed(names, images);
    }
}