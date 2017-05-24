package cs3500.hw03;

import cs3500.hw02.FreecellOperations;
import cs3500.hw02.PileType;
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

  /**
   * Constructs a {@code FreecellController} object.
   *
   * @param rd     a Readable object to receive input from
   * @param ap     an Appendable object to transmit output to
   */
  public FreecellController(Readable rd, Appendable ap){
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public void playGame(List<ISlot> deck, FreecellOperations<ISlot> model, int numCascades,
                       int numOpens, boolean shuffle) throws IllegalStateException {
    if (rd == null || ap == null) {
      throw new IllegalStateException("Cannot read or append to null.");
    } else if (deck == null || model == null) {
      throw new IllegalArgumentException("Cannot start a game with a null deck or model.");
    }
    try {
      try {
        model.startGame(deck, numCascades, numOpens, shuffle);
      } catch (IllegalArgumentException e) {
        this.ap.append("Could not start game.");
        return;
      }

      Scanner scan = new Scanner(this.rd);

      while (scan.hasNext()) {
        this.ap.append(model.getGameState());
        if (model.isGameOver()) {
          this.ap.append("\nGame over.");
          return;
        } else {
          String next = scan.next().toLowerCase();
          if (next == null) {
            this.ap.append("\nCannot give null as an argument. Try again.");
          } else if (next.equals("q")) {
            this.ap.append("\nGame quit prematurely.");
            return;
          } else if (validCommand(next) != null) {

          } else {
            this.ap.append("\nUnkown input. Try again.");
          }
          System.out.println("NEW: " + next);
        }
      }

      scan.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if the given String input is a valid command (either for a pile or card index, as
   * indicated by the {@code lookForPile} boolean).
   *
   * @param input         the possible command
   * @return true if the input is a command, false otherwise
   */
  private Command validCommand(String input) {
    if (input != null || input.length() > 0) {
      Character identifier = input.charAt(0);
      if (Character.isDigit(identifier)) {
        return new CardCommand(Integer.parseInt(input));
      } else if (Character.isLetter(identifier)) {
        if (identifier == 'c' || identifier == 'o' || identifier == 'f') {
          try {
            int pileNumber = Integer.parseInt(input);
            switch (identifier) {
              case 'c':
                return new PileCommand(PileType.CASCADE, pileNumber);
              case 'o':
                return new PileCommand(PileType.OPEN, pileNumber);
              case 'f':
                return new PileCommand(PileType.FOUNDATION, pileNumber);
              default:
                return null;
            }
          } catch (NumberFormatException e) {}
        }
      }
    }
    return null;
  }
}
