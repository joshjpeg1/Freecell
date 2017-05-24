package cs3500.hw03;

import cs3500.hw02.PileType;

/**
 * Created by josh_jpeg on 5/24/17.
 */
public class PileCommand implements Command {
  private PileType pileType;
  private int pileNumber;

  public PileCommand(PileType pileType, int pileNumber) throws IllegalArgumentException {
    if (pileType == null) {
      throw new IllegalArgumentException("Cannot assign null to pile type.");
    }
    this.pileType = pileType;
    this.pileNumber = pileNumber;
  }

  public PileType getPileType() {
    return this.pileType;
  }

  public int getPileNumber() {
    return this.pileNumber;
  }
}
