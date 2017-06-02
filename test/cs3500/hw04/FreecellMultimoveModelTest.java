package cs3500.hw04;

import cs3500.hw02.PileType;
import cs3500.hw02.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Tests for {@link FreecellMultimoveModel}. */
public class FreecellMultimoveModelTest {
  FreecellMultimoveModel fcm = new FreecellMultimoveModel();
  // Tests for the move()
  @Test(expected = IllegalArgumentException.class)
  public void moveNullSource() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(null, 3, 6, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNullDestination() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 3, 6, null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadeSourcePileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, -1, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenSourcePileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.OPEN, -1, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationSourcePileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.FOUNDATION, -1, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadeSourcePileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 8, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenSourcePileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.OPEN, 4, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationSourcePileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.FOUNDATION, 4, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadeSourceCardTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 5, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenSourceCardTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.OPEN, 0, -1, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationSourceCardTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.FOUNDATION, 0, -1, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadeSourceCardTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 7, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenSourceCardTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.OPEN, 0, 1, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationSourceCardTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.FOUNDATION, 0, 1, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadeDestPileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenDestPileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.OPEN, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFoundationDestPileTooLow() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, -1, 0, PileType.FOUNDATION, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadeDestPileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenDestPileTooHigh() {
    fcm.startGame(fcm.getDeck(), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.OPEN, 4);
  }

  @Test(expected = IllegalArgumentException.class)
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
    fcm.move(PileType.CASCADE, 4, 5, PileType.OPEN, 0);
    fcm.move(PileType.CASCADE, 4, 4, PileType.FOUNDATION, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOntoSelf() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsToFoundation() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsCantFromFoundation() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    for (int i = 0; i <= 6; i++) {
      fcm.move(PileType.CASCADE, 0, 6 - i, PileType.FOUNDATION, 0);
      if (6 - (i + 1) >= 0) {
        fcm.move(PileType.CASCADE, 4, 6 - (i + 1), PileType.FOUNDATION, 0);
      }
    }
    fcm.move(PileType.FOUNDATION, 0, 10, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsToOpen() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsInvalidBuild() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 3, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsToWrongSuit() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);

    fcm.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 0);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    fcm.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsToWrongValue() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);

    fcm.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 0);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    fcm.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsExceedsMaxBuildSizeEmptyOpens() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    fcm.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 0);
    fcm.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 5);
    fcm.move(PileType.CASCADE, 5, 4, PileType.CASCADE, 0);
    fcm.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 5);
    fcm.move(PileType.CASCADE, 5, 3, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsExceedsMaxBuildSizeFilledOpens() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    // fill empty slots
    fcm.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    fcm.move(PileType.CASCADE, 2, 6, PileType.OPEN, 1);
    fcm.move(PileType.CASCADE, 3, 6, PileType.OPEN, 2);
    fcm.move(PileType.CASCADE, 4, 5, PileType.OPEN, 3);
    // move one card
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    // move two cards
    fcm.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveMultipleCardsExceedsMaxBuildSizeEmptyCascades() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    for (int i = 0; i <= 6; i++) {
      fcm.move(PileType.CASCADE, 0, 6 - i, PileType.FOUNDATION, 0);
      if (6 - (i + 1) >= 0) {
        fcm.move(PileType.CASCADE, 4, 6 - (i + 1), PileType.FOUNDATION, 0);
      }
    }
    fcm.move(PileType.FOUNDATION, 0, 12, PileType.CASCADE, 0);
    // empty cascades is 1, max build size should be (4 + 1) * 2^1 = 10
    fcm.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 7); //1
    fcm.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 1); //2
    fcm.move(PileType.CASCADE, 1, 5, PileType.CASCADE, 7); //3
    fcm.move(PileType.CASCADE, 7, 4, PileType.CASCADE, 1); //4
    fcm.move(PileType.CASCADE, 1, 4, PileType.CASCADE, 7); //5
    fcm.move(PileType.CASCADE, 7, 3, PileType.CASCADE, 1); //6
    fcm.move(PileType.CASCADE, 1, 3, PileType.CASCADE, 7); //7
    fcm.move(PileType.CASCADE, 7, 2, PileType.CASCADE, 1); //8
    fcm.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 7); //9
    fcm.move(PileType.CASCADE, 7, 1, PileType.CASCADE, 1); //10
    fcm.move(PileType.CASCADE, 1, 1, PileType.CASCADE, 7); //11 (exceeds)
  }

  @Test
  public void moveMultipleCardsToEmptyCascades() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    for (int i = 0; i <= 6; i++) {
      fcm.move(PileType.CASCADE, 0, 6 - i, PileType.FOUNDATION, 0);
      if (6 - (i + 1) >= 0) {
        fcm.move(PileType.CASCADE, 4, 6 - (i + 1), PileType.FOUNDATION, 0);
      }
    }
    fcm.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 7);
    fcm.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 1);
    fcm.move(PileType.CASCADE, 1, 5, PileType.CASCADE, 7);
    fcm.move(PileType.CASCADE, 7, 4, PileType.CASCADE, 1);
    fcm.move(PileType.CASCADE, 1, 4, PileType.CASCADE, 7);
    fcm.move(PileType.CASCADE, 7, 3, PileType.CASCADE, 4);
    assertEquals("F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣",
        fcm.getGameState());
  }

  @Test
  public void moveMultipleCardsBackAndForth() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    fcm.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 5);
    fcm.move(PileType.CASCADE, 5, 5, PileType.CASCADE, 0);
    fcm.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 5);
    fcm.move(PileType.CASCADE, 5, 4, PileType.CASCADE, 0);
    fcm.move(PileType.CASCADE, 0, 4, PileType.CASCADE, 5);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 5♠, 4♥, 3♠, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣",
        fcm.getGameState());
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
  public void moveToEmptyCascade() {
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    for (int i = 0; i < 7; i++) {
      fcm.move(PileType.CASCADE, 0, 6 - i, PileType.FOUNDATION, 0);
      if (6 - (i + 1) >= 0) {
        fcm.move(PileType.CASCADE, 4, 6 - (i + 1), PileType.FOUNDATION, 0);
      }
    }
    fcm.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 0);
    assertEquals("F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: A♥\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
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
        + "C1:\nC2:\nC3:\nC4:",
        fcm.getGameState());
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
        + "C1:\nC2:\nC3:\nC4:\nC5:\nC6:\nC7:\nC8:",
        fcm.getGameState());
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

  @Test
  public void moveCantAfterGameOver() {
    moveWin8Cascade();
    fcm.move(PileType.FOUNDATION, 0, 12, PileType.OPEN, 0);
    assertEquals("F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\nC2:\nC3:\nC4:\nC5:\nC6:\nC7:\nC8:",
        fcm.getGameState());
  }
}
