import java.util.Scanner;

/**
 * Entry point for the To-Do List application.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        // Connect to the database
        String fileName = args.length > 0 ? args[0] : "task.db";
        Database.connect();

        System.out.println("Tervetuloa To-Do List -sovellukseen!");

        while (true) {
            System.out.println("\nVaihtoehdot:");
            System.out.println("1. Lisää tehtävä");
            System.out.println("2. Poista tehtävä");
            System.out.println("3. Merkitse tehtävä valmiiksi");
            System.out.println("4. Näytä tehtävät");
            System.out.println("5. Lisää tehtävä kategoriolla");
            System.out.println("6. Näytä tehtävät kategorioilla");
            System.out.println("7. Poistu");
            System.out.print("Valitse toiminto: ");

            String choice = InputValidator.readMenuChoice(scanner, 1, 7);

            switch (choice) {
                case "1":
                    System.out.print("Anna tehtävän nimi: ");
                    String taskName = scanner.nextLine();
                    taskManager.addTask(taskName);
                    break;
                case "2":
                    System.out.print("Anna poistettavan tehtävän nimi: ");
                    String taskToRemove = scanner.nextLine();
                    taskManager.removeTask(taskToRemove);
                    break;
                case "3":
                    System.out.print("Anna valmiiksi merkittävän tehtävän nimi: ");
                    String taskToComplete = scanner.nextLine();
                    taskManager.markTaskAsCompleted(taskToComplete);
                    break;
                case "4":
                    taskManager.displayTasks();
                    break;
                case "5":
                    System.out.print("Anna tehtävän nimi: ");
                    String newTaskName = scanner.nextLine();
                    System.out.print("Anna kategorian nimi: ");
                    String newCategoryName = scanner.nextLine();
                    taskManager.addTaskWithCategory(newTaskName, newCategoryName);
                    break;
                case "6":
                    taskManager.displayTasksWithCategories();
                    break;
                case "7":
                    Database.closeConnection();
                    System.out.println("Näkemiin!");
                    // Wait for a second before exiting because why not
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Ajastin keskeytettiin: " + e.getMessage());
                    }

                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Virheellinen valinta. Yritä uudelleen.");
            }
        }
    }
}
