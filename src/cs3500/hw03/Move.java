package cs3500.hw03;

import cs3500.hw02.FreecellOperations;
import cs3500.hw02.PileType;
import cs3500.hw02.slot.ISlot;

/**
 * Created by josh_jpeg on 5/24/17.
 */
public class Move {
  private PileType source;
  private int pileNumber;
  private int cardIndex;
  private PileType destination;
  private int destPileNumber;

  public Move() {
    this.source = null;
    this.pileNumber = -1;
    this.cardIndex = -1;
    this.destination = null;
    this.destPileNumber = -1;
  }

  @Override
  public String toString() {
    return "(" + pileToString(source) + ", " + pileNumber + ", " + cardIndex + ", "
        + pileToString(destination) + ", " + destPileNumber + ")";
  }

  public String pileToString(PileType pt) {
    if (pt == null) {
      return "NULL";
    }
    switch (pt) {
      case OPEN: return "open";
      case FOUNDATION: return "foundation";
      case CASCADE: return "cascade";
      default: return "UNKNOWN";
    }
  }

  public void setPile(PileType pileType, int pileNumber) {
    if (pileType != null) {
      if (this.source == null) {
        this.source = pileType;
        this.pileNumber = pileNumber;
      } else {
        this.destination = pileType;
        this.destPileNumber = pileNumber;
      }
    }
  }

  public void setCardIndex(int cardIndex) {
    this.cardIndex = cardIndex;
  }

  public boolean tryMove(FreecellOperations model) {
    try {
      model.move(this.source, this.pileNumber, this.cardIndex, this.destination,
          this.destPileNumber);
      //System.out.println(this.toString() + " : " + "PASS");
      return true;
    } catch (IllegalArgumentException e) {
      //System.out.println(this.toString() + " : " + e.getMessage());
      return false;
    }
  }
}
