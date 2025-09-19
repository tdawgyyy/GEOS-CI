package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import com.example.geoquestkidsexplorer.models.UserProfile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.sql.*;
import javafx.scene.image.Image;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:geoquest.db";

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return A Connection object to the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
        //Note: Added this for the sidebar navigation icons!
    }

    // ===========================
    // Init / Schema
    // ===========================
    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            if (conn != null) {
                enableForeignKeys(conn);

                createContinentsTable(conn);
                seedContinents(conn);

                createCountriesTable(conn);
                createUsersTable(conn);
                createResultsTable(conn);

                System.out.println("Database created/connected.");
            }
        } catch (SQLException e) {
            System.err.println("DB init error: " + e.getMessage());
        }
    }

    private static void enableForeignKeys(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys=ON");
        }
    }
    // Continents
    private static void createContinentsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS continents (
                continent TEXT PRIMARY KEY,
                level     INTEGER NOT NULL UNIQUE CHECK(level BETWEEN 1 AND 7)
            );
            """;
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    private static void seedContinents(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) AS c FROM continents")) {
            if (rs.next() && rs.getInt("c") > 0) return; // already seeded
        }

        String sql = "INSERT INTO continents (continent, level) VALUES (?, ?)";
        String[] names = {"Antarctica","Oceania","South America","North America","Europe","Asia","Africa"};
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 0; i < names.length; i++) {
                ps.setString(1, names[i]);
                ps.setInt(2, i + 1); // levels 1..7
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        }
    }

    // Countries
    private static void createCountriesTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS countries (
                country         TEXT PRIMARY KEY,
                continent       TEXT NOT NULL,
                country_picture BLOB,
                hints           TEXT,
                FOREIGN KEY (continent) REFERENCES continents(continent)
                    ON UPDATE CASCADE ON DELETE RESTRICT
            );
            """;
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    // Users
    private static void createUsersTable(Connection conn) throws SQLException {
        // If table already exists, SQLite will keep the existing schema.
        // New setups will get UNIQUE(email).
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                avatar   TEXT NOT NULL,
                email    TEXT UNIQUE,
                password TEXT NOT NULL,
                level    INTEGER,
                role     TEXT,
                FOREIGN KEY (level) REFERENCES continents(level)
                    ON UPDATE CASCADE ON DELETE SET NULL
            );
            """;
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    // Results
    private static void createResultsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS results (
                id_result INTEGER PRIMARY KEY AUTOINCREMENT,
                username  TEXT NOT NULL,
                level     INTEGER NOT NULL,
                grades    REAL,
                status    TEXT CHECK (status IN ('Pass','Fail')),
                FOREIGN KEY (username) REFERENCES users(username)
                    ON UPDATE CASCADE ON DELETE CASCADE,
                FOREIGN KEY (level) REFERENCES continents(level)
                    ON UPDATE CASCADE ON DELETE RESTRICT
            );
            """;
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    // ===========================
    // Users API
    // ===========================

    /** Duplicate-safe insert used by Register. */
    public static void insertUser(String username, String email, String password,
                                  String avatar, Integer level, String role) {
        // Pre-check for duplicates
        if (userExists(username, email)) {
            throw new RuntimeException("Username or email already exists.");
        }

        String sql = "INSERT INTO users(username, email, password, avatar, level, role) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            enableForeignKeys(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, password); // ⚠️ In production, store a password hash instead.
                ps.setString(4, avatar);
                if (level == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, level);
                ps.setString(6, role);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Insert user error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /** Returns true if a user with matching username OR email exists. */
    public static boolean userExists(String username, String email) {
        final String sql = "SELECT 1 FROM users WHERE username = ? OR email = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            enableForeignKeys(conn);
            ps.setString(1, username);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("userExists error: " + e.getMessage());
            return false;
        }
    }

    /** Simple credential check for Login. */
    public static boolean validateLogin(String email, String password) {
        final String sql = "SELECT 1 FROM users WHERE email = ? AND password = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            enableForeignKeys(conn);
            ps.setString(1, email);
            ps.setString(2, password); // ⚠️ Compare hashes in production
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Login validation error: " + e.getMessage());
            return false;
        }
    }

    // Adding this new method to DatabaseManager.java file for fetching userID.
    public static int getUserIdByEmail(String email) {
        final String sql = "SELECT id FROM users WHERE email = ? LIMIT 1"; // assuming your users table has an 'id' column
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:geoquest.db");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("getUserIdByEmail error: " + e.getMessage());
        }
        return -1; // Return -1 if not found
    }


    /** Optional helper to load a profile by username. */
    public static UserProfile getUserProfileByUsername(String username) {
        final String sql = "SELECT username, email, avatar, level, role FROM users WHERE username = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            enableForeignKeys(conn);
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Adjust to your actual UserProfile constructor/fields
                    return new UserProfile(
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("avatar"),
                            rs.getInt("level"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("getUserProfileByUsername error: " + e.getMessage());
        }
        return null;
    }

    public static Image getCountryImage(String countryName) {
        String sql = "SELECT country_picture FROM countries WHERE country = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("country_picture");
                if (imgBytes != null) {
                    return new Image(new ByteArrayInputStream(imgBytes));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getUsernameByEmail(String email) {
        final String sql = "SELECT username FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:geoquest.db");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("username");
            }
        } catch (SQLException e) {
            System.err.println("getUsernameByEmail error: " + e.getMessage());
        }
        return null;
    }

    public static String getAvatarByEmail(String email) {
        final String sql = "SELECT avatar FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:geoquest.db");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("avatar");
            }
        } catch (SQLException e) {
            System.err.println("getAvatarByEmail error: " + e.getMessage());
        }
        return null;
    }
