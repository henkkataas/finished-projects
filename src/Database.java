import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles SQLite database connection and setup.
 */
public class Database {
    private static final String URL = "jdbc:sqlite:C:\\Users\\Henkk\\Documents\\GitHub\\finished-projects\\task.db"; // Update this to your actual database path
    private static Connection conn = null;

    public static void connect() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Tietokantayhteys avattu.");

            // Luodaan taulut
            createCategoryTable();
            createTaskTable();

        } catch (SQLException e) {
            System.out.println("Tietokantavirhe: " + e.getMessage());
        }
    }

    private static void createTaskTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Task ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "isCompleted INTEGER NOT NULL,"
                + "categoryId INTEGER,"
                + "FOREIGN KEY (categoryId) REFERENCES Category(id)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Taulu Task varmistettu.");
        } catch (SQLException e) {
            System.out.println("Virhe luodessa taulua Task: " + e.getMessage());
        }
    }

    private static void createCategoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Category ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Taulu Category varmistettu.");
        } catch (SQLException e) {
            System.out.println("Virhe luodessa taulua Category: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Virhe tietokantayhteyttä luodessa: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Tietokantayhteys suljettu.");
            }
        } catch (SQLException e) {
            System.out.println("Yhteyden sulkeminen epäonnistui: " + e.getMessage());
        }
    }
}
