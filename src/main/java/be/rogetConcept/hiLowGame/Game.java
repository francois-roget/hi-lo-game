package be.rogetConcept.hiLowGame;

public class Game {
    private final int minRange, maxRange;
    private final String[] players;
    private final int mysteryNumber;
    private int currentPlayer = 0;
    private String winner;

    public Game(int minRange, int maxRange, String[] players) throws GameCreationException {
        checkInputs(minRange, maxRange, players);
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.players = players;
        this.mysteryNumber = generateRandomNumber(minRange, maxRange);
    }

    private static void checkInputs(int minRange, int maxRange, String[] players) throws GameCreationException {
        if(minRange == maxRange) {
            throw new GameCreationException("The minRange and maxRange cannot be the same");
        }
        if(minRange > maxRange) {
            throw new GameCreationException("The minRange must be lower than the maxRange");
        }
        if(minRange < 0) {
            throw new GameCreationException("The minRange must be positive");
        }
        if(players.length == 0) {
            throw new GameCreationException("At least one player is required");
        }
        for (String player : players) {
            if(player.isBlank()) {
                throw new GameCreationException("Player name cannot be blank");
            }
        }
    }

    public Game(int minRange, int maxRange) throws GameCreationException {
        this(minRange, maxRange, new String[]{"Single Player"});
    }

    public GamePlayResult play(int guess) throws GamePlayException {
        if(this.winner != null) {
            throw new GamePlayException("Game is over. The winner is " + this.winner);
        }
        if(guess < minRange || guess > maxRange) {
            throw new GamePlayException("Guess must be between " + minRange + " and " + maxRange);
        }
        if (guess == mysteryNumber) {
            this.winner = players[currentPlayer];
            return new GamePlayResult(players[currentPlayer], players[advancePlayer()], guess, "You guessed the mystery number!", true);
        } else if (guess < mysteryNumber) {
            return new GamePlayResult(players[currentPlayer], players[advancePlayer()], guess, "Your guess is too low.", false);
        } else {
            return new GamePlayResult(players[currentPlayer], players[advancePlayer()], guess, "Your guess is too high.", false);
        }
    }

    int getMysteryNumber() {
        return mysteryNumber;
    }

    private int advancePlayer() {
        currentPlayer = (currentPlayer + 1) % players.length;
        return currentPlayer;
    }

    private int generateRandomNumber(int minRange, int maxRange) {
        var range = maxRange - minRange;
        return (int) (Math.random() * range) + minRange;
    }
}
