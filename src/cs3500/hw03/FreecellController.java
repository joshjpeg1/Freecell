package cs3500.hw03;

import cs3500.hw02.FreecellOperations;
import cs3500.hw02.slot.ISlot;

import java.util.List;
import java.util.Scanner;

/**
 * Created by josh_jpeg on 5/23/17.
 */
public class FreecellController implements IFreecellController<ISlot> {
  private Readable rd;
  private Appendable ap;
  private Scanner scan;

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
  }
}
