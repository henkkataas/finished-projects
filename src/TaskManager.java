import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles task-related database operations.
 */
public class TaskManager {
    private Map<Integer, String> categories = new HashMap<>();

    public void addTask(String name) {
        String sql = "INSERT INTO Task(name, isCompleted) VALUES(?, ?)";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, 0);
            pstmt.executeUpdate();
            System.out.println("Tehtävä lisätty: " + name);
        } catch (SQLException e) {
            System.out.println("Virhe tehtävää lisätessä: " + e.getMessage());
        }
    }

    public void removeTask(String name) {
        String sql = "DELETE FROM Task WHERE name = ?";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, name);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Tehtävä poistettu: " + name);
            } else {
                System.out.println("Tehtävää ei löytynyt: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Virhe tehtävää poistaessa: " + e.getMessage());
        }
    }

    public void markTaskAsCompleted(String name) {
        String sql = "UPDATE Task SET isCompleted = 1 WHERE name = ?";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, name);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Tehtävä merkitty valmiiksi: " + name);
            } else {
                System.out.println("Tehtävää ei löytynyt: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Virhe tehtävää päivittäessä: " + e.getMessage());
        }
    }

    public void displayTasks() {
        String sql = "SELECT * FROM Task";
        try (Statement stmt = Database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            boolean hasTasks = false;
            while (rs.next()) {
                hasTasks = true;
                String name = rs.getString("name");
                boolean isCompleted = rs.getInt("isCompleted") == 1;
                System.out.println((isCompleted ? "[X] " : "[ ] ") + name);
            }
            if (!hasTasks) {
                System.out.println("Ei tehtäviä näytettäväksi.");
            }
        } catch (SQLException e) {
            System.out.println("Virhe tehtäviä haettaessa: " + e.getMessage());
        }
    }

    public void addTaskWithCategory(String taskName, String categoryName) {
        String insertCategorySql = "INSERT INTO Category(name) VALUES(?)";
        String insertTaskSql = "INSERT INTO Task(name, isCompleted, categoryId) VALUES(?, ?, ?)";
        Connection conn = Database.getConnection();

        try {
            conn.setAutoCommit(false); // Aloita transaktio

            // Lisää kategoria
            int categoryId;
            try (PreparedStatement pstmt = conn.prepareStatement(insertCategorySql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, categoryName);
                pstmt.executeUpdate();
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        categoryId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Kategorian lisääminen epäonnistui, ID:tä ei saatu.");
                    }
                }
            }

            // Lisää tehtävä
            try (PreparedStatement pstmt = conn.prepareStatement(insertTaskSql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, 0); // isCompleted = 0 (false)
                pstmt.setInt(3, categoryId);
                pstmt.executeUpdate();
            }

            conn.commit(); // Vahvista transaktio
            System.out.println("Tehtävä ja kategoria lisätty onnistuneesti.");

        } catch (SQLException e) {
            try {
                conn.rollback(); // Peruuta transaktio virheen sattuessa
                System.out.println("Transaktio peruutettu.");
            } catch (SQLException ex) {
                System.out.println("Virhe transaktion peruutuksessa: " + ex.getMessage());
            }
            System.out.println("Virhe lisättäessä tehtävää ja kategoriaa: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Palauta oletusasetukseksi
            } catch (SQLException e) {
                System.out.println("Virhe AutoCommitin palautuksessa: " + e.getMessage());
            }
        }
    }

    public void displayTasksWithCategories() {
        String sql = "SELECT Task.name AS taskName, Task.isCompleted, Category.name AS categoryName "
                + "FROM Task LEFT JOIN Category ON Task.categoryId = Category.id";
        try (Statement stmt = Database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            boolean hasTasks = false;
            while (rs.next()) {
                hasTasks = true;
                String taskName = rs.getString("taskName");
                boolean isCompleted = rs.getInt("isCompleted") == 1;
                String categoryName = rs.getString("categoryName");
                System.out.println((isCompleted ? "[X] " : "[ ] ") + taskName + " - Kategoria: " + (categoryName != null ? categoryName : "Ei kategoriaa"));
            }
            if (!hasTasks) {
                System.out.println("Ei tehtäviä näytettäväksi.");
            }
        } catch (SQLException e) {
            System.out.println("Virhe tehtäviä haettaessa: " + e.getMessage());
        }
    }
}
