package cs3500.pa01.study.question;

import cs3500.pa01.gen.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Handles the questions data for the study session
 */
public class QuestionsDataHandler {

  private int totalQuestionsAnswered;
  private int totalEasyQuestionsAnswered;
  private int totalHardQuestionsAnswered;
  private int totalQuestionsChangedEasyToHard;
  private int totalQuestionsChangedHardToEasy;
  private int updatedTotalNumberOfHardQuestions;
  private int updatedTotalNumberOfEasyQuestions;
  private int amountQuestions;
  private List<Question> allHardQuestions;
  private List<Question> allEasyQuestions;
  private final List<Question> randomQuestions;
  private final String filePath;

  /**
   * Initializes the questions data handler for the session
   *
   * @param filePath the file path containing the questions
   * @param amountQuestions the amount of questions requested
   * @param random the random seed
   */
  public QuestionsDataHandler(String filePath, int amountQuestions, Random random) {
    this.filePath = filePath;

    // INITIALIZE QUESTIONS
    try {
      initializeQuestions();
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize questions.");
    }

    // SETS THE AMOUNT OF QUESTIONS FOR SESSION
    this.amountQuestions = Math.min(amountQuestions, Question.getAllQuestions().size());
    randomQuestions = generateRandomListOfQuestions(amountQuestions, random);

    // SET COUNT
    totalQuestionsAnswered = 0;
    totalQuestionsChangedEasyToHard = 0;
    totalQuestionsChangedHardToEasy = 0;
    updatedTotalNumberOfHardQuestions = 0;
    updatedTotalNumberOfEasyQuestions = 0;
  }

  /**
   * Get random questions
   *
   * @return a list of random questions
   */
  public List<Question> getRandomQuestions() {
    return randomQuestions;
  }

  /**
   * Generates a list of random questions.
   *
   * @param amountQuestions amount of questions being asked
   * @return a list of random questions
   */
  private List<Question> generateRandomListOfQuestions(int amountQuestions, Random random) {
    Collections.shuffle(allHardQuestions, random);
    Collections.shuffle(allEasyQuestions, random);
    List<Question> randomQuestions = new ArrayList<>();

    if (amountQuestions < Question.getAllQuestions().size()) {
      for (int i = 0; i < amountQuestions; i++) {
        if (i < allHardQuestions.size()) {
          Question hardQuestion = allHardQuestions.get(i);
          randomQuestions.add(hardQuestion);
        } else if (i < allEasyQuestions.size()) {
          int index = Math.abs(allHardQuestions.size() - i);
          Question easyQuestion = allEasyQuestions.get(index);
          randomQuestions.add(easyQuestion);
        }
      }
    } else {
      randomQuestions.addAll(allHardQuestions);
      randomQuestions.addAll(allEasyQuestions);
    }

    return randomQuestions;
  }

  /**
   * Counts the question when answered.
   *
   * @param question the question that was answered
   */
  public void questionAnswered(Question question, Question.Mark mark) {
    totalQuestionsAnswered++;
    if (mark == Question.Mark.HARD) {
      totalHardQuestionsAnswered++;
    } else {
      totalEasyQuestionsAnswered++;
    }
    if (!question.sameMark(mark)) {
      question.updateMark(mark);
      countChange(mark);
    }
  }

  /**
   * Counts the mark that changed
   *
   * @param mark the mark of the question
   */
  private void countChange(Question.Mark mark) {
    if (mark == Question.Mark.HARD) {
      totalQuestionsChangedEasyToHard++;
    } else {
      totalQuestionsChangedHardToEasy++;
    }
  }

  /**
   * Sends in the final results from the session
   *
   * @return a Map with the final results
   */
  public Map<String, Integer> endResults() {
    calcResults();
    Map<String, Integer> results = addResults();
    return results;
  }

  /**
   * Adds the results to the hash map with their respective names
   * @return a Map with the final results
   */
  private Map<String, Integer>  addResults() {
    Map<String, Integer> results = new HashMap<>();
    results.put("TOTAL QUESTIONS ANSWERED", totalQuestionsAnswered);
    results.put("TOTAL EASY QUESTIONS ANSWERED", totalEasyQuestionsAnswered);
    results.put("TOTAL HARD QUESTIONS ANSWERED", totalHardQuestionsAnswered);
    results.put("TOTAL QUESTIONS CHANGED FROM EASY TO HARD", totalQuestionsChangedEasyToHard);
    results.put("TOTAL QUESTIONS CHANGED FROM HARD TO EASY", totalQuestionsChangedHardToEasy);
    results.put("UPDATED TOTAL NUMBER OF HARD QUESTIONS", updatedTotalNumberOfHardQuestions);
    results.put("UPDATED TOTAL NUMBER OF EASY QUESTIONS", updatedTotalNumberOfEasyQuestions);
    return results;
  }

  /**
   * Calculates the data for the final results from the session.
   */
  private void calcResults() {
    updatedTotalNumberOfHardQuestions = totalNumberOfMarkQuestions(Question.Mark.HARD);
    updatedTotalNumberOfEasyQuestions = totalNumberOfMarkQuestions(Question.Mark.EASY);
  }

  /**
   * Counts the number of questions that have a certain Mark
   *
   * @param mark the question mark difficulty
   * @return a count of how many questions there are with that mark
   */
  private int totalNumberOfMarkQuestions(Question.Mark mark) {
    int count = 0;
    for (Question question : Question.getAllQuestions()) {
      if (question.getMark() == mark) {
        count++;
      }
    }
    return count;
  }

  /**
   * Initializes the list of questions from filePath
   *
   * @throws FileNotFoundException when the given file was not found
   */
  private void initializeQuestions() throws FileNotFoundException {
    File file = new File(filePath);
    String data;
    try {
      data = FileReader.readFromFile(file); // read the file data
    } catch (Exception e) {
      throw new FileNotFoundException("The given file was not found.");
    }
    String[] dataSplit = data.split("\n");

    this.allHardQuestions = new ArrayList<>();
    this.allEasyQuestions = new ArrayList<>();

    for (String currentData : dataSplit) {
      String[] values = currentData.split(":::");
      Question question = new Question(values);
      Question.append(question);
      if (question.getMark() == Question.Mark.HARD) {
        allHardQuestions.add(question);
      } else {
        allEasyQuestions.add(question);
      }
    }
  }

  /**
   * Get total questions answered so far
   *
   * @return the amount of questions answered so far
   */
  public int getTotalQuestionsAnswered() {
    return totalQuestionsAnswered;
  }

  /**
   * Get total questions that have changed from easy to hard so far
   *
   * @return the amount of questions that have changed from easy to hard so far
   */
  public int getTotalQuestionsChangedEasyToHard() {
    return totalQuestionsChangedEasyToHard;
  }

  /**
   * Get total questions that have changed from hard to easy so far
   *
   * @return the amount of questions that have changed from hard to easy so far
   */
  public int getTotalQuestionsChangedHardToEasy() {
    return totalQuestionsChangedHardToEasy;
  }

  /**
   * Get total amount of questions
   *
   * @return the amount of questions
   */
  public int getAmountQuestions() {
    return amountQuestions;
  }
}