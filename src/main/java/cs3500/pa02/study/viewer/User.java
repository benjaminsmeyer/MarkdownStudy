package cs3500.pa02.study.viewer;

/**
 * User interface for the questions and responses
 */
public interface User {
  /**
   * Handles the question.
   *
   * @param question a string of the question
   * @return a string from the response
   */
  String askUser(String question);

  /**
   * Send a user a message
   *
   * @param message the message for user
   */
  void sendMessage(String message);

  /**
   * Send a user a message in red
   *
   * @param message the message for user
   */
  void sendMessageInRed(String message);
}
