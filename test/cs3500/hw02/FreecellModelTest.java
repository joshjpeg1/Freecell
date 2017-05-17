package cs3500.hw02;

import cs3500.hw02.slot.ASlot;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

/** Tests for {@link FreecellModel}. */
public class FreecellModelTest {
  FreecellModel fcm = new FreecellModel();

  // Tests for the getDeck() method
  @Test
  public void getDeckSizeCheck() {
    assertEquals(52, fcm.getDeck().size());
  }

  @Test
  public void getDeckNoDuplicatesCheck() {
    assertTrue(Utils.noDuplicates(fcm.getDeck()));
  }

  // Tests for the startGame() method
  @Test(expected = IllegalArgumentException.class)
  public void startGameLowCascades() {
    fcm.startGame(fcm.getDeck(), 3, 3, true);
  }

  @Test
  public void startGameHighCascades() {
    List<ASlot> deck = fcm.getDeck();
    fcm.startGame(deck, deck.size(), 3, true);
    for (List<ASlot> pile : fcm.cascades) {
      assertEquals(pile.size(), 1);
    }
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
    List<ASlot> deck = fcm.getDeck();
    deck.add(deck.get(0));
    fcm.startGame(deck, 6, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDuplicatesInDeck() {
    List<ASlot> deck = fcm.getDeck();
    deck.remove(deck.size() - 1);
    deck.add(deck.get(0));
    fcm.startGame(deck, 6, 2, true);
  }

  // Tests for the getGameState() method
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

  // Tests for the move() method
  @Test(expected = IndexOutOfBoundsException.class)
  public void moveCascadeSourcePileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, -1, 0, PileType.OPEN, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveOpenSourcePileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.OPEN, -1, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveFoundationSourcePileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.FOUNDATION, -1, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveCascadeSourcePileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 8, 0, PileType.OPEN, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveOpenSourcePileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.OPEN, 4, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveFoundationSourcePileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.FOUNDATION, 4, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveCascadeSourceCardTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 5, PileType.OPEN, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveOpenSourceCardTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.OPEN, 0, -1, PileType.CASCADE, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveFoundationSourceCardTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.FOUNDATION, 0, -1, PileType.CASCADE, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveCascadeSourceCardTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 7, PileType.OPEN, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveOpenSourceCardTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.OPEN, 0, 1, PileType.CASCADE, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveFoundationSourceCardTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.FOUNDATION, 0, 1, PileType.CASCADE, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveCascadeDestPileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveOpenDestPileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.OPEN, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveFoundationDestPileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, -1, 0, PileType.FOUNDATION, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveCascadeDestPileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 8);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveOpenDestPileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.OPEN, 4);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void moveFoundationDestPileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, -1, 0, PileType.FOUNDATION, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadeDestHighOnLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 4);
  }

  /*
  @Test(expected = IllegalArgumentException.class)
  public void moveOpenDestFilled() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    fcm.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
  }*/

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationDestEmptyNotAce() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
  }

  /*
  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationDestWrongSuit() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    fcm.move(PileType.CASCADE, 5, 5, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationDestWrongNextValue() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    fcm.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
    fcm.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 0);
  }*/

}
