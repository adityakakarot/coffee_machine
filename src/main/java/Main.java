import java.io.IOException;

import Service.InputParserService;
import org.json.simple.parser.ParseException;

public class Main {

  public static void main(String[] args) throws IOException, ParseException {
    InputParserService inputParserService = new InputParserService();
    inputParserService.parseInput();
  }
}
