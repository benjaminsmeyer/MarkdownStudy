package cs3500.pa01.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the FileWriter class
 */
class FileWriterTest {

  private Arguments argumentsFileName;
  private Arguments argumentsModified;
  private Arguments argumentsCreated;
  private Map<File, String> contents;
  private String arrayExpectedOutput;
  private String vectorExpectedOutput;

  /**
   * Sets up each test
   */
  @BeforeEach
  void setup() {
    arrayExpectedOutput = "# Java Arrays"
        + "\n- An **array** is a collection of variables of the same type\n"
        + "\n"
        + "## Declaring an Array\n"
        + "- General Form: type[] arrayName;\n"
        + "- only creates a reference\n"
        + "- no array has actually been created yet.\n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- General form:  arrayName = new type[numberOfElements];\n"
        + "- numberOfElements must be a positive Integer.\n"
        + "- Gotcha: Array size is not modifiable once instantiated.\n";

    vectorExpectedOutput = "# Vectors"
        + "\n- Vectors act like resizable arrays\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- General Form: Vector<type> v = new Vector();\n"
        + "- type needs to be a valid reference type\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- v.add(object of type);\n";

    argumentsFileName = new Arguments("src/test/resources", "filename",
        "src/test/outputResults/fileNameResult.md");
    argumentsModified = new Arguments("src/test/resources", "modified",
        "src/test/outputResults/modifiedResult");
    argumentsCreated = new Arguments("src/test/resources", "created",
        "src/test/outputResults/createdResult.md");

    contents = new HashMap<>();

    File current = new File("src/test/resources/arrays.md");

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date newLastModified = sdf.parse("4/13/2023");
      current.setLastModified(newLastModified.getTime());

      contents.put(current, arrayExpectedOutput);

      current = new File("src/test/resources/vectors.md");

      sdf = new SimpleDateFormat("dd/MM/yyyy");
      newLastModified = sdf.parse("4/12/2023");
      current.setLastModified(newLastModified.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    contents.put(current, vectorExpectedOutput);
  }

  /**
   * Tests to see if writeToFile works
   */
  @Test
  void testWriteToFileWithFileNameSort() {
    try {
      FileWriter.writeToFile(argumentsFileName, contents);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String outputFileName = FileReader.readFromFile(argumentsFileName.getOutputFile());
    String outputFileNameExpect = (arrayExpectedOutput + "\n" + vectorExpectedOutput).trim();
    assertEquals(outputFileNameExpect, outputFileName);
  }

  /**
   * Create a FileWriter instance
   */
  @Test
  void testFileWriterInstance() {
    FileWriter createFileWriter = new FileWriter();
  }


  /**
   * Tests to see if writeToFile throws correctly when given an incorrect path
   */
  @Test
  void testThrowsWriteToFile() {
    Arguments argumentsInvalid = new Arguments("src/test/resources", "filename",
        "src/test/nope/fileNameResult.md");
    assertThrows(RuntimeException.class, () -> FileWriter.writeToFile(argumentsInvalid, contents));
  }

  /**
   * Tests to see if sortMarkdownFileName sorts the text correctly
   */
  @Test
  void testSortMarkdownFileName() {
    try {
      assertEquals((arrayExpectedOutput + "\n" + vectorExpectedOutput).trim(),
          FileWriter.sortMarkdown(argumentsFileName.getSort(), contents));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests to see if sortMarkdownCreated sorts the text correctly
   */
  @Test
  void testSortMarkdownCreated() {
    try {
      assertEquals((vectorExpectedOutput + "\n" + arrayExpectedOutput).trim(),
          FileWriter.sortMarkdown(argumentsCreated.getSort(), contents));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests to see if sortMarkdownLastModified sorts the text correctly
   */
  @Test
  void testSortMarkdownLastModified() {
    try {
      assertEquals((vectorExpectedOutput + "\n" + arrayExpectedOutput).trim(),
          FileWriter.sortMarkdown(argumentsModified.getSort(), contents));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
