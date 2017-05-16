package cs3500.hw02.slot;

import cs3500.hw02.PileType;

/**
 * Represents an empty slot in a game of Freecell.
 */
public class EmptySlot extends ASlot {
  /**
   * Constructs a {@code EmptySlot} object.
   */
  public EmptySlot() {}

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof EmptySlot)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  /**
   * Returns a {@code EmptySlot} as a String.
   *
   * @return the empty slot as a String
   */
  @Override
  public String toString() {
    return "";
  }

  /**
   * Returns whether moving the given {@code CardSlot} onto this {@code EmptySlot} in the given
   * pile is possible.
   *
   * @param from       the card moving on this ASlot
   * @param where      the pile this is located
   * @return whether the given card can be moved on this ASlot in the given pile
   */
  @Override
  protected boolean moveTo(CardSlot from, PileType where) {
    if (where.equals(PileType.FOUNDATION)) {
      return from.value == CardSlot.ACE;
    }
    return true;
  }
}
