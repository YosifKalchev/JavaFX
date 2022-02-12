import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(System.in);
        String wordToGuess;
        System.out.println("Enter the number of the players (1 or 2): ");

        String playersCount = input.nextLine();

        if (playersCount.equals("1")) {

            Scanner scanner = new Scanner(new File(Objects
                    .requireNonNull(Main.class.getResource("words.txt")).getPath()));

            List<String> words = new ArrayList<>();
            while (scanner.hasNext()) {
                words.add(scanner.nextLine());
            }

            Random random = new Random();
            wordToGuess = words.get(random.nextInt(words.size()));
        } else {
            System.out.println("Player 1, please enter a word: ");
            wordToGuess = input.nextLine();
            for (int i = 0; i < 100; i++) {
                System.out.println("\n");
            }
            System.out.println("Player 2, it is your turn: ");
        }

        List<Character> playerGuesses = new ArrayList<>();

        int wrongCount = 0;
        while (true) {

            printHangman(wrongCount);

            if (wrongCount >= 6) {
                System.out.println("You lose! The word is: " + wordToGuess);
                break;
            }

            if (!printWordGuesses(wordToGuess, playerGuesses)) {
                wrongCount++;
            }

            getPlayerGuess(input, playerGuesses);
            if (printWordGuesses(wordToGuess, playerGuesses)) {
                System.out.println("Congratulations! You WIN!");
                break;
            }
            System.out.println("Please enter your guess for the word: ");
            if (input.nextLine().equals(wordToGuess)) {
                System.out.println("Congratulations! You WIN!");
                break;
            } else {
                System.out.println("You guessed WRONG, try again: ");
            }
        }

    }

    private static void printHangman(int wrongCount) {
        System.out.println(" =======");
        System.out.println(" |     |");
        if (wrongCount >= 1) {
            System.out.println(" o");
        }
        if (wrongCount >= 2) {
            System.out.print("\\ ");
            if (wrongCount >= 3) {
                System.out.println("/");
            }
        } else {
            System.out.println();
        }
        if (wrongCount >= 4) {
            System.out.println(" |");
        }
        if (wrongCount >= 5) {
            System.out.print("/ ");
            if (wrongCount >= 6) {
                System.out.println("\\");
            }
        } else {
            System.out.println();
        }
    }

    private static void getPlayerGuess(Scanner input, List<Character> playerGuesses) {
        System.out.println("Please enter a letter: ");
        String letterGuess = input.nextLine();
        playerGuesses.add(letterGuess.charAt(0));

    }

    private static boolean printWordGuesses(String wordToGuess, List<Character> playerGuesses) {

        int correctCount = 0;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (playerGuesses.contains(wordToGuess.charAt(i))) {
                System.out.print(wordToGuess.charAt(i));
                correctCount++;
            }
            else {
                System.out.print("-");
            }
        }
        System.out.println();

        return (correctCount == wordToGuess.length());
    }
}