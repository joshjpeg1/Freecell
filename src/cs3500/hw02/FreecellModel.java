package cs3500.hw02;

import cs3500.hw02.card.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh_jpeg on 5/14/17.
 */
public class FreecellModel implements FreecellOperations<Card> {
  List<Card> deck;

  public FreecellModel() {
    this.deck = getDeck();
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
