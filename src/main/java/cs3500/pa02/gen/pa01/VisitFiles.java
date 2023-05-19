package cs3500.pa02.gen.pa01;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * Visits each of the Markdown files in the directory
 */
public class VisitFiles implements FileVisitor {

  private final Map<File, String> contents;

  /**
   * Sets up the visitFiles content variable when initialized.
   */
  public VisitFiles() {
    contents = new HashMap<>();
  }

  /**
   * Sets up the visitFiles content variable when initialized.
   *
   * @param currentContent the current content of a previous search
   */
  public VisitFiles(Map<File, String> currentContent) {
    contents = currentContent;
  }

  /**
   * Returns the contents received from the FileVisitor exploration.
   *
   * @return contents of the visited files in a hashmap
   */
  public Map<File, String> getContents() {
    return contents;
  }

  /**
   * Continues the directory search.
   *
   * @param dir
   *          a reference to the directory
   * @param attrs
   *          the directory's basic attributes
   *
   * @return FileVisitResult the result
   */
  @Override
  public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) {
    return CONTINUE;
  }

  /**
   * Checks if the file is a markdown and sends it through FileReader,
   * placing the result in contents.
   *
   * @param file
   *          a reference to the file
   * @param attrs
   *          the file's basic attributes
   *
   * @return FileVisitResult the result
   */
  @Override
  public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) {
    if (attrs.isRegularFile()) {
      String filePath = String.format("%s", file);
      int getExtensionIndex = filePath.indexOf(".");
      String endExtension = filePath.substring(getExtensionIndex + 1);
      if (endExtension.equalsIgnoreCase("md")) {
        File currentFile = ((Path) file).toFile();
        FileReader currentReader = new FileReader(currentFile);
        if (currentReader.getKeyContent().length() != 0) {
          contents.put(currentFile, currentReader.getKeyContent());
        }
      }
    }
    return CONTINUE;
  }

  /**
   * If file fails to be read or found,
   * it prints out the name of the File object
   * that was not able to be visited in the terminal, and
   * continues the search for other files.
   *
   * @param file
   *          a reference to the file
   * @param exc
   *          the I/O exception that prevented the file from being visited
   *
   * @return FileVisitResult the result
   */
  @Override
  public FileVisitResult visitFileFailed(Object file, IOException exc) {
    System.err.println("FILE UNABLE TO BE VISITED: " + file);
    return CONTINUE;
  }

  /**
   * Continues the directory search.
   *
   * @param dir
   *          a reference to the directory
   * @param exc
   *          {@code null} if the iteration of the directory completes without
   *          an error; otherwise the I/O exception that caused the iteration
   *          of the directory to complete prematurely
   *
   * @return FileVisitResult the result
   */
  @Override
  public FileVisitResult postVisitDirectory(Object dir, IOException exc) {
    return CONTINUE;
  }
}

