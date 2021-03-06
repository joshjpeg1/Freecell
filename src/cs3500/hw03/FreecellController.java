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
  public FreecellController(Readable rd, Appendable ap) {
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public void playGame(List<ISlot> deck, FreecellOperations<ISlot> model, int numCascades,
                       int numOpens, boolean shuffle)
                       throws IllegalStateException, IllegalArgumentException {
    if (this.rd == null || this.ap == null) {
      throw new IllegalStateException("Cannot read or append to null.");
    } else if (deck == null || model == null) {
      throw new IllegalArgumentException("Cannot start a game with a null deck or model.");
    }
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      this.appendMsg("Could not start game.");
      return;
    }
    Scanner scan = new Scanner(this.rd);
    while (true) {
      this.appendMsg(model.getGameState());
      if (model.isGameOver()) {
        this.appendMsg("\nGame over.");
        return;
      }
      try {
        this.moveFromInput(scan, model);
      } catch (IllegalStateException e) {
        this.appendMsg("\nGame quit prematurely.");
        return;
      }
      this.appendMsg("\n");
    }
  }

  /**
   * Performs a move using input from the {@code rd} object. Handles any errors in input and
   * asks for more accordingly.
   *
   * @param model       the model of the Freecell game to be played
   * @throws IllegalStateException when the input is "q" or "Q" for quit
   */
  private void moveFromInput(Scanner scan, FreecellOperations<ISlot> model)
                             throws IllegalStateException {
    while (true) {
      Move nextMove = new Move();
      this.appendMsg("\nPlease enter a new move (e.g.: C1 7 F2).");
      while (!nextMove.searchingFor().equals(SearchState.FINISHED) && scan.hasNext()) {
        String next = scan.next();
        if (next.equalsIgnoreCase("q")) {
          throw new IllegalStateException("To exit out of grandparent while loop.");
        }
        if (!validateCommand(next, nextMove)) {
          this.appendMsg("\nInvalid " + nextMove.searchingFor().toString()
              + " (Given: \"" + next + "\"). Try again.");
        }
      }
      try {
        nextMove.tryMove(model);
        return;
      } catch (IllegalArgumentException e) {
        this.appendMsg("\nInvalid move. Try again.\nREASON: " + e.getMessage());
      }
    }
  }

  /**
   * Appends the given message to the {@code ad} object.
   *
   * @param message    the message to be appended
   */
  private void appendMsg(String message) {
    try {
      this.ap.append(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if the given String input is a valid command, and if so, gives the {@code Move} object
   * the data.
   *
   * @param input   the possible command
   * @param move    the move to be mutated if the command is valid
   * @return true if the input is a command, false otherwise
   * @throws IllegalArgumentException if the given {@code Move} object has not been initialized
   */
  private boolean validateCommand(String input, Move move) {
    if (move == null) {
      throw new IllegalArgumentException("Move has not been initialized.");
    }
    if (input != null && input.length() > 0) {
      SearchState searchFor = move.searchingFor();
      input = input.toLowerCase();
      Character identifier = input.charAt(0);
      try {
        if (Character.isDigit(identifier) && searchFor.equals(SearchState.CARD_INDEX)) {
          move.setCardIndex(Integer.parseInt(input) - 1);
          return true;
        } else if (Character.isLetter(identifier) && (searchFor.equals(SearchState.SOURCE_PILE)
                   || searchFor.equals(SearchState.DEST_PILE))) {
          boolean addToSource = searchFor.equals(SearchState.SOURCE_PILE);
          if (identifier == 'c' || identifier == 'o' || identifier == 'f') {
            int pileNumber = Integer.parseInt(input.substring(1)) - 1;
            if (identifier == 'c') {
              move.setPile(PileType.CASCADE, pileNumber, addToSource);
            } else if (identifier == 'o') {
              move.setPile(PileType.OPEN, pileNumber, addToSource);
            } else {
              move.setPile(PileType.FOUNDATION, pileNumber, addToSource);
            }
            return true;
          }
        }
      } catch (NumberFormatException e) {
        // only will catch if parseInt fails (invalid input), will fall to 'return false' statement
      }
    }
    return false;
  }
}
