package cs3500.pa02.study.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Question class
 */
class QuestionTest {

  private Question test;

  /**
   * Runs before each test to set up testing variables
   */
  @BeforeEach
  void setup() {
    Question.resetAllQuestionsList();
    String[] testValues = new String[]{"hello", "world", "red"};
    test = new Question(testValues);
  }

  /**
   * Tests if toString returns correctly
   */
  @Test
  void toStringTest() {
    assertEquals("[hello:::world:::HARD]", test.toString());
  }

  /**
   * Tests if checkForQuestion returns correctly
   */
  @Test
  void checkForQuestionTest() {
    assertFalse(Question.checkForQuestion("hello"));
    assertTrue(Question.checkForQuestion("you:::too"));
    assertTrue(Question.checkForQuestion("you:::too:::world"));
    assertTrue(Question.checkForQuestion("you:::too:::hard"));
    assertTrue(Question.checkForQuestion("you:::too:::EASY"));
  }

  /**
   * Tests if addQuestion returns correctly
   */
  @Test
  void addQuestionTest() {
    assertEquals(1, Question.getAllQuestions().size());
    Question.addQuestion("hello");
    assertEquals(1, Question.getAllQuestions().size());
    Question.addQuestion("you:::too");
    assertEquals(2, Question.getAllQuestions().size());
    Question.addQuestion("you:::too:::world");
    assertEquals(2, Question.getAllQuestions().size());
    Question.addQuestion("you:::too:::hard");
    assertEquals(2, Question.getAllQuestions().size());
    Question.addQuestion("you:::too:::EASY");
    assertEquals(2, Question.getAllQuestions().size());
  }

  /**
   * Tests if checkForValidQuestion returns correctly
   */
  @Test
  void checkForValidQuestionTest() {
    assertFalse(Question.checkForValidQuestion("hello"));
    assertTrue(Question.checkForValidQuestion("you:::too"));
    assertTrue(Question.checkForValidQuestion("you:::too:::world"));
    assertTrue(Question.checkForValidQuestion("you:::too:::hard"));
    assertTrue(Question.checkForValidQuestion("you:::too:::EASY"));
  }

  /**
   * Tests if getQuestion returns correctly
   */
  @Test
  void getQuestionTest() {
    assertEquals("hello", test.getQuestion());
  }

  /**
   * Tests if getAllQuestions returns correctly
   */
  @Test
  void getAllQuestionsTest() {
    assertEquals(1, Question.getAllQuestions().size());
    assertEquals("[[hello:::world:::HARD]]", Question.getAllQuestions().toString());
  }

  /**
   * Tests if append runs correctly
   */
  @Test
  void appendTest() {
    assertEquals(1, Question.getAllQuestions().size());
    Question.append(test);
    assertEquals(1, Question.getAllQuestions().size());
    Question.append(new Question("123:::123:::lol"));
    assertEquals(2, Question.getAllQuestions().size());
  }

  /**
   * Tests if getAnswer returns correctly
   */
  @Test
  void getAnswerTest() {
    assertEquals("world", test.getAnswer());
  }

  /**
   * Tests if getMark returns correctly
   */
  @Test
  void getMarkTest() {
    assertEquals(Question.Mark.HARD, test.getMark());
  }

  /**
   * Tests if updateMark runs correctly
   */
  @Test
  void updateMarkTest() {
    test.updateMark(Question.Mark.HARD);
    assertTrue(test.sameMark(Question.Mark.HARD));
    test.updateMark(Question.Mark.EASY);
    assertTrue(test.sameMark(Question.Mark.EASY));
  }

  /**
   * Tests if sameMark returns correctly
   */
  @Test
  void sameMarkTest() {
    assertTrue(test.sameMark(Question.Mark.HARD));
    assertFalse(test.sameMark(Question.Mark.EASY));
  }

  /**
   * Tests if Constructors throw correctly
   */
  @Test
  void questionsConstructorThrowsTest() {
    assertThrows(IllegalArgumentException.class,
        () -> new Question(new String[]{}));
    assertThrows(IllegalArgumentException.class,
        () -> new Question(""));
    assertThrows(IllegalArgumentException.class,
        () -> new Question(new String[]{"HELLO", "WORLD", "TEST", "123"}));
  }

  /**
   * Tests if Constructors runs correctly
   */
  @Test
  void constructorTest() {
    String[] testValues = new String[]{"hello", "world", "hard"};
    Question two = new Question(testValues);
    testValues = new String[]{"hello", "world", "easy"};
    Question three = new Question(testValues);
    String testString = "you:::too";
    Question four = new Question(testString);
    testString = "you:::too:::world";
    Question five = new Question(testString);
    testString = "you:::too:::hard";
    Question six = new Question(testString);
    testString = "you:::too:::EASY";
    Question seven = new Question(testString);
  }
}