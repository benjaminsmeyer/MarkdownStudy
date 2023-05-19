package cs3500.pa02.gen.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the VisitFiles class
 */
class VisitFilesTest {

  private VisitFiles visitFiles;
  private VisitFiles visitFilesWithArg;
  private Map<File, String> currentContent;

  private Path dir;
  private Path file;

  /**
   * Sets up each test
   */
  @BeforeEach
  void setup() {
    currentContent = new HashMap<>();
    visitFiles = new VisitFiles();
    visitFilesWithArg = new VisitFiles(currentContent);
    dir = Path.of("src/test/resources");
    file = Path.of("src/test/resources/arrays.md");
  }

  /**
   * Tests to see if getContents works
   */
  @Test
  void testGetContents() {
    assertEquals(currentContent, visitFilesWithArg.getContents());
  }

  /**
   * Tests to see if preVisitDirectory continues
   */
  @Test
  void testPreVisitDirectory() {
    assertEquals(FileVisitResult.CONTINUE, visitFiles.preVisitDirectory(dir, null));
  }

  /**
   * Tests to see if visitFiles continues
   */
  @Test
  void testVisitFile() throws IOException {
    assertEquals(FileVisitResult.CONTINUE,
        visitFiles.visitFile(file, Files.readAttributes(file, BasicFileAttributes.class)));
  }

  /**
   * Tests to see if visitFiles continues when given not a regular File
   */
  @Test
  void testVisitFileNotRegularFile() throws IOException {
    assertEquals(FileVisitResult.CONTINUE, visitFiles.visitFile(file,
        Files.readAttributes(Path.of("src/test/outputResults"), BasicFileAttributes.class)));
  }

  /**
   * Tests to see if visitFileFailed continues
   */
  @Test
  void testVisitFileFailed() {
    assertEquals(FileVisitResult.CONTINUE, visitFiles.visitFileFailed(file, new IOException()));
  }

  /**
   * Tests to see if postVisitDirectory continues
   */
  @Test
  void testPostVisitDirectory() {
    assertEquals(FileVisitResult.CONTINUE, visitFiles.postVisitDirectory(dir, new IOException()));
  }
}