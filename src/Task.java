/**
 * Represents a task in the system.
 */
public class Task extends AbstractTask {
    private int id;
    private int categoryId;

    // Constructor for new tasks
    public Task(String name) {
        super(name);
    }

    // Constructor for tasks loaded from the database
    public Task(int id, String name, boolean isCompleted, int categoryId) {
        super(name);
        this.id = id;
        this.isCompleted = isCompleted;
        this.categoryId = categoryId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void displayTask() {
        System.out.println((isCompleted ? "[X] " : "[ ] ") + name);
    }
}
