package cs3500.pa01.gen;

import cs3500.pa01.study.question.Question;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Writes the results into a Markdown file
 */
public class FileWriter {

  /**
   * Writes the given Strings to the given filepath.
   *
   * @param arguments where to write the contents and sort type
   * @param contents contents to write to the file
   * @throws IOException when the file data is inaccessible
   */
  public static void writeToFile(Arguments arguments, Map<File, String> contents)
      throws IOException {

    String sortedContents = sortMarkdown(arguments.getSort(), contents);

    // Add .md to the end of the file path.
    // You may need to change this to get the desired user-experience that was asked for.
    Path path = Path.of(arguments.getOutputFile().toPath().toUri());

    fileWrite(sortedContents, path);
  }

  /**
   * Writes the Question class data to the given filepath
   *
   * @param outputPath where to write the contents
   */
  public static void writeQuestionAnswersFile(String outputPath) {
    String questionsContent = createQuestionsFile();
    Path path = Path.of(outputPath);
    fileWrite(questionsContent, path);
  }

  /**
   * Writes the file data given the output
   *
   * @param output the string that will be used as the output of the file
   * @param path the path of the output
   */
  private static void fileWrite(String output, Path path) {
    // Convert String to data for writing ("raw" byte data)
    byte[] data = output.getBytes();

    // Built-in convenience method for writing data to a file.
    try {
      Files.write(path, data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates a string filled of the questions with their respective information.
   * The String format is question:::answer:::mark
   *
   * @return the string with all the questions
   */
  private static String createQuestionsFile() {
    List<Question> questions = Question.getAllQuestions();
    StringBuilder output = new StringBuilder();
    for (Question question : questions) {
      String current = question.getQuestion()
          + ":::"
          + question.getAnswer()
          + ":::"
          + question.getMark();
      output.append(current);
      output.append("\n");
    }
    return output.toString().trim();
  }

  /**
   * Sorts the strings into one large string, in the sort type order.
   *
   * @param sort arguments record sort type
   * @param contents map of file and string for data
   * @return combined string of each file's content in the requested sort order
   * @throws IOException when accessing File data has an error
   */
  public static String sortMarkdown(Arguments.Sort sort, Map<File, String> contents)
      throws IOException {
    if (sort == Arguments.Sort.MODIFIED) {
      return modifiedSort(contents);
    } else if (sort == Arguments.Sort.CREATED) {
      return createdSort(contents);
    } else {
      return filenameSort(contents);
    }
  }

  /**
   * Sort the strings into one string in the created date order.
   *
   * @param contents a map of the file and content of the page given as a string
   * @return strings from the map into one string, sorted with created date order
   * @throws IOException when accessing File data has an error
   */
  private static String createdSort(Map<File, String> contents) throws IOException {
    // organize the content in the output summary file in order
    // based on the create-date time stamp of the source file.
    TreeMap<FileTime, String> creationTimeMap = new TreeMap<>();
    for (File file : contents.keySet()) {
      BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
      creationTimeMap.put(attr.creationTime(), contents.get(file));
    }

    StringBuilder resultString = new StringBuilder();
    for (FileTime time : creationTimeMap.keySet()) {
      String result = creationTimeMap.get(time) + "\n";
      resultString.append(result);
    }

    return resultString.toString().trim();
  }

  /**
   * Sort the strings into one string in the filename (alphabetic) order.
   *
   * @param contents a map of the file and content of the page given as a string
   * @return strings from the map into one string, sorted with filename and alphabetic order
   */
  private static String filenameSort(Map<File, String> contents) {
    // organize the content in the output summary file in order based on
    // the alphabetically sorted source file names.
    TreeMap<String, String> filenameMap = new TreeMap<>();
    for (File file : contents.keySet()) {
      filenameMap.put(file.getName(), contents.get(file));
    }

    StringBuilder resultString = new StringBuilder();
    for (String time : filenameMap.keySet()) {
      String result = filenameMap.get(time) + "\n";
      resultString.append(result);
    }

    return resultString.toString().trim();
  }

  /**
   * Sort the strings into one string in the modified date order.
   *
   * @param contents a map of the file and content of the page given as a string
   * @return strings from the map into one string, sorted with modified date order
   * @throws IOException when accessing File data has an error
   */
  private static String modifiedSort(Map<File, String> contents) throws IOException {
    // organize the content in the output summary file in order based on
    // the last modified time stamp of the source file.
    TreeMap<FileTime, String> modifiedTimeMap = new TreeMap<>();
    for (File file : contents.keySet()) {
      BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
      modifiedTimeMap.put(attr.lastModifiedTime(), contents.get(file));
    }

    StringBuilder resultString = new StringBuilder();
    for (FileTime time : modifiedTimeMap.keySet()) {
      String result = modifiedTimeMap.get(time) + "\n";
      resultString.append(result);
    }

    return resultString.toString().trim();
  }
}
