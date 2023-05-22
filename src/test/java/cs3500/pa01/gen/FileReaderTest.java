package cs3500.pa01.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the FileReader class
 */
class FileReaderTest {

  private File testVectorFile;
  private File testArrayFile;
  private FileReader fileArrayReader;
  private FileReader fileVectorReader;

  private String arrayInput;
  private String arrayExpectedOutput;
  private String arrayInputV2;
  private String arrayExpectedOutputV2;
  private String arrayInputV3;
  private String arrayExpectedOutputV3;
  private String arrayInputV4;
  private String arrayExpectedOutputV4;
  private String vectorInput;
  private String vectorExpectedOutput;
  private String vectorInputV2;
  private String vectorExpectedOutputV2;

  /**
   * Sets up each test
   */
  @BeforeEach
  void setup() {
    testVectorFile = new File("src/test/resources/vectors.md");
    testArrayFile = new File("src/test/resources/arrays.md");

    fileArrayReader = new FileReader(testArrayFile);
    fileVectorReader = new FileReader(testVectorFile);

    arrayInput = "# Java Arrays\n"
        + "- [[An **array** is a collection of variables of the same type]], referred to \n"
        + "by a common name. \n"
        + "- In Java, arrays are objects, and must be created dynamically (at runtime).\n"
        + "- [[question:::answer]]\n"
        + "- [[:::]]\n"
        + "- [[ ::: ]]\n"
        + "\n"
        + "## Declaring an Array\n"
        + "- [[General Form: type[] arrayName;]]\n"
        + "- ex: int[] myData;\n"
        + "\n"
        + "- The above [[only creates a reference]] to an array [[questio"
        + "n:::answer]] object, but [[no array has\n"
        + "actually been created yet.]] \n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- [[General form:  arrayName = new type[numberOfElements];]]\n"
        + "- ex: myData = new int[100];\n"
        + "\n"
        + "- Data types of the reference and array need to match. \n"
        + "- [[numberOfElements must be a positive Integer.]] [[question:::\n"
        + "answer]]\n"
        + "- [[Gotcha: Array size is not \n"
        + "modifiable once instantiated.]]";

    arrayExpectedOutput = "# Java Arrays\n"
        + "- An **array** is a collection of variables of the same type\n"
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

    arrayInputV2 = "# Java Arrays\n"
        + "- [[An **array** is a collection of variables of the same type]], referred to \n"
        + "by a common name. \n"
        + "- In Java, arrays are objects, and must be created dynamically (at runtime).\n"
        + "\n"
        + "## Declaring an Array\n"
        + "- [[General Form: type[] arrayName;]]\n"
        + "- ex: int[] myData;\n"
        + "\n"
        + "- The above [[only creates a reference]] to an array object, but [[no array has\n"
        + "actually been created yet.]] \n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- [[General form:  arrayName = new type[numberOfElements];]]\n"
        + "- ex: myData = new int[100];\n"
        + "\n"
        + "- Data types of the reference and array need to match. \n"
        + "- [[numberOfElements must be a positive Integer.]]\n"
        + "- [[Gotcha: Array size is not \n"
        + "extra line here\n"
        + "modifiable once instantiated.]]";

    arrayExpectedOutputV2 = "# Java Arrays\n"
        + "- An **array** is a collection of variables of the same type\n"
        + "\n"
        + "## Declaring an Array\n"
        + "- General Form: type[] arrayName;\n"
        + "- only creates a reference\n"
        + "- no array has actually been created yet.\n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- General form:  arrayName = new type[numberOfElements];\n"
        + "- numberOfElements must be a positive Integer.\n"
        + "- Gotcha: Array size is not extra line here modifiable once instantiated.\n";

    arrayInputV3 = "# Java Arrays\n"
        + "- [[An **array** is a collection of "
        + "variables of "
        + "the same type]], referred to \n"
        + "by a common name. \n"
        + "- In Java, arrays are objects, and must be created dynamically (at runtime).\n"
        + "\n"
        + "#TEST\n"
        + "## Declaring an Array\n"
        + "- [[General Form: type[] arrayName;]]\n"
        + "- ex: int[] myData;\n"
        + "\n"
        + "- The above #HELLO [[only creates a reference]] to [[test array object]] an [[array\n"
        + "object]], but [[no array has\n"
        + "actually been created yet.]] \n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- [[General form:  arrayName = new type[numberOfElements];]] [[array object]]\n"
        + "- ex: myData = new int[100];\n"
        + "- The above [[only\n"
        + "creates a\n"
        + "reference]] to [[test "
        + "array where the world revolves around blue\n"
        + "object]] an [[array - object]], but [[no array - has\n"
        + "actually been created yet.]] \n"
        + "\n"
        + "- Data types of the reference and array need to match. \n"
        + "- [[numberOfElements must - be a positive Integer.]]\n"
        + "- [[Gotcha: Array - size is not \n"
        + "extra line here\n"
        + "modifiable once instantiated.]]";

    arrayExpectedOutputV3 = "# Java Arrays\n"
        + "- An **array** is a collection of variables of the same type\n"
        + "\n"
        + "## Declaring an Array\n"
        + "- General Form: type[] arrayName;\n"
        + "- only creates a reference\n"
        + "- test array object\n"
        + "- array object\n"
        + "- no array has actually been created yet.\n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- General form:  arrayName = new type[numberOfElements];\n"
        + "- array object\n"
        + "- only creates a reference\n"
        + "- test array where the world revolves around blue object\n"
        + "- array - object\n"
        + "- no array - has actually been created yet.\n"
        + "- numberOfElements must - be a positive Integer.\n"
        + "- Gotcha: Array - size is not extra line here modifiable once instantiated.\n";

    arrayInputV4 = "\n"
        + "- [[An **array** is a collection of "
        + "variables of "
        + "the same type]], referred to \n"
        + "by a common name. \n"
        + "- In Java, arrays are objects, and must be created dynamically (at runtime).\n"
        + "\n"
        + "- [[General Form: type[] arrayName;]]\n"
        + "- ex: int[] myData;\n"
        + "\n"
        + "- The above [[only creates a reference]] to [[test array object]] an [[array\n"
        + "object]], but [[no array has\n"
        + "actually been created yet.]] \n"
        + "\n"
        + "#TEST\n"
        + "- [[General form:  arrayName = new type['numberOfElements'];]] [[array object]]\n"
        + "- ex: myData = new int[100];\n"
        + "- The above [[only\n"
        + "creates - a\n"
        + "reference]] to [[test "
        + "array where the world revolves around blue\n"
        + "object]] an [[array object]], but [[no array has\n"
        + "actually - been\n"
        +  "created yet.]]\n"
        + "\n"
        + "- Data types of the reference and array need to match. \n"
        + "- [[numberOfElements must be a positive Integer.]]\n"
        + "- [[Gotcha: Array size is not \n"
        + "extra line here\n"
        + "modifiable once instantiated.]]";

    arrayExpectedOutputV4 = ""
        + "- An **array** is a collection of variables of the same type\n"
        + "- General Form: type[] arrayName;\n"
        + "- only creates a reference\n"
        + "- test array object\n"
        + "- array object\n"
        + "- no array has actually been created yet.\n"
        + "- General form:  arrayName = new type['numberOfElements'];\n"
        + "- array object\n"
        + "- only creates - a reference\n"
        + "- test array where the world revolves around blue object\n"
        + "- array object\n"
        + "- no array has actually - been created yet.\n"
        + "- numberOfElements must be a positive Integer.\n"
        + "- Gotcha: Array size is not extra line here modifiable once instantiated.\n";

    vectorInput = "# Vectors\n"
        + "- [[Vectors act like resizable arrays]].\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- [[General Form: Vector<type> v = new Vector();]]\n"
        + "- Example: Vector<Integer> v = new Vector();\n"
        + "\n"
        + "- [[type needs to be a valid reference type]]\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- [[v.add(object of type);]]\n"
        + "\n"
        + "- Reminder - go back and review clearing a vector!";

    vectorExpectedOutput = "# Vectors\n"
        + "- Vectors act like resizable arrays\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- General Form: Vector<type> v = new Vector();\n"
        + "- type needs to be a valid reference type\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- v.add(object of type);\n";

    vectorInputV2 = "# Vectors\n"
        + "- [[Vectors act like resizable arrays]].\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- [[General Form: Vector<type> v =\n"
        + "new\n"
        + "Vector();]]\n"
        + "- Example: Vector<Integer> v = new Vector();\n"
        + "\n"
        + "- [[type needs to be a valid reference type]]\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- [[v.add(object of type);]]\n"
        + "\n"
        + "- Reminder - go back and review clearing a vector!";

    vectorExpectedOutputV2 = "# Vectors\n"
        + "- Vectors act like resizable arrays\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- General Form: Vector<type> v = new Vector();\n"
        + "- type needs to be a valid reference type\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- v.add(object of type);\n";
  }

