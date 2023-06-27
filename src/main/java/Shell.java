import java.util.Scanner;

/**
 * Collection of static methods for interaction with program via shell.
 */
public final class Shell {
    private static Scanner in = new Scanner(System.in);
    private static Trie trie = new Trie();

    private Shell() {

    }

    /**
     * Entrypoint into the program. Loops endlessly if break condition not
     * triggered.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.print("trie> ");
            String[] input = in.nextLine().trim().split("\s+");
            if (input == null) {
                break;
            }
            switch (input[0]) {
                case "new":
                    trie = new Trie();
                    break;
                case "add":
                    addCommand(input);
                    break;
                case "change":
                    changeCommand(input);
                    break;
                case "delete":
                    deleteCommand(input);
                    break;
                case "points":
                    pointsCommand(input);
                    break;
                case "help":
                    helpCommand();
                    break;
                case "trie":
                    System.out.println(trie);
                    break;
                case "quit":
                    running = false;
                    break;
                default:
                    printErr("Not a valid command.");
            }
        }
    }

    /**
     * Prints a help dialog to the console, explaining all available commands.
     */
    private static void helpCommand() {
        String dialog = """
                Allowed Commands:
                new: empties the Trie.
                add <n> <p>: adds a new entry for <n> with value <p>.
                change <n> <p>: changes the value for <n> to <p>.
                delete <n>: removes the entry for <n> from the trie.
                points <n>: prints the value for <n> to the console.
                trie <n>: prints the whole trie in a one-line format.
                help: shows a dialog containing all valid commands.
                quit: stops the program from executing further.
                """;
        System.out.print(dialog);
    }

    /**
     * Executes necessary checks on the input and prints points if possible.
     *
     * @param input Array of arguments, including command itself.
     */
    private static void pointsCommand(String[] input) {
        if (!checkArgs(input, 2) || !checkName(input)) {
            return;
        }
        Integer points = trie.points(input[1]);
        if (points == null) {
            printErr("Could not get points!");
        } else {
            System.out.println(points);
        }
    }

    /**
     * Executes necessary checks on the input and removes entry if possible.
     *
     * @param input Array of arguments, including command itself.
     */
    private static void deleteCommand(String[] input) {
        if (!checkArgs(input, 2) || !checkName(input)) {
            return;
        }
        if (!trie.remove(input[1])) {
            printErr("Could not be removed!");
        }
    }

    /**
     * Executes necessary checks on the input and changes entries value if
     * possible.
     *
     * @param input Array of arguments, including command itself.
     */
    private static void changeCommand(String[] input) {
        if (!checkArgs(input, 3) || !checkName(input)) {
            return;
        }
        Integer points = checkPoints(input);
        if (points == null) {
            return;
        } else if (!trie.change(input[1], points)) {
            printErr("Could not be changed!");
        }
    }

    /**
     * Executes necessary check on the input and add new entry if possible.
     *
     * @param input Array of arguments, including command itself.
     */
    private static void addCommand(String[] input) {
        if (!checkArgs(input, 3) || !checkName(input)) {
            return;
        }
        Integer points = checkPoints(input);
        if (points == null) {
            return;
        } else if (!trie.add(input[1], points)) {
            printErr("Could not be added!");
        }
    }

    /**
     * Prints an error message with a given prefix.
     *
     * @param err The message which should be printed.
     */
    private static void printErr(String err) {
        System.out.println("Error! " + err);
    }

    /**
     * Helper method for validating points input.
     *
     * @param input Array of arguments, including points.
     * @return points if parsing possible; null otherwise
     */
    private static Integer checkPoints(String[] input) {
        Integer points = null;
        try {
            return Integer.parseInt(input[2]);
        } catch (NumberFormatException e) {
            printErr("You need to specify a valid number.");
            return null;
        }
    }

    /**
     * Helper method for validating name input.
     *
     * @param input Array of arguments, including name.
     * @return boolean indicating if name is valid.
     */
    private static boolean checkName(String[] input) {
        if (input[1] == null
                || input[1].length() == 0
                || !input[1].matches("[a-z]+")) {
            printErr("You need to specify a valid name.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Helper method for validating argument count.
     *
     * @param input Array of arguments.
     * @param count minimum number of expected arguments.
     * @return boolean indicating if enough arguments were supplied.
     */
    private static boolean checkArgs(String[] input, int count) {
        if (input.length >= count) {
            return true;
        } else {
            printErr("Not enough arguments.");
            return false;
        }
    }
}
