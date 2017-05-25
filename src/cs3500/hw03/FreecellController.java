package cs3500.hw03;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;
import cs3500.hw02.PileType;
import cs3500.hw02.slot.ISlot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the controller for a game of Freecell.
 */
public class FreecellController implements IFreecellController<ISlot> {
  private final Readable rd;
  private final Appendable ap;

  public static void main(String[] argv) {
    FreecellController fcc = new FreecellController(new InputStreamReader(System.in), System.out);
    FreecellOperations<ISlot> fcm = new FreecellModel();
    fcc.playGame(fcm.getDeck(), fcm, 8, 4, false);
  }

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
                       int numOpens, boolean shuffle)
                       throws IllegalStateException, IllegalArgumentException {
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
      while (true) {
        this.ap.append(model.getGameState());
        Move nextMove = new Move();
        if (model.isGameOver()) {
          this.ap.append("\nGame over.");
          return;
        }
        while (!nextMove.tryMove(model) && scan.hasNext()) {
          String next = scan.next();
          if (next.equalsIgnoreCase("q")) {
            this.ap.append("\nGame quit prematurely.");
            return;
          }
          Move prevMove = nextMove;
          if (!validateCommand(next, nextMove, numCascades, numOpens)) {
            this.ap.append("\nUnknown input. Try again.");
          } else if (prevMove.equals(nextMove)) {
            // VALID COMMAND, BUT INVALID PILE NUMBER
          }
        }
        this.ap.append("\n");
      }
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
  private boolean validateCommand(String input, Move move, int numCascades, int numOpens) {
    if (input != null && input.length() > 0) {
      input = input.toLowerCase();
      Character identifier = input.charAt(0);
      try {
        if (Character.isDigit(identifier)) {
          move.setCardIndex(Integer.parseInt(input));
          return true;
        } else if (Character.isLetter(identifier)) {
          if (identifier == 'c' || identifier == 'o' || identifier == 'f') {
            int pileNumber = Integer.parseInt(input.substring(1));
            switch (identifier) {
              case 'c':
                if (withinRange(pileNumber, 3, numCascades, true, false)) {
                  move.setPile(PileType.CASCADE, pileNumber);
                }
                break;
              case 'o':
                if (withinRange(pileNumber, 3, numCascades, true, false)) {
                  move.setPile(PileType.CASCADE, pileNumber);
                }
                break;
              default:
                if (withinRange(pileNumber, 0, 4, true, false)) {
                  move.setPile(PileType.FOUNDATION, pileNumber);
                }
            }
            return true;
          }
        }
      } catch (NumberFormatException e) {}
    }
    return false;
  }

  /**
   * Checks if the given value is within range of the upper and lower, either inclusive or exclusive
   * as determined by the given booleans.
   *
   * @param given   the value being checked
   * @param lower   the lower bound of the range
   * @param upper   the upper bound of the range
   * @param lowInc  true if the lower bound is inclusive, false otherwise
   * @param uppInc  true if the upper bound is inclusive, false otherwise
   * @return whether the given value is within range of the lower and upper
   */
  private boolean withinRange(int given, int lower, int upper, boolean lowInc, boolean uppInc) {
    boolean inRange;
    if (lowInc) {
      inRange = given >= lower;
    } else {
      inRange = given > lower;
    }
    if (uppInc) {
      return inRange && (given <= upper);
    } else {
      return inRange && (given < upper);
    }
  }
}
