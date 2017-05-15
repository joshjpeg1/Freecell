package cs3500.hw02;

import cs3500.hw02.card.Card;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh_jpeg on 5/14/17.
 */
public class FreecellModelTest {
  FreecellModel fcm = new FreecellModel();

  @Test
  public void getDeckSizeCheck() {
    assertEquals(52, fcm.getDeck().size());
  }

  @Test
  public void getDeckNoDuplicatesCheck() {
    assertTrue(Utils.noDuplicates(fcm.getDeck()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameLowCascades() {
    fcm.startGame(fcm.getDeck(), 3, 3, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameHighCascades() {
    fcm.startGame(fcm.getDeck(), 9, 3, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameLowOpens() {
    fcm.startGame(fcm.getDeck(), 6, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameHighOpens() {
    fcm.startGame(fcm.getDeck(), 6, 5, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameEmptyDeck() {
    fcm.startGame(new ArrayList<>(), 6, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameTooLargeDeck() {
    List<Card> deck = fcm.getDeck();
    deck.add(deck.get(0));
    fcm.startGame(deck, 6, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDuplicatesInDeck() {
    List<Card> deck = fcm.getDeck();
    deck.remove(deck.size() - 1);
    deck.add(deck.get(0));
    fcm.startGame(deck, 6, 2, true);
  }

  @Test
  public void getGameStateRegular() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:"
        + "\nO1:\nO2:\nO3:\nO4:\n"
        + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
        + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
        + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
        + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
        + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
        + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠",
        fcm.getGameState());
  }

  @Test
  public void getGameStateShuffled() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    String regular = fcm.getGameState();
    fcm.startGame(fcm.getDeck(), 8, 4, true);
    String shuffled = fcm.getGameState();
    assertNotEquals(regular, shuffled);
  }
}
