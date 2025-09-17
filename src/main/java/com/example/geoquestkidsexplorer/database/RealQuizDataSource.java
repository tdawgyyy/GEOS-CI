package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.database.DatabaseManager.CountryQuestion;

// Use Implements -> Derived from the Modules week 9 I believe for more info
public final class RealQuizDataSource implements QuizDataSource{

    @Override
    public CountryQuestion getRandomCountryByContinent(String continent) {
        return DatabaseManager.getRandomCountryByContinent(continent);
    }

    @Override
    public String normalise(String source) {
        return DatabaseManager.normalize(source);
    }
}
