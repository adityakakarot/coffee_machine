import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.naming.InsufficientResourcesException;

import Model.Beverage;
import Model.Ingredient;
import Model.IngredientItem;
import Service.CoffeeMachineService;
import Service.DataManagerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class CoffeeMachineServiceTest {
  DataManagerService dataManagerService;

  CoffeeMachineService coffeeMachineService;

  @BeforeEach
  public void testPrepareCoffee() {
    dataManagerService = new DataManagerService();
    coffeeMachineService = new CoffeeMachineService(dataManagerService);

    Integer outlets = 1;
    Map<String, Integer> ingredients = new HashMap<>();
    ingredients.put("ingredient 1", 10);
    coffeeMachineService.defineCoffeeMachine(outlets, ingredients);
  }

  private Beverage createBeverage() {
    Set<IngredientItem> requiredIngredients = new HashSet<>();
    requiredIngredients.add(new IngredientItem(new Ingredient("ingredient 1"), 9));
    return new Beverage("beverage 1", requiredIngredients);
  }

  @Test
  public void testFirstBeverage() throws InsufficientResourcesException {
    Assertions.assertDoesNotThrow(() -> coffeeMachineService.prepareBeverage(createBeverage()));
  }

  @Test
  public void testMultipleBeverage() throws InsufficientResourcesException {
    coffeeMachineService.prepareBeverage(createBeverage());

    Assertions.assertThrows(InsufficientResourcesException.class,
        () -> coffeeMachineService.prepareBeverage(createBeverage()));
  }
}
