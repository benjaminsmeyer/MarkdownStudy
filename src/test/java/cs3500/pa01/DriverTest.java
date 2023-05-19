package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa01.gen.FileReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests the Driver class
 */
class DriverTest {

  /**
   * Fake test
   */
  @Test
  public void fakeTest() {
    assertEquals(5, 5);
    Driver drive = new Driver();
  }

  /**
   * Tests main for invalid arguments
   */
  @Test
  void testMainInvalidArgs() {
    String[] finalArgs = new String[10];
    assertThrows(IllegalArgumentException.class, () -> Driver.main(finalArgs));
    String[] secondFinalArgs = new String[2];
    assertThrows(IllegalArgumentException.class, () -> Driver.main(secondFinalArgs));
  }

  /**
   * Tests main for file results
   */
  @Test
  void testMainWithArgs() throws IOException {
    String[] finalArgs = new String[3];
    finalArgs[0] = "src/test/resources";
    finalArgs[1] = "filename";
    finalArgs[2] = "src/test/outputResults/works.md";

    String arrayExpectedOutput = "# Java Arrays"
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

    String vectorExpectedOutput = "# Vectors"
        + "\n- Vectors act like resizable arrays\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- General Form: Vector<type> v = new Vector();\n"
        + "- type needs to be a valid reference type\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- v.add(object of type);\n";

    String questionExpectedOutput = "question:::answer:::HARD\n"
        + "What is the capital of Canada?:::The capital is Ottawa.:::HARD\n"
        + "Which country is known as the Land of the Rising Sun?:::Japan.:::HARD\n"
        + "What is the largest river in Africa?:::The largest river is the Nile River.:::HARD\n"
        +
        "What is the tallest mountain in North America?::"
        + ":The tallest mountain is Denali (also known as Mount McKinley).:::HARD\n"
        + "Which continent is the driest inhabited continent on Earth?:::Australia.:::HARD\n"
        +
        "What is the longest river in South America?:::"
        + "The longest river is the Amazon River.:::HARD\n"
        +
        "What is the highest waterfall in the world?:::The highest waterfall is "
        + "Angel Falls in Venezuela.:::HARD\n"
        +
        "Which country is the largest in terms of land are"
        + "a?:::The largest country is Russia.:::HARD\n"
        + "Which continent is known as the \"Roof of the World\"?:::Asia.:::HARD\n"
        + "What is the largest desert in Asia?:::The largest desert is the Gobi Desert.:::HARD\n"
        +
        "Which country is the smallest in terms of land area?:::The "
        + "smallest country is Vatican City.:::HARD\n"
        + "What is the official language of Brazi"
        + "l?:::The official language is Portuguese.:::HARD\n"
        +
        "Which city is located on two continents?:::Istanbul, Turkey. "
        + "It is located on both Europe and Asia.:::HARD\n"
        + "What is the largest island in the world?:::The largest island is Greenland.:::HARD\n"
        + "Which country is known as the Land of Fire and Ice?:::Iceland.:::HARD\n"
        + "What is the longest river in Europe?:::The longest river is the Volga River.:::HARD\n"
        + "Which country is famous for its tulips and windmills?:::The Netherlands.:::HARD\n"
        + "What is the capital of Argentina?:::The capital is Buenos Aires.:::HARD\n"
        + "Which country is known as the Land of a Thousand Lakes?:::Finland.:::HARD\n"
        + "What is the largest lake in Africa?:::The largest lake is Lake Victoria.:::HARD\n"
        + "Which country is located in both Europe and Asia?::"
        + ":Russia.:::HARD\n"
        + "What is the official language of Japan?:::The offic"
        + "ial language is Japanese.:::HARD\n"
        + "What is the largest coral reef system in the world?:"
        + "::The largest coral reef system is the Great Barrier Reef in Australia.:::HARD\n"
        + "Which country is known as the Land of the Midnight Sun?:::Norway.:::HARD\n"
        + "What is the largest lake in North America?:::The largest lake is Lake Superior.:::HARD\n"
        + "What is the capital of Mexico?:::The capital is Mexico City.:::HARD";

    Driver.main(finalArgs);

    assertEquals((arrayExpectedOutput + "\n" + vectorExpectedOutput).trim(),
        FileReader.readFromFile(new File("src/test/outputResults/works.md")));

    String output = FileReader.readFromFile(new File("src/test/questions/questionsfordrivergen.sr"));
    assertEquals(questionExpectedOutput, output);

    String[] badFinalArgs = new String[3];
    badFinalArgs[0] = "src/test/run";
    badFinalArgs[1] = "filename";
    badFinalArgs[2] = "////////";

    assertThrows(RuntimeException.class, () -> Driver.main(badFinalArgs));
  }

  /**
   * Tests main for study session
   */
  @Test
  void testMainWithNoArgs() throws IOException {

    String userInput = "src/test/questions/questionsfordrivergen.sr\n100\n1\n"
        + "1\n2\n3\n4\n5\nn\n3\n2\n1\n3\n2\nn\n-1\nn\n2\n4\ny";

    ByteArrayInputStream message = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(message);

    String[] args = new String[0];
    Driver.main(args);

    String expectedOne = FileReader.readFromFile(new File("src/test/questions/expecteddriverquestionsone.sr"));
    String expectedTwo = FileReader.readFromFile(new File("src/test/questions/expecteddriverquestionstwo.sr"));
    String actual = FileReader.readFromFile(new File("src/test/questions/questionsfordriverstudy.sr"));

    if (expectedOne.equals(actual)) {
      assertEquals(expectedOne, actual);
    } else {
      assertEquals(expectedTwo, actual);
    }

  }

}