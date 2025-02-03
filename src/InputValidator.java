import java.util.Scanner;

/**
 * Utility class for validating user input.
 */
public class InputValidator {

    public static int readInt(Scanner scanner) {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Virheellinen syöte. Anna numero: ");
            }
        }
        return number;
    }

    public static String readMenuChoice(Scanner scanner, int min, int max) {
        String choice = scanner.nextLine();
        while (!choice.matches("[" + min + "-" + max + "]")) {
            System.out.print("Virheellinen valinta. Valitse uudestaan: ");
            choice = scanner.nextLine();
        }
        return choice;
    }
}
