package cs3500.hw03;

import cs3500.hw02.FreecellOperations;

import java.util.List;

/**
 * This is the interface of the Freecell controller. It is parameterized over the
 * slot type, i.e. when you implement it, you can substitute K with your
 * implementation of a slot.
 */
public interface IFreecellController<K> {
  /**
   * Starts a new game of Freecell using the given model, number of cascade and open piles, and the
   * provided deck.
   *
   * @param deck            the deck to be dealt
   * @param model           the model of the Freecell game to be played
   * @param numCascades     number of cascade piles
   * @param numOpens        number of open piles
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalStateException if the input or output are uninitialized
   * @throws IllegalArgumentException if the deck or model are uninitialized
   */
  void playGame(List<K> deck, FreecellOperations<K> model, int numCascades, int numOpens,
                boolean shuffle) throws IllegalStateException;
}