//For Quiz mode

    public static class CountryQuestion {
        public final String countryName;
        public final Image image;          // JavaFX image to show
        public final String hints;
        public CountryQuestion(String countryName, Image image, String hints) {
            this.countryName = countryName;
            this.image = image;
            this.hints = hints;
        }
    }

    /** Get a random country+image from a given continent. */
    public static CountryQuestion getRandomCountryByContinent(String continent) {
        final String sql = """
        SELECT country, country_picture, hints
        FROM countries
        WHERE continent = ?
        ORDER BY RANDOM()
        LIMIT 1
        """;
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            enableForeignKeys(conn);
            ps.setString(1, continent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String country = rs.getString("country");
                    String hints = rs.getString ("hints");
                    byte[] bytes = rs.getBytes("country_picture");
                    Image img = (bytes != null && bytes.length > 0)
                            ? new Image(new ByteArrayInputStream(bytes))
                            : null;
                    return new CountryQuestion(country, img, hints);
                }
            }
        } catch (SQLException e) {
            System.err.println("getRandomCountryByContinent error: " + e.getMessage());
        }
        return null;
    }

    /** Optional: normalize strings for answer checking (basic). */
    public static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

//---------------------------------------------------------------------------------------------------
//Note: Added for Practice Mode Quiz with country images. GLENDA

    // This method will generate a complete quiz question with an image and multiple-choice options.
    public static PracticeQuizQuestions getPracticeQuizQuestion(String continent) {
        //Get the correct country and its image for the question
        CountryQuestion correctQuestion = getRandomCountryByContinent(continent);

        if (correctQuestion == null) {
            return null; // No countries found
        }

        String correctAnswer = correctQuestion.countryName;
        Image countryImage = correctQuestion.image;
        String hints = correctQuestion.hints;

        //Get 3 random incorrect country names to use as distractors
        List<String> choices = getNRandomCountriesByContinent(continent, 3, correctAnswer);

        //Add the correct answer to the list of choices
        choices.add(correctAnswer);
        Collections.shuffle(choices); // Randomize the order of all 4 choices

        //(Optional) Create a simple question text and fun fact.
        // You can hard-code a generic question or use a more sophisticated method.
        String questionText = "Which country is this?";
        String funFact = "This is " + correctAnswer + ". " + hints;

        return new PracticeQuizQuestions(questionText, choices, correctAnswer, funFact, countryImage);
    }

    /** Gets a list of N random country names from a given continent.
     * Excludes the given 'exclude' country name if provided.
     */
    public static List<String> getNRandomCountriesByContinent(String continent, int limit, String exclude) {
        List<String> countries = new ArrayList<>();
        // The `AND country != ?` part handles the exclusion of the correct answer.
        String sql = "SELECT country FROM countries WHERE continent = ? AND country != ? ORDER BY RANDOM() LIMIT ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, continent);
            ps.setString(2, exclude);
            ps.setInt(3, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    countries.add(rs.getString("country"));
                }
            }
        } catch (SQLException e) {
            System.err.println("getNRandomCountriesByContinent error: " + e.getMessage());
        }
        return countries; // Returns the list, even if it's empty
    }
}
