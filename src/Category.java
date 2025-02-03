/**
 * Represents a task category in the system.
 */
public class Category {
    private int id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
