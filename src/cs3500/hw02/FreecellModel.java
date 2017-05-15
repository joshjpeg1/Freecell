package cs3500.hw02;

import cs3500.hw02.card.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by josh_jpeg on 5/14/17.
 */
public class FreecellModel implements FreecellOperations<Card> {
  Random rand;
  List<Card> deck;
  List<Card>[] cascades;
  Card[] opens;
  List<Card>[] foundations;

  public FreecellModel() {
    rand = new Random();
    foundations = new List[4];
    this.startGame(this.getDeck(), 8, 4, true);
  }

  public FreecellModel(int numCascadePiles, int numOpenPiles, boolean shuffle) {
    rand = new Random();
    foundations = new List[4];
    this.startGame(this.getDeck(), numCascadePiles, numOpenPiles, shuffle);
  }

  @Override
  public List<Card> getDeck() {
    List<Card> tempDeck = new ArrayList<Card>();
    for (int i = 1; i <= 13; i += 1) {
      for (Suit suit : Suit.values()) {
        tempDeck.add(new Card(i, suit));
      }
    }
    return tempDeck;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) throws IllegalArgumentException {
    if (numCascadePiles < 4 || numCascadePiles > 8) {
      throw new IllegalArgumentException("Invalid number of cascade piles.");
    }
    cascades = new List[numCascadePiles];

    if (numOpenPiles < 1 || numOpenPiles > 4) {
      throw new IllegalArgumentException("Invalid number of open piles.");
    }
    opens = new Card[numOpenPiles];

    if (shuffle) {
      List<Card> tempDeck = new ArrayList<>();
      for (Card c : deck) {
        tempDeck.add(c);
      }
      deck.clear();
      while (tempDeck.size() > 0) {
        int index = rand.nextInt(tempDeck.size());
        deck.add(tempDeck.remove(index));
      }
    }
    this.deck = deck;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination, int destPileNumber) throws IllegalArgumentException {

  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public String getGameState() {
    return null;
  }
}
