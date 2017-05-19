package cs3500.hw02.slot;

import cs3500.hw02.PileType;

/**
 * Represents an empty slot in a game of Freecell.
 */
public class EmptySlot implements ISlot {
  /**
   * Constructs a {@code EmptySlot} object.
   */
  public EmptySlot() {
    // EmptySlot has no fields to be initialized
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    return that instanceof EmptySlot;
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
   * Always returns true, as an {@code EmptySlot} can move anywhere since it's empty.
   *
   * @param to         the slot to be move on
   * @param where      the pile the desired slot is located
   * @return whether this empty can move to the other in the given pile
   * @throws IllegalArgumentException if given ISlot or PileType are null
   */
  @Override
  public boolean moveTo(ISlot to, PileType where) {
    if (to == null || where == null) {
      throw new IllegalArgumentException("ISlot or pile type are invalid.");
    }
    return true;
  }

  /**
   * Helper to the {@code moveTo} method. Returns whether moving the given {@code CardSlot} onto
   * this {@code EmptySlot} in the given pile is possible.
   *
   * @param from       the card moving on this EmptySlot
   * @param where      the pile this is located
   * @return whether the given card can be moved on this ISlot in the given pile
   * @throws IllegalArgumentException if given CardSlot or PileType are null
   */
  @Override
  public boolean moveFrom(CardSlot from, PileType where) {
    if (from == null || where == null) {
      throw new IllegalArgumentException("CardSlot or pile type are invalid.");
    }
    if (where.equals(PileType.FOUNDATION)) {
      return from.value.equals(CardValue.ACE);
    }
    return true;
  }
}
