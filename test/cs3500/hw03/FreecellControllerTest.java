package cs3500.hw03;

import cs3500.hw02.FreecellModel;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Created by josh_jpeg on 5/23/17.
 */
public class FreecellControllerTest {

  // Tests for the constructor
  @Test(expected = IllegalStateException.class)
  public void constructorNullRd() {
    new FreecellController(null, new StringWriter());
  }

  @Test(expected = IllegalStateException.class)
  public void constructorNullAp() {
    new FreecellController(new StringReader("hey"), null);
  }

  @Test(expected = IllegalStateException.class)
  public void constructorBothNull() {
    new FreecellController(null, null);
  }

  // Tests for the playGame() method
  @Test(expected = IllegalArgumentException.class)
  public void playGameNullDeck() {
    IFreecellController fcc = new FreecellController(new StringReader(""), new StringWriter());
    fcc.playGame(null, new FreecellModel(), 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameNullModel() {
    IFreecellController fcc = new FreecellController(new StringReader(""), new StringWriter());
    fcc.playGame(new FreecellModel().getDeck(), null, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameBothNull() {
    IFreecellController fcc = new FreecellController(new StringReader(""), new StringWriter());
    fcc.playGame(null, null, 8, 4, false);
  }
}
