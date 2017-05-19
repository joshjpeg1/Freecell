package cs3500.hw02;

import cs3500.hw02.slot.CardSuit;
import cs3500.hw02.slot.CardValue;
import cs3500.hw02.slot.ISlot;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/** Tests for {@link FreecellModel}. */
public class FreecellModelTest {
  FreecellModel fcm = new FreecellModel();

  // Tests for the getDeck() method
  @Test
  public void getDeckSizeCheck() {
    assertEquals(CardValue.values().length * CardSuit.values().length,
        fcm.getDeck().size());
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

  @Test(expected = IllegalArgumentException.class)
  public void startGameLowOpens() {
    fcm.startGame(fcm.getDeck(), 6, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNullDeck() {
    fcm.startGame(null, 6, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNullInDeck() {
    List<ISlot> deck = fcm.getDeck();
    deck.remove(0);
    deck.add(null);
    fcm.startGame(deck, 6, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameEmptyDeck() {
    fcm.startGame(new ArrayList<>(), 6, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameTooLargeDeck() {
    List<ISlot> deck = fcm.getDeck();
    deck.add(deck.get(0));
    fcm.startGame(deck, 6, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDuplicatesInDeck() {
    List<ISlot> deck = fcm.getDeck();
    deck.remove(deck.size() - 1);
    deck.add(deck.get(0));
    fcm.startGame(deck, 6, 2, true);
  }

  @Test
  public void startGameHighOpens() {
    fcm.startGame(fcm.getDeck(), 8, 10, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\nO5:\nO6:\nO7:\nO8:\nO9:\nO10:\n"
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
  public void startGameHighCascades() {
    List<ISlot> deck = fcm.getDeck();
    fcm.startGame(deck, deck.size(), 3, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\n"
        + "C1: A♣\nC2: A♦\nC3: A♥\nC4: A♠\nC5: 2♣\nC6: 2♦\nC7: 2♥\nC8: 2♠\n"
        + "C9: 3♣\nC10: 3♦\nC11: 3♥\nC12: 3♠\nC13: 4♣\nC14: 4♦\nC15: 4♥\nC16: 4♠\n"
        + "C17: 5♣\nC18: 5♦\nC19: 5♥\nC20: 5♠\nC21: 6♣\nC22: 6♦\nC23: 6♥\nC24: 6♠\n"
        + "C25: 7♣\nC26: 7♦\nC27: 7♥\nC28: 7♠\nC29: 8♣\nC30: 8♦\nC31: 8♥\nC32: 8♠\n"
        + "C33: 9♣\nC34: 9♦\nC35: 9♥\nC36: 9♠\nC37: 10♣\nC38: 10♦\nC39: 10♥\nC40: 10♠\n"
        + "C41: J♣\nC42: J♦\nC43: J♥\nC44: J♠\nC45: Q♣\nC46: Q♦\nC47: Q♥\nC48: Q♠\n"
        + "C49: K♣\nC50: K♦\nC51: K♥\nC52: K♠",
        fcm.getGameState());
  }

  @Test
  public void startGameVeryHighCascades() {
    List<ISlot> deck = fcm.getDeck();
    fcm.startGame(deck, deck.size() + 7, 3, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\n"
        + "C1: A♣\nC2: A♦\nC3: A♥\nC4: A♠\nC5: 2♣\nC6: 2♦\nC7: 2♥\nC8: 2♠\n"
        + "C9: 3♣\nC10: 3♦\nC11: 3♥\nC12: 3♠\nC13: 4♣\nC14: 4♦\nC15: 4♥\nC16: 4♠\n"
        + "C17: 5♣\nC18: 5♦\nC19: 5♥\nC20: 5♠\nC21: 6♣\nC22: 6♦\nC23: 6♥\nC24: 6♠\n"
        + "C25: 7♣\nC26: 7♦\nC27: 7♥\nC28: 7♠\nC29: 8♣\nC30: 8♦\nC31: 8♥\nC32: 8♠\n"
        + "C33: 9♣\nC34: 9♦\nC35: 9♥\nC36: 9♠\nC37: 10♣\nC38: 10♦\nC39: 10♥\nC40: 10♠\n"
        + "C41: J♣\nC42: J♦\nC43: J♥\nC44: J♠\nC45: Q♣\nC46: Q♦\nC47: Q♥\nC48: Q♠\n"
        + "C49: K♣\nC50: K♦\nC51: K♥\nC52: K♠\nC53:\nC54:\nC55:\nC56:\nC57:\nC58:\nC59:",
        fcm.getGameState());
  }

  // Tests for the getGameState() method
  @Test
  public void getGameStateEmpty() {
    FreecellModel empty = new FreecellModel();
    assertEquals("", empty.getGameState());
  }

  @Test
  public void getGameStateRegular() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
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

  @Test
  public void getGameStateMoveOpen() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.OPEN, 2);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3: K♣\nO4:\n"
        + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣\n"
        + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
        + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
        + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
        + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
        + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠",
        fcm.getGameState());
    fcm.move(PileType.OPEN, 2, 0, PileType.OPEN, 3);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4: K♣\n"
        + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣\n"
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
  public void getGameStateTakenOpenTakenFoundation() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 2; j++) {
        fcm.move(PileType.CASCADE, i + (j * 4), 6 - j, PileType.FOUNDATION, i);
      }
    }
    for (int i = 0; i < 4; i++) {
      fcm.move(PileType.CASCADE, i, 5, PileType.OPEN, i);
    }
    assertEquals("F1: A♠, 2♠\n"
        + "F2: A♥, 2♥\n"
        + "F3: A♦, 2♦\n"
        + "F4: A♣, 2♣\n"
        + "O1: 3♠\nO2: 3♥\nO3: 3♦\nO4: 3♣\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣",
        fcm.getGameState());
  }

  // Tests for the move() method
  @Test(expected = IllegalArgumentException.class)
  public void moveNullSource() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(null, -1, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNullDestination() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(null, -1, 0, null, 0);
  }

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

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenDestFilled() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    fcm.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationDestEmptyNotAce() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationDestWrongSuit() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    fcm.move(PileType.CASCADE, 5, 5, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationDestWrongNextValue() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 2);
    fcm.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 2);
    fcm.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOntoSelf() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 0);
  }

  @Test
  public void moveCardToOpen() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1: Q♣\nO2:\nO3:\nO4:\n"
        + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
        + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
        + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
        + "C5: 2♣, 4♣, 6♣, 8♣, 10♣\n"
        + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
        + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠",
        fcm.getGameState());
  }

  @Test
  public void moveQueenToKing() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 4, 5, PileType.CASCADE, 1);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
        + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦, Q♣\n"
        + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
        + "C5: 2♣, 4♣, 6♣, 8♣, 10♣\n"
        + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
        + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠",
        fcm.getGameState());
  }

