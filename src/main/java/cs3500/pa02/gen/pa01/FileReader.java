package cs3500.pa02.gen.pa01;

import cs3500.pa02.study.question.Question;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Reads the Markdown files
 */
public class FileReader {

  private final File file;
  private final String defaultContent;
  private final String keyContent;

  /**
   * Reads the contents from a file to a String.
   * Reformats the String following the Markdown Study Guide rules.
   *
   * @param file A file object which corresponds to a
   *             path in the file system and some information at that path
   */
  public FileReader(File file) {
    this.file = file;
    this.defaultContent = readFromFile(file);
    this.keyContent = rewriteMarkdown(defaultContent);
  }

  /**
   * Returns the file object.
   *
   * @return the file object
   */
  public File getFile() {
    return file;
  }

  /**
   * Returns the default content.
   *
   * @return the default content
   */
  public String getDefaultContent() {
    return defaultContent;
  }

  /**
   * Returns the key content.
   *
   * @return the key content
   */
  public String getKeyContent() {
    return keyContent;
  }

  /**
   * Reads the contents from a file to a String.
   *
   * @param file A file object which corresponds to a
   *             path in the file system and some information at that path
   * @return the contents of the file
   */
  public static String readFromFile(File file) {
    // Initialize a Scanner to read the file
    Scanner sc;
    try { // The file may not exist, in which case we need to handle that error (hence try-catch)
      sc = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not found.");
    }

    // Use the Scanner to iterate through the file line-by-line
    // and accumulate its contents in a string
    StringBuilder content = new StringBuilder();
    while (sc.hasNextLine()) { // Check there is another unread line in the file
      content.append(sc.nextLine()).append("\n"); // Read the aforementioned line
    }

    return content.toString().trim(); // Return the contents collected in the StringBuilder
  }

  /**
   * Counts the number of times a string appears in a piece of text.
   *
   * @param line a line of text
   * @param substring the substring
   * @return the number of times the substring was found in the line
   */
  private static int countStringOccurrence(String line, String substring) {
    int currentIndex = 0;
    int count = 0;
    while (currentIndex != -1) {
      currentIndex = line.indexOf(substring, currentIndex);
      if (currentIndex != -1) {
        count++;
        currentIndex++;
      }
    }
    return count;
  }

  /**
   * Counts the number of '#' characters to determine the heading level.
   *
   * @param line a string of text
   * @return the number of '#' characters in a row
   */
  private static int determineHeadingLevel(String line) {
    int level = 1;
    while (line.charAt(level) == '#') {
      level++;
    }
    return level;
  }

  /**
   * Adds a bullet point before the first bracketed phrase
   *
   * @param text the string of text to add bullet point to
   * @return a string with a bullet point if text
   *         does not contain a bullet point or an empty string if it does
   */
  private static String addBulletPoint(String text) {
    if (!text.contains("- ") || !(text.indexOf("- ") == 0)) {
      return "- ";
    }
    return "";
  }

  /**
   * Decides which String to append to the StringBuilder output
   *
   * @param line the string of text to add bullet point to
   * @param current the current string that has been built
   * @param start the start index of the string for line
   * @param end the end index of the string for line
   * @param endBracketFound the value if the endBracket has been found
   * @return a string with the right selected text
   */
  private static String decideText(String line, StringBuilder current, int start,
                                   int end, boolean endBracketFound) {
    return !endBracketFound ? line.substring(start + 2, end) : current.toString();
  }

  /**
   * Finds the end bracket of text when spanning multiple lines.
   *
   * @param lines the lines of text
   * @param start the start index of the starting bracket
   * @param end the end index that was not found
   * @param index the current index of the lines of text
   * @return a List containing the current build of text, the end
   *         index from the search, and the end bracket boolean value
   */
  private static List<Object> endBracketNotFound(String[] lines, int start,
                                                 int end, int index) {
    boolean endBracketFound = false;
    String line = lines[index].trim();
    StringBuilder current = new StringBuilder();
    current.append("- ");
    String text = line.substring(start + 2);
    current.append(text);
    int endBracket = 1;
    while (!endBracketFound) {
      int newEnd = lines[index + endBracket].indexOf("]]");
      String result;
      if (newEnd == -1) {
        result = " " + lines[index + endBracket];
      } else {
        result = " " + lines[index + endBracket].substring(0, newEnd);
        endBracketFound = true;
      }
      current.append(result);
      end = newEnd;
      endBracket++;
    }
    return Arrays.asList(current, end);
  }

