package cs3500.pa02;

import cs3500.pa02.gen.pa01.Arguments;
import cs3500.pa02.gen.pa01.FileWriter;
import cs3500.pa02.gen.pa01.VisitFiles;
import cs3500.pa02.study.controller.StudySession;
import cs3500.pa02.study.controller.StudySessionImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is the main driver of this Markdown Study Guide project
 */
public class Driver {

  /**
   * Project entry point
   *
   * @param args requires three arguments: directory path,
   *             sort type (created, modified, filename),
   *             and output path for study guide file.
   * @throws IllegalArgumentException when the arguments are invalid
   * @throws IOException when the file data is inaccessible
   */
  public static void main(String[] args) throws IOException, IllegalArgumentException {
    if (args.length != 0 && args.length != 3) {
      String argMessage = "To generate a Study Guide MD file and Questions Answer SR file, "
          + "you must have three arguments in the following order: "
          + "a directory path, a sort type (filename, modified, created), "
          + "and a path to output study guide and questions answer file. "
          + "To start a study session, do not provide any arguments in the command line.";
      throw new IllegalArgumentException(argMessage);
    }

    if (args.length == 3) {
      runGen(args);
    } else {
      runStudy();
    }
  }

  /**
   * Project entry point for Study Guide Summarizer and Questions Answer File Generator
   *
   * @param args requires three arguments: directory path,
   *             sort type (created, modified, filename),
   *             and output path for study guide file.
   * @throws IllegalArgumentException when the arguments are invalid
   * @throws IOException when the file data is inaccessible
   */
  private static void runGen(String[] args) throws IOException {
    String containFilesPath = args[0];
    String sortType = args[1];
    String returnFilePath = args[2];
    try {
      Arguments arguments = new Arguments(containFilesPath, sortType, returnFilePath);
      Path startingDirectory = Path.of(arguments.dirPath());
      VisitFiles visitFiles = new VisitFiles();

      Files.walkFileTree(startingDirectory, visitFiles);
      FileWriter.writeToFile(arguments, visitFiles.getContents());
      FileWriter.writeQuestionAnswersFile(arguments.getOutputPath(".sr"));
    } catch (IOException e) {
      throw new IOException("Unable to access file data.");
    }
  }

  /**
   * Project entry point for Study Session
   */
  private static void runStudy() {
    // STARTS THE STUDY SESSION
    StudySession studySession = new StudySessionImpl(System.in, System.out);
    studySession.startSession();
  }
}