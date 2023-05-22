package cs3500.pa02.study.controller;

import cs3500.pa01.gen.FileWriter;
import cs3500.pa02.study.model.Question;
import cs3500.pa02.study.model.QuestionsDataHandler;
import cs3500.pa02.study.viewer.StudyUser;
import cs3500.pa02.study.viewer.User;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Controller for Study Session
 */
public class StudySessionImpl implements StudySession {

  private User user;
  private final String filePath;
  private final Random random;
  private final int amountQuestions;
  private QuestionsDataHandler session;
  private Map<String, Integer> userQuestions;
  private final InputStream inputStream;
  private final PrintStream printStream;

  /**
   * Controls the study session with the requested input and print stream
   *
   * @param inputStream the input stream for the user
   * @param printStream the print stream for the user
   */
  public StudySessionImpl(InputStream inputStream, PrintStream printStream) {
    user = new StudyUser(inputStream, printStream);
    welcomeNoArgs();
    this.filePath = filePath();
    this.amountQuestions = amountQuestions();
    this.random = randomSeed();
    this.inputStream = inputStream;
    this.printStream = printStream;
  }

  /**
   * Controls the study session with the input and print stream
   *
   * @param filePath the file path that contains the questions
   * @param amountQuestions the amount of questions wanted
   * @param random the random seed for shuffling the questions
   * @param inputStream the input stream for the user
   * @param printStream the print stream for the user
   */
  public StudySessionImpl(String filePath, int amountQuestions,
                          Random random, InputStream inputStream, PrintStream printStream) {
    this.filePath = filePath;
    this.amountQuestions = amountQuestions;
    this.random = random;
    this.inputStream = inputStream;
    this.printStream = printStream;
  }

  /**
   * Starts the study session with the given arguments
   */
  @Override
  public void startSession() {
    if (user == null) {
      user = new StudyUser(inputStream, printStream);
      welcomeWithArgs();
    }
    // START THE DATA HANDLER
    getUserQuestions();
    try {
      session = new QuestionsDataHandler(filePath, amountQuestions, random);
      startStudy(session);
    } catch (Exception e) {
      user.sendMessageInRed("Ending session...");
      endSession();
    }
  }

  /**
   * Get user prompt questions
   */
  private void getUserQuestions() {
    userQuestions = new HashMap<>();
    userQuestions.put("Mark as Easy", 1);
    userQuestions.put("Mark as Hard", 2);
    userQuestions.put("Show answer", 3);
    userQuestions.put("Exit", 4);
  }

  /**
   * Starts the study session
   *
   * @param session the session that contains the data for the questions
   */
  private void startStudy(QuestionsDataHandler session) {
    startSessionMessage();

    List<Question> questions = session.getRandomQuestions();
    boolean studying = true;
    int index = 0;

    while (studying && index < questions.size()) {
      Question current = questions.get(index);
      int response = sendQuestion(current);
      if (response == -1 || response == userQuestions.get("Exit")) {
        studying = checkExit();
      } else {
        try {
          int outcome = handleQuestionResponse(current, response);
          if (outcome != userQuestions.get("Show answer")) {
            index++;
          }
        } catch (IllegalArgumentException e) {
          invalidNumberResponse();
        }
      }
    }

    endSession();
  }

  /**
   * Sends a user a message saying that the response
   * was invalid when provided an invalid number input.
   */
  private void invalidNumberResponse() {
    String invalidResponse = "Invalid response.\n"
        + "Asking the question again.\n"
        + "If you want to exit, answer with exit, "
        + "an invalid input that is not a number, or the number 4.";
    user.sendMessageInRed(invalidResponse);
  }

  /**
   * Handles the user's response.
   *
   * @param question the current question being asked
   * @param response the user's response
   * @throws IllegalArgumentException when the response is invalid
   */
  private int handleQuestionResponse(Question question, int response) {
    if (validResponse(response)) {
      if (response == userQuestions.get("Mark as Easy")) {
        session.questionAnswered(question, Question.Mark.EASY);
        return userQuestions.get("Mark as Easy");
      } else if (response == userQuestions.get("Mark as Hard")) {
        session.questionAnswered(question, Question.Mark.HARD);
        return userQuestions.get("Mark as Hard");
      } else if (response == userQuestions.get("Show answer")) {
        showAnswer(question);
        return userQuestions.get("Show answer");
      }
    }
    throw new IllegalArgumentException("Invalid response.");
  }

  /**
   * Shows user the answer
   */
  private void showAnswer(Question current) {
    String question = current.getQuestion().trim();
    String answer = current.getAnswer().trim();
    String sendAnswer = "\nThe answer for the question \"" + question + "\" is\n"
        + "\"" + answer + "\"";
    user.sendMessage(sendAnswer);
  }

  /**
   * Checks if the response is valid
   *
   * @param response the user's response
   * @return true if the response is valid, false other
   */
  private boolean validResponse(int response) {
    return userQuestions.size() > response && response > 0;
  }

  /**
   * Checks if the user wants to exit
   *
   * @return true if user does, false otherwise.
   */
  private boolean checkExit() {
    String checkExit = "\nAre you sure you want to exit?\n"
        + "Respond with Y if yes, N if no.\n"
        + "If you say yes, the session will end and the results will be sent to you.\n"
        + "If you say no, the study session will continue.\n";
    String response;
    do {
      user.sendMessageInRed(checkExit);
      response = user.askUser("Respond here: ");
    } while (!responseEquals(response, "Y", "N"));

    return responseEquals(response, "N");
  }

