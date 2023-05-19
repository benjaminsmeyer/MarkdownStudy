package cs3500.pa02.gen.pa01;

import java.io.File;

/**
 * Stores arguments received from the command line.
 *
 * @param dirPath the directory path for the files
 * @param sort the sort type for the study guide
 * @param outputPath the output path for the study guide
 */
public record Arguments(String dirPath, String sort, String outputPath) {

  /**
   * Returns the sort type.
   *
   * @return the Sort enum value of the String sort
   * @throws IllegalArgumentException if the string for sort type is not found
   */
  public Sort getSort() {
    if (checkSortName(sort, Sort.FILENAME.toString())) {
      return Sort.FILENAME;
    } else if (checkSortName(sort, Sort.CREATED.toString())) {
      return Sort.CREATED;
    } else if (checkSortName(sort, Sort.MODIFIED.toString())) {
      return Sort.MODIFIED;
    } else {
      throw new IllegalArgumentException("Invalid sort type: "
          + "the options are created, modified, and filename.");
    }
  }

  /**
   * Returns a file object for the requested output directory.
   *
   * @return file a file object for the requested output directory
   */
  public File getOutputFile() {
    if (outputPath.contains(".md")) {
      return new File(outputPath);
    } else {
      return new File(outputPath + ".md");
    }
  }

  /**
   * Returns the output path for the requested output directory.
   *
   * @param extension the extension for the output path
   * @return string an output path for the requested output directory
   */
  public String getOutputPath(String extension) {
    if (outputPath.contains(extension)) {
      return outputPath;
    }

    String currentOutputPath = outputPath;
    if (currentOutputPath.contains(".")) {
      int index = currentOutputPath.indexOf(".");
      currentOutputPath = outputPath.substring(0, index);
    }

    return currentOutputPath + extension;
  }

  /**
   * Checks if the received sort type is equal to another sort type.
   *
   * @param sort the string sort name
   * @param name the name of sort type
   * @return true if string sort name equals the name of the sort type,
   *         false if not (not case-sensitive)
   */
  private boolean checkSortName(String sort, String name) {
    return sort.equalsIgnoreCase(name);
  }

  /**
   * Names of sort types.
   */
  public enum Sort {
    FILENAME, // FILENAME SORT TYPE
    CREATED, // CREATED SORT TYPE
    MODIFIED // MODIFIED SORT TYPE
  }

}
