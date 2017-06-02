package cs3500.hw04;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;

/**
 * The factory class for creating new models for the game of Freecell.
 */
public class FreecellModelCreator {
  /**
   * Represents the different types of possible models.
   */
  public enum GameType { SINGLEMOVE, MULTIMOVE }

  /**
   * Creates a new Freecell model of the given {@code GameType}.
   *
   * @param type     the type of game model to create
   * @return a new Freecell model of the given game type
   * @throws IllegalArgumentException if the given GameType is uninitialized
   */
  public static FreecellOperations create(GameType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("GameType does not exist.");
    } else if (type.equals(GameType.SINGLEMOVE)) {
      return new FreecellModel();
    } else {
      return new FreecellMultimoveModel();
    }
  }
}
