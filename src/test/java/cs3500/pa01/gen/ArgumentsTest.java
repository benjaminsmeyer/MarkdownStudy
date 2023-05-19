package cs3500.pa01.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the arguments record
 */
class ArgumentsTest {

  private Arguments argumentsFileName;
  private Arguments argumentsModified;
  private Arguments argumentsCreated;
  private Arguments argumentsInvalid;

  /**
   * Sets up the tests
   */
  @BeforeEach
  void setup() {
    argumentsFileName = new Arguments("src/test/resources",
        "filename", "src/test/outputResults/fileNameResult.md");
    argumentsModified = new Arguments("src/test/resources",
        "modified", "src/test/outputResults/modifiedResult");
    argumentsCreated = new Arguments("src/test/resources",
        "created", "src/test/outputResults/createdResult.md");
    argumentsInvalid = new Arguments("src/test/resources",
        "invalid", "src/test/outputResults/createdResult.md");
  }

  /**
   * Tests if the sort returns correctly
   */
  @Test
  void testGetSort() {
    assertEquals(Arguments.Sort.CREATED, argumentsCreated.getSort());
    assertEquals(Arguments.Sort.MODIFIED, argumentsModified.getSort());
    assertEquals(Arguments.Sort.FILENAME, argumentsFileName.getSort());
  }

  /**
   * Tests if the Arguments enum returns sort correctly
   */
  @Test
  void testStringSort() {
    assertEquals("created", argumentsCreated.sort());
  }

  /**
   * Tests if the Arguments enum returns outputPath correctly
   */
  @Test
  void testOutputPath() {
    assertEquals("src/test/outputResults/createdResult.md", argumentsCreated.outputPath());
  }

  /**
   * Tests if the getOutputPath returns correctly
   */
  @Test
  void testGetOutputPath() {
    assertEquals("src/test/outputResults/createdResult.sr",
        argumentsCreated.getOutputPath(".sr"));
    assertEquals("src/test/outputResults/createdResult.md",
        argumentsCreated.getOutputPath(".md"));
    assertEquals("src/test/outputResults/modifiedResult.md",
        argumentsModified.getOutputPath(".md"));
    assertEquals("src/test/outputResults/modifiedResult.sr",
        argumentsModified.getOutputPath(".sr"));
  }

  /**
   * Tests if the Arguments enum returns getOutputFile correctly
   */
  @Test
  void testGetOutputFile() {
    assertEquals(new File("src/test/outputResults/createdResult.md"),
        argumentsCreated.getOutputFile());
    assertEquals(new File("src/test/outputResults/modifiedResult.md"),
        argumentsModified.getOutputFile());
  }

  /**
   * Tests if the getSort function throws an error when given an invalid sort type
   */
  @Test
  void testThrowsGetSort() {
    assertThrows(
        IllegalArgumentException.class,
        () -> argumentsInvalid.getSort());
  }
}