package cs3500.hw02.slot;

import cs3500.hw02.PileType;

/**
 * Represents an abstract slot in a game of Freecell.
 */
public abstract class ASlot {
  /**
   * Returns whether moving this {@code ASlot} onto the given {@code ASlot} in the given
   * pile is possible.
   *
   * @param to         the slot to be move on
   * @param where      the pile the desired slot is located
   * @return whether this slot can move to the other in the given pile
   */
  public boolean moveTo(ASlot to, PileType where) {
    return to.moveTo(this, where);
  }

  /**
   * Returns whether moving the given {@code CardSlot} onto this {@code ASlot} in the given
   * pile is possible.
   *
   * @param from       the card moving on this slot
   * @param where      the pile this is located
   * @return whether the given card can be moved on this ASlot in the given pile
   */
  protected boolean moveTo(CardSlot from, PileType where) {
    return false;
  }

  /**
   * Always returns true, as an {@code EmptySlot} can move anywhere since it's empty.
   *
   * @param from       the empty moving on this slot
   * @param where      the pile this is located
   * @return if an empty slot can move onto this slot
   */
  protected boolean moveTo(EmptySlot from, PileType where) {
    return true;
  }
}