  /**
   * Tests if the rewrite Markdown works correctly
   */
  @Test
  void testRewriteMarkdown() {
    String arrayInputResult = FileReader.rewriteMarkdown(arrayInput);
    assertEquals(arrayExpectedOutput, arrayInputResult);

    String vectorInputResult = FileReader.rewriteMarkdown(vectorInput);
    assertEquals(vectorExpectedOutput, vectorInputResult);

    String arrayInputV2Result = FileReader.rewriteMarkdown(arrayInputV2);
    assertEquals(arrayExpectedOutputV2, arrayInputV2Result);

    String vectorInputV2Result = FileReader.rewriteMarkdown(vectorInputV2);
    assertEquals(vectorExpectedOutputV2, vectorInputV2Result);

    String arrayInputV3Result = FileReader.rewriteMarkdown(arrayInputV3);
    assertEquals(arrayExpectedOutputV3, arrayInputV3Result);

    String arrayInputV4Result = FileReader.rewriteMarkdown(arrayInputV4);
    assertEquals(arrayExpectedOutputV4, arrayInputV4Result);
  }

  /**
   * Tests if the getFile returns correctly
   */
  @Test
  void testGetFile() {
    assertEquals(testArrayFile, fileArrayReader.getFile());
    assertEquals(testVectorFile, fileVectorReader.getFile());
  }

  /**
   * Tests if the getDefaultContent returns correctly
   */
  @Test
  void testGetDefaultContent() {
    assertEquals(arrayInput, fileArrayReader.getDefaultContent());
    assertEquals(vectorInput, fileVectorReader.getDefaultContent());
  }

  /**
   * Tests if the getKeyContent returns correctly
   */
  @Test
  void testGetKeyContent() {
    assertEquals(arrayExpectedOutput, fileArrayReader.getKeyContent());
    assertEquals(vectorExpectedOutput, fileVectorReader.getKeyContent());
  }

  /**
   * Tests if the readFromFile returns correctly
   */
  @Test
  void testReadFromFile() {
    assertEquals(arrayInput, FileReader.readFromFile(testArrayFile));
    assertEquals(vectorInput, FileReader.readFromFile(testVectorFile));
  }

  /**
   * Tests if the readFromFile throws an error when given an invalid path
   */
  @Test
  void testThrowsReadFromFile() {
    assertThrows(RuntimeException.class,
        () -> FileReader.readFromFile(new File("src/test/resources/doesNotExist.md")));
  }
}