package be.rogetConcept.hiLowGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testMysteryNumberCreation() throws GameCreationException {
        var game = new Game( 1, 10);
        assertTrue(game.getMysteryNumber() >= 1 && game.getMysteryNumber() <= 10);
    }

    @Test
    public void testGameCreation_minEqualMax() {
        assertThrows(GameCreationException.class, () -> new Game(1, 1));
    }

    @Test
    public void testGameCreation_minHigherThanMax() {
        assertThrows(GameCreationException.class, () -> new Game(10, 1));
    }

    @Test
    public void testGameCreation_noPlayers() {
        assertThrows(GameCreationException.class, () -> new Game(1, 10, new String[]{}));
    }

    @Test
    public void testGameCreation_negativeRange() {
        assertThrows(GameCreationException.class, () -> new Game(-10, -1));
    }

    @Test
    public void testPlay_winGame() throws GameCreationException, GamePlayException {
        var game = new Game(1, 10);
        var mysteryNumber = game.getMysteryNumber();
        var result = game.play(mysteryNumber);
        assertEquals("You guessed the mystery number!", result.result());
        assertTrue(result.win());
    }

    @Test
    public void testPlay_tooLow() throws GameCreationException, GamePlayException  {
        var game = new Game(1, 10);
        var mysteryNumber = game.getMysteryNumber();

        // Make sure the mystery number is not 1 since we need to test a lower number
        while(mysteryNumber == 1) {
            game = new Game(1, 10);
            mysteryNumber = game.getMysteryNumber();
        }
        var result = game.play(mysteryNumber - 1);
        assertEquals("Your guess is too low.", result.result());
        assertFalse(result.win());
    }

    @Test
    public void testPlay_tooHigh() throws GameCreationException, GamePlayException  {
        var game = new Game(1, 10);
        var mysteryNumber = game.getMysteryNumber();

        // Make sure the mystery number is not 10 since we need to test a higher number
        while(mysteryNumber == 10) {
            game = new Game(1, 10);
            mysteryNumber = game.getMysteryNumber();
        }
        var result = game.play(mysteryNumber + 1);
        assertEquals("Your guess is too high.", result.result());
        assertFalse(result.win());
    }

    @Test
    public void testPlay_switchPlayer() throws GameCreationException, GamePlayException  {
        var game = new Game(1, 10, new String[]{"Player 1", "Player 2", "Player 3"});
        var mysteryNumber = game.getMysteryNumber();

        // Make sure the mystery number is not 1 nor 10 since we need to test a lower number and a higher number
        while(mysteryNumber == 1 || mysteryNumber == 10) {
            game = new Game(1, 10, new String[]{"Player 1", "Player 2", "Player 3"});
            mysteryNumber = game.getMysteryNumber();
        }
        var result = game.play(mysteryNumber -1);
        assertEquals("Player 1", result.player());
        assertEquals("Player 2", result.nextPlayer());
        assertFalse(result.win());

        result = game.play(mysteryNumber + 1);
        assertEquals("Player 2", result.player());
        assertEquals("Player 3", result.nextPlayer());
        assertFalse(result.win());

        result = game.play(mysteryNumber);
        assertEquals("Player 3", result.player());
        assertEquals("Player 1", result.nextPlayer());
        assertTrue(result.win());
    }

    @Test
    public void testPlay_alreadyWon() throws GameCreationException, GamePlayException  {
        var game = new Game(1, 10);
        var mysteryNumber = game.getMysteryNumber();
        var result = game.play(mysteryNumber);
        assertEquals("You guessed the mystery number!", result.result());
        assertTrue(result.win());

        assertThrows(GamePlayException.class, () -> game.play(mysteryNumber));
    }

    @Test
    public void testPlay_guessOutOfRange() throws GameCreationException  {
        var game = new Game(1, 10);
        var mysteryNumber = game.getMysteryNumber();
        assertThrows(GamePlayException.class, () -> game.play(mysteryNumber + 100));
    }
}
