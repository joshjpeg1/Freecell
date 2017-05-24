package cs3500.hw03;

import cs3500.hw02.FreecellOperations;
import cs3500.hw02.slot.ISlot;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the controller for a game of Freecell.
 */
public class FreecellController implements IFreecellController<ISlot> {
  private final Readable rd;
  private final Appendable ap;
  private final Scanner scan;

  /**
   * Constructs a {@code FreecellController} object.
   *
   * @param rd     a Readable object to receive input from
   * @param ap     an Appendable object to transmit output to
   * @throws IllegalStateException if either {@code rd} or {@code ap} are null
   */
  public FreecellController(Readable rd, Appendable ap) throws IllegalStateException {
    if (rd == null || ap == null) {
      throw new IllegalStateException("Cannot read or append to null.");
    }
    this.rd = rd;
    this.ap = ap;
    this.scan = new Scanner(this.rd);
  }

  @Override
  public void playGame(List<ISlot> deck, FreecellOperations<ISlot> model, int numCascades,
                       int numOpens, boolean shuffle) throws IllegalStateException {
    if (deck == null || model == null) {
      throw new IllegalArgumentException("Cannot start a game with a null deck or model.");
    }
    try {
      try {
        model.startGame(deck, numCascades, numOpens, shuffle);
      } catch (IllegalArgumentException e) {
        ap.append("The given arguments are invalid. Reason: " + e.getMessage());
        return;
      }
      ap.append(model.getGameState());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
