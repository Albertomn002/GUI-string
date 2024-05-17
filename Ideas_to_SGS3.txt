import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class StringSplitter {

    public static void main(String[] args) {
        // Replace with your database connection details
        String dbUrl = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";

        // Input string to split
        String inputString = "This is an example string to split";
        String delimiter = " "; // Change delimiter based on your needs

        // Split the string
        String[] parts = inputString.split(delimiter);

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            // Prepare SQL statement (avoiding SQL injection)
            String sql = "INSERT INTO your_table (part1, part2, part3, ...) VALUES (?, ?, ?, ...)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set values for each part and insert into database
            for (int i = 0; i < parts.length; i++) {
                statement.setString(i + 1, parts[i]);
            }
            statement.executeUpdate();
            System.out.println("Successfully inserted data into database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}