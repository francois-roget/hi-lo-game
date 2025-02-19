package be.rogetConcept.hiLowGame;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Welcome in the Hi-Low Game!");
            String[] players = getPlayers();

            Game game = initializeGame(players);

            GamePlayResult result = null;
            while (true) {
                try {
                    var nextPlayer = result != null ? result.nextPlayer() : players[0];
                    System.out.printf("%s : Guess the mystery number%n", nextPlayer);
                    int guess = scanner.nextInt();
                    result = game.play(guess);
                    System.out.println(result.result());
                    if (result.win()) {
                        System.out.println("The winner is " + result.player());
                        break;
                    }
                }catch (GamePlayException e) {
                    System.err.println(e.getMessage());
                }
            }
        }catch (GameCreationException e) {
            System.err.println(e.getMessage());
        }

    }

    private static Game initializeGame(String[] players) throws GameCreationException {
        System.out.println("Enter the min range of the mystery number");
        int minRange = scanner.nextInt();
        System.out.println("Enter the max range of the mystery number");
        int maxRange = scanner.nextInt();

        return new Game(minRange, maxRange, players);
    }

    private static String[] getPlayers() {
        System.out.println("How many players do you want to play with?");
        int numberOfPlayers = scanner.nextInt();

        String[] players = new String[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter the name of player " + (i + 1));
            String playerName = scanner.next();
            players[i] = playerName;
        }
        return players;
    }
}