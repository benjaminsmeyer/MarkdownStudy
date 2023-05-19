package cs3500.pa02.study.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the StudyUser class
 */
class StudyUserTest {

  private User study;

  /**
   * Runs before each test
   */
  @BeforeEach
  void setup() {
    PrintStream printStream = new PrintStream(System.out);
    study = new StudyUser(System.in, printStream);
  }

  /**
   * Tests if the user returns the expected response
   */
  @Test
  void askUserTest() {
    String input = "test";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);
    User userInput = new StudyUser(inputStream, ps);
    String actual = userInput.askUser("hello");
    assertEquals("test", actual);
  }

  /**
   * Tests if the user returns null if pressed return
   */
  @Test
  void askUserReturnsNullTest() {
    String userInput = "";

    ByteArrayInputStream response = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(response);

    String actual = study.askUser("hello");

    ByteArrayOutputStream message = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(message);
    System.setOut(printStream);

    assertNull(actual);
  }

  /**
   * Tests if the user sends expected message
   */
  @Test
  void sendMessageTest() {
    study.sendMessage("MESSAGE SENT");
  }

  /**
   * Tests if the user sends expected message in red
   */
  @Test
  void sendMessageInRedTest() {
    study.sendMessageInRed("MESSAGE SENT");
  }
}