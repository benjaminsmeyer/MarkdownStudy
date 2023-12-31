package cs3500.pa02.study.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa01.gen.FileReader;
import cs3500.pa02.study.model.Question;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Study Session Controller Class
 */
class StudySessionImplTest {

  /**
   * Runs before each question to set up their testing variables
   */
  @BeforeEach
  void setup() {
    Question.resetAllQuestionsList();
  }

  /**
   * Tests the session for the expected output
   */
  @Test
  void startSessionWithArgsTest() {
    String userInput = "1\n2\n3\n-1\nn\ne\n-1\nn\n100\n1\n4\n4\ny";

    ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);

    String filePath = "src/test/questions/sessionquestions.sr";
    Random random = new Random(1);
    int amountQuestions = 12;
    StudySession test = new StudySessionImpl(filePath, amountQuestions, random, inputStream, ps);
    test.startSession();

    String expectedOne = FileReader.readFromFile(new File("src/test/questions/"
        + "expectedsessionquestionsone.sr"));
    String expectedTwo = FileReader.readFromFile(new File("src/test/questions/"
        + "expectedsessionquestionstwo.sr"));
    String actual = FileReader.readFromFile(new File("src/test/questions/"
        + "sessionquestions.sr"));

    if (expectedOne.equals(actual)) {
      assertEquals(expectedOne, actual);
    } else {
      assertEquals(expectedTwo, actual);
    }
  }

  /**
   * Tests the constructors with args
   */
  @Test
  void contructorWithArgs() {
    String userInput = "1\n2";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);

    int amountQuestions = 15;
    Random random = new Random(1);
    String filePath = "invalidPath";
    StudySession test = new StudySessionImpl(filePath, amountQuestions, random, inputStream, ps);
    assertThrows(Exception.class,
        () -> test.startSession());
  }

  /**
   * Tests the constructors without args
   */
  @Test
  void constructorWithoutArgsTest() {
    String userInput = "red.sr\nsrc/test/outputResults/works.md\n"
        + "green\nsrc/test/questions/questions.sr\n20\n1";

    ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);

    StudySession testOne = new StudySessionImpl(inputStream, ps);
    testOne.startSession();

    ByteArrayOutputStream message = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(message);
    System.setOut(printStream);

    userInput = "src/test/questions/randomquestionsfile.sr\n-100\n-100";

    inputStream = new ByteArrayInputStream(userInput.getBytes());
    byteArrayOutputStream = new ByteArrayOutputStream();
    ps = new PrintStream(byteArrayOutputStream);

    StudySession testTwo = new StudySessionImpl(inputStream, ps);
    testTwo.startSession();

    message = new ByteArrayOutputStream();
    printStream = new PrintStream(message);
    System.setOut(printStream);

    userInput = "no";

    inputStream = new ByteArrayInputStream(userInput.getBytes());
    byteArrayOutputStream = new ByteArrayOutputStream();
    ps = new PrintStream(byteArrayOutputStream);

    StudySession testThree = new StudySessionImpl(inputStream, ps);
    testThree.startSession();

    message = new ByteArrayOutputStream();
    printStream = new PrintStream(message);
    System.setOut(printStream);
  }

  /**
   * Tests if parseResponse works correctly
   */
  @Test
  void parseResponseTest() {
    assertEquals(10, StudySessionImpl.parseResponse("10"));
    assertEquals(2, StudySessionImpl.parseResponse("2"));
    assertEquals(0, StudySessionImpl.parseResponse("0"));
    assertEquals(-100, StudySessionImpl.parseResponse("-100"));
    assertEquals(-1, StudySessionImpl.parseResponse("100A"));
    assertEquals(-1, StudySessionImpl.parseResponse("hello"));
    assertEquals(-1, StudySessionImpl.parseResponse("five"));
  }
}