  @Test
  public void moveAceToTwo() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣",
        fcm.getGameState());
  }

  @Test
  public void moveAceToFoundation() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    assertEquals("F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣",
        fcm.getGameState());
  }

  @Test
  public void moveAddToFoundation() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
    fcm.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 0);
    assertEquals("F1: A♠, 2♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣",
        fcm.getGameState());
  }

  @Test
  public void moveWin4Cascade() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 4, 4, false);
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < 13; i++) {
        fcm.move(PileType.CASCADE, j, 12 - i, PileType.FOUNDATION, j);
      }
    }
    assertEquals("F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\nC2:\nC3:\nC4:", fcm.getGameState());
  }

  @Test
  public void moveWin8Cascade() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i <= 6; i++) {
        fcm.move(PileType.CASCADE, j, 6 - i, PileType.FOUNDATION, j);
        if (6 - (i + 1) >= 0) {
          fcm.move(PileType.CASCADE, j + 4, 6 - (i + 1), PileType.FOUNDATION, j);
        }
      }
    }
    assertEquals("F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\nC2:\nC3:\nC4:\nC5:\nC6:\nC7:\nC8:", fcm.getGameState());
  }

  @Test
  public void moveWin52Cascade() {
    fcm.startGame(fcm.getDeck(), 52, 4, false);
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < 13; i++) {
        fcm.move(PileType.CASCADE, (i * 4) + j, 0, PileType.FOUNDATION, j);
      }
    }
    assertEquals("F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\nC2:\nC3:\nC4:\nC5:\nC6:\nC7:\nC8:\nC9:\nC10:\n"
        + "C11:\nC12:\nC13:\nC14:\nC15:\nC16:\nC17:\nC18:\nC19:\nC20:\n"
        + "C21:\nC22:\nC23:\nC24:\nC25:\nC26:\nC27:\nC28:\nC29:\nC30:\n"
        + "C31:\nC32:\nC33:\nC34:\nC35:\nC36:\nC37:\nC38:\nC39:\nC40:\n"
        + "C41:\nC42:\nC43:\nC44:\nC45:\nC46:\nC47:\nC48:\nC49:\nC50:\nC51:\nC52:",
        fcm.getGameState());
  }

  // Tests for the isGameOver() method
  @Test
  public void isGameOverStart() {
    fcm.startGame(fcm.getDeck(), 8, 4, true);
    assertFalse(fcm.isGameOver());
  }

  @Test
  public void isGameOverMidGame() {
    fcm.startGame(fcm.getDeck(), 52, 4, false);
    for (int j = 0; j < 2; j++) {
      for (int i = 0; i < 13; i++) {
        fcm.move(PileType.CASCADE, (i * 4) + j, 0, PileType.FOUNDATION, j);
      }
    }
    assertFalse(fcm.isGameOver());
  }

  @Test
  public void isGameOverOneMoveAway() {
    moveWin8Cascade();
    fcm.move(PileType.FOUNDATION, 0, 12, PileType.CASCADE, 0);
    assertFalse(fcm.isGameOver());
  }

  @Test
  public void isGameOverWin4Cascade() {
    moveWin4Cascade();
    assertTrue(fcm.isGameOver());
  }

  @Test
  public void isGameOverWin8Cascade() {
    moveWin8Cascade();
    assertTrue(fcm.isGameOver());
  }

  @Test
  public void isGameOverWin52Cascade() {
    moveWin52Cascade();
    assertTrue(fcm.isGameOver());
  }
}
