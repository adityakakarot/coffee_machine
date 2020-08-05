package Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.InsufficientResourcesException;

import Model.Beverage;
import Model.Ingredient;
import Model.IngredientItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InputParserService {
  DataManagerService dataManagerService;

  CoffeeMachineService coffeeMachineService;

  public InputParserService() {
    dataManagerService = new DataManagerService();
    coffeeMachineService = new CoffeeMachineService(dataManagerService);
  }

  public void parseInput() throws IOException, ParseException {
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader(
        "/Users/aditya/Practice/dunzo/coffee-machine/src/main/java/com/dunzo/coffee_machine"
            + "/sample.json"));

    JSONObject input = (JSONObject) obj;
    JSONObject machine = (JSONObject) input.get("machine");
    JSONObject outlets = (JSONObject) machine.get("outlets");

    /* Read coffe machine data */
    Integer outletCount = Integer.parseInt(outlets.get("count_n")
        .toString());
    Map<String, Integer> itemsQuantity = new ObjectMapper().readValue(
        machine.get("total_items_quantity")
            .toString(), HashMap.class);
    coffeeMachineService.defineCoffeeMachine(outletCount, itemsQuantity);

    /* Read beverages data */
    Map<String, Object> beverages = new ObjectMapper().readValue(machine.get("beverages")
        .toString(), HashMap.class);

    beverages.forEach((beverageName, beverageItemsObj) -> {
      try {
        Map<String, Integer> beverageItems = new ObjectMapper().readValue(
            new ObjectMapper().writeValueAsString(beverageItemsObj), HashMap.class);

        Set<IngredientItem> ingredientItemList = beverageItems.entrySet()
            .stream()
            .map(ingredient -> new IngredientItem(new Ingredient(ingredient.getKey()),
                ingredient.getValue()))
            .collect(Collectors.toSet());

        coffeeMachineService.prepareBeverage(new Beverage(beverageName, ingredientItemList));
        System.out.println(beverageName + " successfully prepared.");
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      } catch (InsufficientResourcesException e) {
        System.out.println(e.getMessage());
      }
    });
  }
}
