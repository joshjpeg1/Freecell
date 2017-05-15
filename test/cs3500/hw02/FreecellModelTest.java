package cs3500.hw02;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

/**
 * Created by josh_jpeg on 5/14/17.
 */
public class FreecellModelTest {
  FreecellModel fcm = new FreecellModel();

  @Test
  public void getDeckExample1() {
    assertEquals(52, fcm.getDeck().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameExample1() {
    fcm.startGame(new ArrayList<>(), 3, 3, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameExample2() {
    fcm.startGame(new ArrayList<>(), 9, 3, true);
  }

  @Test
  public void startGameExample3() {
    fcm.startGame(new ArrayList<>(), 4, 3, true);
  }

  @Test
  public void startGameExample4() {
    fcm.startGame(new ArrayList<>(), 8, 3, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameExample5() {
    fcm.startGame(new ArrayList<>(), 6, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameExample6() {
    fcm.startGame(new ArrayList<>(), 6, 5, true);
  }

  @Test
  public void startGameExample7() {
    fcm.startGame(new ArrayList<>(), 6, 1, true);
  }

  @Test
  public void startGameExample8() {
    fcm.startGame(new ArrayList<>(), 6, 4, true);
  }
}
