package cs3500.hw02;

import cs3500.hw02.card.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by josh_jpeg on 5/14/17.
 */
public class FreecellModel implements FreecellOperations<Card> {
  private Random rand;
  public List<List<Card>> cascades;
  private Card[] opens;
  private List<List<Card>> foundations;

  public FreecellModel() {
    this.rand = new Random();
    this.foundations = new ArrayList<>(4);
    this.startGame(this.getDeck(), 8, 4, true);
  }

  public FreecellModel(int numCascadePiles, int numOpenPiles, boolean shuffle) {
    rand = new Random();
    foundations = new ArrayList<>(4);
    this.startGame(this.getDeck(), numCascadePiles, numOpenPiles, shuffle);
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (int i = 1; i <= 13; i += 1) {
      for (Suit suit : Suit.values()) {
        deck.add(new Card(i, suit));
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) throws IllegalArgumentException {
    if (numCascadePiles < 4 || numCascadePiles > 8) {
      throw new IllegalArgumentException("Invalid number of cascade piles.");
    }
    if (numOpenPiles < 1 || numOpenPiles > 4) {
      throw new IllegalArgumentException("Invalid number of open piles.");
    }
    if (deck.size() != 52 || !Utils.noDuplicates(deck)) {
      throw new IllegalArgumentException("Invalid deck.");
    }

    this.foundations = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      this.foundations.add(new ArrayList<>());
    }
    this.cascades = new ArrayList<>();
    for (int i = 0; i < numCascadePiles; i++) {
      this.cascades.add(new ArrayList<>());
    }
    this.opens = new Card[numOpenPiles];

    if (shuffle) {
      Utils.shuffle(deck);
    }

    while (deck.size() > 0) {
      for (int i = 0; i < this.cascades.size(); i++) {
        if (deck.size() > 0) {
          this.cascades.get(i).add(deck.remove(0));
        }
      }
    }
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
    String str = "";
    for (int i = 0; i < this.foundations.size(); i++) {
      str += "F" + (i + 1) + ":" + Utils.listToString(this.foundations.get(i)) + "\n";
    }
    for (int i = 0; i < this.opens.length; i++) {
      str += "O" + (i + 1) + ":" + ((this.opens[i] != null) ? this.opens[i].toString() : "") + "\n";
    }
    for (int i = 0; i < this.cascades.size(); i++) {
      str += "C" + (i + 1) + ":" + Utils.listToString(this.cascades.get(i));
      if (i < this.cascades.size() - 1) {
        str += "\n";
      }
    }
    return str;
  }
}
