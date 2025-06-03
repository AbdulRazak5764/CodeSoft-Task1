 import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    
    // Game variables
    private Scanner scanner;
    private Random random;
    private int minNumber = 1;
    private int maxNumber = 100;
    private int secretNumber;
    private int attempts;
    private int maxAttempts = 7;
    private int gamesWon = 0;
    private int totalGames = 0;
    
    public NumberGame() {
        scanner = new Scanner(System.in);
        random = new Random();
    }
    
    // Show welcome message
    public void showWelcome() {
        System.out.println("\n==========================================");
        System.out.println("WELCOME TO THE NUMBER GUESSING GAME!");
        System.out.println("==========================================");
        System.out.println("I'm thinking of a number between " + minNumber + " and " + maxNumber);
        System.out.println("You have " + maxAttempts + " tries to guess it");
        System.out.println("Let's get started!");
        System.out.println("==========================================\n");
    }
    
    // Generate random number
    public void generateNumber() {
        secretNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        System.out.println("I've picked a number between " + minNumber + " and " + maxNumber + ". Start guessing!");
    }
    
    // Check if guess is valid
    public boolean isValidGuess(String input) {
        try {
            int guess = Integer.parseInt(input);
            if(guess < minNumber || guess > maxNumber) {
                System.out.println("Please enter a number between " + minNumber + " and " + maxNumber + "!");
                return false;
            }
            return true;
        } catch(NumberFormatException e) {
            System.out.println("That's not a valid number! Try again.");
            return false;
        }
    }
    
    // Process the user's guess
    public boolean processGuess(int guess) {
        attempts++;
        
        if(guess == secretNumber) {
            System.out.println("\nYOU GOT IT! The number was " + secretNumber + "!");
            System.out.println("It took you " + attempts + " attempt" + (attempts == 1 ? "" : "s") + "!");
            gamesWon++;
            return true;
        } else if(guess < secretNumber) {
            System.out.println("Too low! Try a higher number. (Attempt " + attempts + "/" + maxAttempts + ")");
        } else {
            System.out.println("Too high! Try a lower number. (Attempt " + attempts + "/" + maxAttempts + ")");
        }
        
        // Check if out of attempts
        if(attempts >= maxAttempts) {
            System.out.println("\nGame over! You're out of attempts. The number was " + secretNumber + ".");
            return true;
        }
        
        return false;
    }
    
    // Play one round of the game
    public void playRound() {
        attempts = 0;
        generateNumber();
        boolean gameFinished = false;
        
        while(!gameFinished) {
            System.out.print("Your guess: ");
            String input = scanner.nextLine();
            
            if(isValidGuess(input)) {
                int guess = Integer.parseInt(input);
                gameFinished = processGuess(guess);
            }
        }
        
        totalGames++;
        showStats();
    }
    
    // Show game statistics
    public void showStats() {
        System.out.println("\n----- GAME STATS -----");
        System.out.println("Games won: " + gamesWon + " out of " + totalGames);
        
        if(totalGames > 0) {
            double winRate = (double)gamesWon / totalGames * 100;
            System.out.println("Win rate: " + Math.round(winRate) + "%");
        }
        
        // Give performance feedback
        if(attempts <= 3) {
            System.out.println("Performance: EXCELLENT!");
        } else if(attempts <= 5) {
            System.out.println("Performance: GOOD!");
        } else {
            System.out.println("Performance: Keep practicing!");
        }
    }
    
    // Ask player to choose difficulty
    public void chooseDifficulty() {
        System.out.println("\nSelect difficulty:");
        System.out.println("1. Easy (1-50, 6 attempts)");
        System.out.println("2. Normal (1-100, 7 attempts)");
        System.out.println("3. Hard (1-200, 8 attempts)");
        System.out.print("Pick a number (1-3) or just hit enter for Normal: ");
        
        String choice = scanner.nextLine();
        
        if(choice.equals("1")) {
            maxNumber = 50;
            maxAttempts = 6;
            System.out.println("Easy mode selected!");
        } else if(choice.equals("3")) {
            maxNumber = 200;
            maxAttempts = 8;
            System.out.println("Hard mode selected!");
        } else {
            // Default to normal mode
            maxNumber = 100;
            maxAttempts = 7;
            System.out.println("Normal mode selected!");
        }
    }
    
    // Ask if player wants to play again
    public boolean playAgain() {
        System.out.print("\nWanna play again? (y/n): ");
        String answer = scanner.nextLine().toLowerCase();
        return answer.equals("y") || answer.equals("yes");
    }
    
    // Main game loop
    public void startGame() {
        showWelcome();
        
        do {
            chooseDifficulty();
            playRound();
        } while(playAgain());
        
        System.out.println("\nThanks for playing! Final score: " + gamesWon + "/" + totalGames + " games won.");
        System.out.println("See ya!");
        scanner.close();
    }
    
    // Main method
    public static void main(String[] args) {
        NumberGame game = new NumberGame();
        game.startGame();
    }
}