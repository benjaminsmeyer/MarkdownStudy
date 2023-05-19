package cs3500.pa01.study.user;

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
  public String askUser(String question);

  /**
   * Send a user a message
   */
  public void sendMessage(String message);

  /**
   * Send a user a message in red
   */
  public void sendMessageInRed(String message);
}
