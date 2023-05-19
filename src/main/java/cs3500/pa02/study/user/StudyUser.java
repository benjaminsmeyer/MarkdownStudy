package cs3500.pa02.study.user;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * User class for the questions and responses
 */
public class StudyUser implements User {
  private final Scanner scanner;
  private final PrintStream printStream;

  /**
   * Starts the user's viewer
   *
   * @param inputStream the input stream for the user
   * @param printStream the print stream for the user
   */
  public StudyUser(InputStream inputStream, PrintStream printStream) {
    this.scanner = new Scanner(inputStream);
    this.printStream = new PrintStream(printStream);
  }

  /**
   * Handles the question.
   *
   * @param question a string of the question
   * @return a string from the response
   */
  public String askUser(String question) {
    printStream.println(question.trim());
    String result;
    try {
      result = scanner.nextLine();
    } catch (NoSuchElementException e) {
      System.err.println("The line was not found.");
      result = null;
    }
    return result;
  }

  /**
   * Send a user a message
   *
   * @param message the message for user
   */
  public void sendMessage(String message) {
    System.out.println("\n" + message.trim());
  }

  /**
   * Send a user a message in red
   *
   * @param message the message for user
   */
  public void sendMessageInRed(String message) {
    System.err.println("\n" + message.trim());
  }
}
