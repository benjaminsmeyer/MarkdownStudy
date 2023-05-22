package cs3500.pa02.study.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Questions Data Handler Class
 */
class QuestionsDataHandlerTest {

  private QuestionsDataHandler dataHandlerOne;


  /**
   * Runs before each test
   */
  @BeforeEach
  void setup() {
    Question.resetAllQuestionsList();
    String filePath = "src/test/questions/questions.sr";
    int amountQuestions = 100;
    Random random = new Random(1);
    dataHandlerOne = new QuestionsDataHandler(filePath, amountQuestions, random);
  }

  /**
   * Tests if the randomQuestions returns correctly
   */
  @Test
  void getRandomQuestionsTest() {
    assertTrue(dataHandlerOne.getRandomQuestions().toString().contains("Which continent is known "
        + "as the \"Roof of the World\"?:::Asia.:::EASY"));
    assertFalse(dataHandlerOne.getRandomQuestions().toString().contains("YOLO"));
  }

  /**
   * Tests if the questionAnswered runs correctly
   */
  @Test
  void questionAnsweredTest() {
    Question test = dataHandlerOne.getRandomQuestions().get(0);
    dataHandlerOne.questionAnswered(test, Question.Mark.HARD);
    assertEquals(0, dataHandlerOne.getTotalQuestionsChangedEasyToHard());
    dataHandlerOne.questionAnswered(test, Question.Mark.EASY);
    assertEquals(1, dataHandlerOne.getTotalQuestionsChangedHardToEasy());
  }

  /**
   * Tests if the endResults runs correctly
   */
  @Test
  void endResultsTest() {
    Question test = dataHandlerOne.getRandomQuestions().get(0);
    dataHandlerOne.questionAnswered(test, Question.Mark.EASY);
    dataHandlerOne.questionAnswered(test, Question.Mark.HARD);
    dataHandlerOne.questionAnswered(test, Question.Mark.EASY);
    dataHandlerOne.questionAnswered(test, Question.Mark.HARD);
    Map<String, Integer> results = dataHandlerOne.endResults();

    assertEquals(4, results.get("TOTAL QUESTIONS ANSWERED"));
    assertEquals(2, results.get("TOTAL EASY QUESTIONS ANSWERED"));
    assertEquals(2, results.get("TOTAL HARD QUESTIONS ANSWERED"));
    assertEquals(2, results.get("TOTAL QUESTIONS CHANGED FROM EASY TO HARD"));
    assertEquals(2, results.get("TOTAL QUESTIONS CHANGED FROM HARD TO EASY"));
    assertEquals(2, results.get("UPDATED TOTAL NUMBER OF HARD QUESTIONS"));
    assertEquals(26, results.get("UPDATED TOTAL NUMBER OF EASY QUESTIONS"));
  }

  /**
   * Tests if the getTotalQuestionsAnswered runs correctly
   */
  @Test
  void getTotalQuestionsAnsweredTest() {
    Question test = dataHandlerOne.getRandomQuestions().get(0);
    dataHandlerOne.questionAnswered(test, Question.Mark.HARD);
    assertEquals(1, dataHandlerOne.getTotalQuestionsAnswered());
  }

  /**
   * Tests if the getAmountQuestions runs correctly
   */
  @Test
  void getAmountQuestionsTest() {
    assertEquals(28, dataHandlerOne.getAmountQuestions());
    QuestionsDataHandler dataHandlerTwo = new QuestionsDataHandler(
        "src/test/questions/morequestions.sr",
        10, new Random(1));
    assertEquals(10, dataHandlerTwo.getAmountQuestions());
  }

  /**
   * Tests if the initializeQuestions throws error when provided an invalid path
   */
  @Test
  void initializeQuestionsThrowsError() {
    assertThrows(RuntimeException.class,
        () -> new QuestionsDataHandler("blue", 10, new Random()));
  }
}