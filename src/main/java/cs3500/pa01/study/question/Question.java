package cs3500.pa01.study.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles the question, answer, and mark
 */
public class Question {
  private String question;
  private String answer;
  private Mark mark;
  private static List<Question> allQuestions = new ArrayList<>();

  /**
   * Initializes the question
   *
   * @param text the string values of each component of the question
   */
  public Question(String text) {
    String[] textSplit = text.split(":::");
    if (!(textSplit.length == 2 || textSplit.length == 3)) {
      throw new IllegalArgumentException("Invalid argument. It must be a question and an "
          + "answer in the following format: question:::answer OR question:::answer:::mark.");
    }
    question = textSplit[0].trim();
    answer = textSplit[1].trim();
    if (textSplit.length == 3) {
      try {
        mark = Mark.valueOf(textSplit[2].trim());
      } catch (IllegalArgumentException e) {
        mark = Mark.HARD;
      }
    } else {
      mark = Mark.HARD;
    }
    append(this);
  }

  /**
   * Initializes the question
   *
   * @param values the string values of each component of the question
   */
  public Question(String[] values) {
    if (!checkQuestionLength(values)) {
      throw new IllegalArgumentException("Invalid argument. It must be a question, an answer, "
      + "and a mark in the following format: question:::answer OR question:::answer:::mark");
    }
    question = values[0].trim();
    answer = values[1].trim();
    try {
      String markAsString = values[2].toUpperCase().trim();
      mark = Mark.valueOf(markAsString);
    } catch (Exception e) { // If there is an error trying to get the mark, set it as Mark.HARD
      mark = Mark.HARD;
    }
    append(this);
  }

  /**
   *
   */
  @Override
  public String toString() {
    return "[" + getQuestion() + ":::" + getAnswer() + ":::" + getMark().toString() + "]";
  }

  /**
   * Checks the length of the values.
   *
   * @param values the values containing the questions
   * @return true if the values are the correct length, false otherwise.
   */
  private static boolean checkQuestionLength(String[] values) {
    return values.length == 2 || values.length == 3;
  }

  /**
   * Checks if the text contains the three colons
   *
   * @param text the string of text
   * @return true if the text contains a question and an answer, false if not.
   */
  public static boolean checkForQuestion(String text) {
    return text.contains(":::");
  }

  /**
   * Adds question to the list of questions found
   *
   * @param text the string of text
   */
  public static void addQuestion(String text) {
    if (checkForValidQuestion(text)) {
      if (text.indexOf("-") == 0) {
        text = text.substring(1); // removes bullet point from text
      }
      Question currentQuestion = new Question(text.trim());
      Question.append(currentQuestion);
    }
  }

  /**
   * Checks if the text is a valid question and not just space.
   *
   * @param text the string of text
   * @return true if the text contains a question and an answer, false if not.
   */
  public static boolean checkForValidQuestion(String text) {
    String[] splitText = text.split(":::");
    boolean correctLength = checkQuestionLength(splitText);
    if (checkForQuestion(text)) {
      for (String str : splitText) {
        if (str.trim().length() == 0) {
          return false;
        }
      }
    }
    return correctLength;
  }

  /**
   * Returns the question.
   *
   * @return the question
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Returns a list of questions with their answers and marks.
   *
   * @return a list of questions found with their answers and marks.
   */
  public static List<Question> getAllQuestions() {
    return allQuestions;
  }

  /**
   * Adds question to the list of questions
   *
   * @param question the question to add
   */
  public static void append(Question question) {
    if (!contains(question)) {
      allQuestions.add(question);
    }
  }

  /**
   * Checks if the question is already in questions
   *
   * @param question the question to check
   * @return true if question is already in questions, false otherwise.
   */
  private static boolean contains(Question question) {
    for (Question current : allQuestions) {
      if (Objects.equals(current.question, question.question)) {
        return true;
      } else if (Objects.equals(current.answer, question.answer)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the answer of the question.
   *
   * @return the answer
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * Returns the mark of the question
   *
   * @return the mark
   */
  public Mark getMark() {
    return mark;
  }

  /**
   * Updates the mark to a new mark
   *
   * @param mark the new mark
   */
  public void updateMark(Mark mark) {
    if (!sameMark(mark)) {
      this.mark = mark;
    }
  }

  /**
   * Reset allQuestions list
   */
  public static void resetAllQuestionsList() {
    allQuestions = new ArrayList<>();
  }

  /**
   * Checks if the mark is already set as a certain mark.
   *
   * @param mark the mark to check
   * @return true if the mark is the same, false otherwise
   */
  public boolean sameMark(Mark mark) {
    return this.mark == mark;
  }

  /**
   * An enum of the possible marks a question could get
   */
  public enum Mark {
    EASY,
    HARD
  }

}
