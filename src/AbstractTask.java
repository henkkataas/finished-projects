/**
 * Abstract class representing a generic task.
 */
public abstract class AbstractTask {
    protected String name;
    protected boolean isCompleted;

    public AbstractTask(String name) {
        this.name = name;
        this.isCompleted = false;
    }

    public abstract void displayTask();

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public String getName() {
        return this.name;
    }
}