  /**
   * Check if there are additional bracketed phrases on the same line,
   * then adds them to the output String.
   *
   * @param lines the lines of text
   * @param end the end index of the line
   * @param index the current position of where we are in the list of lines
   * @param output the current progress of the output of strings
   * @param current the current build to be appended to the output
   * @return a List containing the end bracket boolean value, and
   *         the output text that added current build
   */
  private static List<Object> additionalBrackets(String[] lines, int end, int index, StringBuilder
                                                     output, StringBuilder current) {
    String line = lines[index].trim();
    int countString = countStringOccurrence(line, "[[");
    boolean endBracketFound = false;
    while (end + 2 < line.length() && countString > 1) {
      boolean continueLoop = true;
      while (line.substring(end + 2).contains("[[") && continueLoop) {
        int start = line.indexOf("[[", end + 2);
        end = line.indexOf("]]", start + 2);

        // When the end bracket was not found
        if (end == -1) {
          List<Object> endBracketFoundResult = endBracketNotFound(lines, start, end, index);
          current = (StringBuilder) endBracketFoundResult.get(0);
          end = (int) endBracketFoundResult.get(1);
          endBracketFound = true;
        }

        String text = decideText(line, current, start, end, endBracketFound);

        if (Question.checkForQuestion(text)) {
          Question.addQuestion(text);
        } else {
          // Add a bullet point before the first bracketed phrase
          output.append(addBulletPoint(text));
          // Append the text of the bracketed phrase
          output.append(text.trim()).append("\n");
        }
        continueLoop = false;
      }
      endBracketFound = false;
      countString--;
    }
    return Arrays.asList(endBracketFound, output);
  }

  /**
   * Adds header to the output.
   *
   * @param output the curent output so far
   * @param index the index position of the lines
   * @param line the current line being parsed
   * @return the current output with the header appended
   */
  private static StringBuilder lineStartsWithHeader(StringBuilder output, int index, String line) {
    // Count the number of '#' characters to determine the heading level
    int level = determineHeadingLevel(line);

    if (!String.valueOf(line.charAt(level)).isBlank()) {
      return output;
    }

    // Add a blank line before non-initial headings
    if (index > 0) {
      output.append("\n");
    }

    // Append the heading with the appropriate number of '#' characters
    output.append("#".repeat(level));
    output.append(" ");
    output.append(line.substring(level).trim());
    output.append("\n");

    return output;
  }

  /**
   * Parses the double brackets text to append to the output.
   *
   * @param lines the lines of text
   * @param index the current position of where we are in the list of lines
   * @param output the current progress of the output of strings
   * @return a List with the endBracketFound value, and the output
   */
  private static List<Object> lineHasDoubleBrackets(String[] lines,
                                                    int index, StringBuilder output) {
    // Extract the text inside the brackets
    String line = lines[index].trim();
    int start = line.indexOf("[[");
    int end = line.indexOf("]]", start + 2);
    StringBuilder current = new StringBuilder();

    // When the end bracket was not found
    boolean endBracketFound = false;
    if (end == -1) {
      List<Object> endBracketFoundResult = endBracketNotFound(lines,
          start, end, index);
      current = (StringBuilder) endBracketFoundResult.get(0);
      end = (int) endBracketFoundResult.get(1);
      endBracketFound = true;
    }

    String text = decideText(line, current, start, end, endBracketFound);

    if (Question.checkForQuestion(text)) {
      Question.addQuestion(text);
    } else {
      // Add a bullet point before the first bracketed phrase
      output.append(addBulletPoint(text));
      // Append the text of the bracketed phrase
      output.append(text.trim()).append("\n");
    }

    // Check if there are additional bracketed phrases on the same line
    List<Object> additionalBracketsResults = additionalBrackets(lines, end, index, output, current);
    endBracketFound = (boolean) additionalBracketsResults.get(0);
    output = (StringBuilder) additionalBracketsResults.get(1);

    return Arrays.asList(endBracketFound, output);
  }

  /**
   * Reads the string and reformats it to take the key content out.
   *
   * @param content a string to reformat
   * @return the reformatted string with the key content
   */
  public static String rewriteMarkdown(String content) {
    // clean up the file to have only the key data
    // (such as headings, bracket text, etc) while also reformatting it accordingly
    StringBuilder output = new StringBuilder(); // current build for the entire reformatted string
    String[] lines = content.split("\n"); // list of lines from the string
    // Iterate over each line in the input string
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();
      // Check if the line is a heading
      if (line.startsWith("#")) {
        output = lineStartsWithHeader(output, i, line);
      } else if (line.contains("[[")) { // Check if the line is a bracketed phrase
        List<Object> lineHasDoubleBracketsResults = lineHasDoubleBrackets(lines, i, output);
        output = (StringBuilder) lineHasDoubleBracketsResults.get(1);
      }
      // Otherwise, ignore the line
    }
    return output.toString();
  }
}