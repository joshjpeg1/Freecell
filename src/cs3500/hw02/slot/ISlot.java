package cs3500.hw02.slot;

import cs3500.hw02.PileType;

/**
 * Represents an abstract slot in a game of Freecell.
 */
public interface ISlot {
  /**
   * Returns whether moving this {@code ISlot} onto the given {@code ISlot} in the given
   * pile is possible.
   *
   * @param to         the slot to be move on
   * @param where      the pile the desired slot is located
   * @return whether this slot can move to the other in the given pile
   */
  boolean moveTo(ISlot to, PileType where);

  /**
   * Returns whether moving the given {@code CardSlot} onto this {@code ISlot} in the given
   * pile is possible.
   *
   * @param from       the card moving on this slot
   * @param where      the pile this is located
   * @return whether the given card can be moved on this ISlot in the given pile
   */
  boolean moveFrom(CardSlot from, PileType where);
}