  /**
   * Checks if the string equals any of the args.
   * It is not case-sensitive.
   *
   * @param check the string to check
   * @param args the args it should equal
   * @return true if one of them equals the check, false otherwise.
   */
  private boolean responseEquals(String check, String... args) {
    for (String arg : args) {
      if (check.equalsIgnoreCase(arg)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sends a message stating that the session is about to begin.
   */
  private void startSessionMessage() {
    String start = "\nNow, let's begin your study session!\n"
        + "We will start with the hard questions.\n"
        + "If the amount of questions requested exceeds the available hard questions,\n"
        + "we will ask questions marked as easy.\n"
        + "If the amount of questions requested exceeds the total available questions,\n"
        + "we will ask all the questions, from hard to easy.\n\n"
        + "Follow the instructions provided when sent a question.\n"
        + "When you want to exit, say exit, 4, -1, or provide an invalid non-integer response.\n";
    user.sendMessage(start);
  }

  /**
   * Ends the session.
   * Saves and sends the final results to the user before exiting.
   */
  private void endSession() {
    Map<String, Integer> endSessionResults = session.endResults();
    String endMessage = "\nWow! Great job on your study session!\n"
        + "Make sure to come back soon to study as you know,\n"
        + "studying keeps what you learn in your head.\n\n"
        + "Here are your results from today's session:";
    user.sendMessage(endMessage);
    for (String results : endSessionResults.keySet()) {
      String outcome = results + ": " + endSessionResults.get(results);
      user.sendMessage(outcome);
    }
    String finalMessage = "\n\nGreat work today!\nUntil next time, Anki out.\n";
    user.sendMessage(finalMessage);
    FileWriter.writeQuestionAnswersFile(filePath);
  }

  /**
   * Welcomes the player by providing basic context into the application.
   */
  private void welcomeNoArgs() {
    String welcome = "Hi there!\n"
        + "Welcome to your study session!\n"
        + "I am Anki Lite, your very own study guide tool!\n\n"
        + "Let's begin with some basic questions, including the file path that\n"
        + "contains the questions, the amount of questions you want, and\n"
        + "if you want to set a shuffling seed for your set of questions.\n\n"
        + "Now, let's begin with the questions!";
    user.sendMessage(welcome);
  }

  /**
   * Welcomes the player with the given arguments
   */
  private void welcomeWithArgs() {
    String welcome = "Hi there!\n"
        + "Welcome to your study session!\n"
        + "I am Anki Lite, your very own study guide tool!\n\n"
        + "Thank you for providing the arguments before starting!";
    user.sendMessage(welcome);
  }

  /**
   * Sends the user the question
   *
   * @param question the question to send to user
   * @return the answer given from the prompt
   */
  private int sendQuestion(Question question) {
    StringBuilder generateQuestion = new StringBuilder();
    String questionCount = "\nQuestion " + (session.getTotalQuestionsAnswered() + 1)
        + " out of " + session.getAmountQuestions()
        + "\n\n" + question.getQuestion() + "\n\n";
    generateQuestion.append(questionCount);
    int questionNum = 0;
    for (String userQuestion : userQuestions.keySet()) {
      questionNum++;
      userQuestions.put(userQuestion, questionNum);
      String current = userQuestions.get(userQuestion) + ". " + userQuestion;
      generateQuestion.append(current + "\n");
    }
    String response = user.askUser(generateQuestion.toString());
    int responseValue = parseResponse(response);
    return responseValue;
  }

  /**
   * Gets the amount of questions requested
   *
   * @return the amount of questions requested
   */
  private int amountQuestions() {
    String questions = user.askUser("\nHow many questions (out of the amount available) "
        + "would you like to receive?: ");
    int amount = parseResponse(questions);
    if (amount > -1) {
      return amount;
    }
    return Integer.MAX_VALUE;
  }

  /**
   * Parses the integer given from the input.
   * If it fails to parse, it returns a -1.
   *
   * @param input the string input
   * @return a string converted to an integer
   */
  public static int parseResponse(String input) throws NumberFormatException {
    int num;
    try {
      num = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      num = -1;
    }
    return num;
  }

  /**
   * Gets the random seed, unless user wants to set it to a certain seed
   *
   * @return the random class
   */
  private Random randomSeed() {
    String setSeed = user.askUser("\nDo you want a specific random seed"
        + " for your shuffling of questions?"
        + "\n(If yes, respond with a seed number. If not, respond with a no.):");
    int seed = parseResponse(setSeed);
    if (seed > 0) {
      return new Random(seed);
    }
    return new Random();
  }

  /**
   * Gets the file path
   *
   * @return the String file path
   */
  private String filePath() {
    String response = user.askUser("\nProvide a file path that has a .sr\n"
        + "extension for your study guide review\n"
        + "(e.g. folder/files/questions.sr). "
        + "If you do not have one,\nrespond with no:");
    boolean checkResponseForNo = response.trim().equalsIgnoreCase("no");
    if (checkResponseForNo) {
      user.sendMessageInRed("No filepath was given.\nEnding session.");
      System.exit(1);
    }
    return response;
  }

}
