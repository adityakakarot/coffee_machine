package Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.InsufficientResourcesException;

import Model.Beverage;
import Model.CoffeeMachine;
import Model.Ingredient;
import Model.IngredientItem;
import Model.Outlet;

public class CoffeeMachineService {
  DataManagerService dataManagerService;

  public CoffeeMachineService(DataManagerService dataManagerService) {
    this.dataManagerService = dataManagerService;
  }

  public void defineCoffeeMachine(Integer outletCount, Map<String, Integer> ingredients) {
    CoffeeMachine coffeeMachine = new CoffeeMachine(Stream.iterate(0, i -> i + 1)
        .limit(outletCount)
        .map(i -> new Outlet())
        .collect(Collectors.toList()), ingredients.entrySet()
        .stream()
        .map(ingredient -> new IngredientItem(new Ingredient(ingredient.getKey()),
            ingredient.getValue()))
        .collect(Collectors.toList()));

    dataManagerService.setCoffeeMachine(coffeeMachine);
  }

  synchronized public void prepareBeverage(Beverage beverage)
      throws InsufficientResourcesException {
    /* Get free outlet or else throw exception */
    Outlet freeOutlet = dataManagerService.getCoffeeMachine()
        .getOutlets()
        .stream()
        .filter(Outlet::getAvailable)
        .findFirst()
        .orElseThrow(() -> new InsufficientResourcesException("No outlet available"));

    /* Check if all ingredients are present in right quantity */
    if (!beverage.getRequiredIngredients()
        .stream()
        .allMatch(requiredIngredient -> dataManagerService.getCoffeeMachine()
            .getIngredientsStock()
            .stream()
            .anyMatch(ingredientItem -> ingredientItem.getIngredient()
                .getIdentifier()
                .equals(requiredIngredient.getIngredient()
                    .getIdentifier())
                && ingredientItem.getQuantity() >= requiredIngredient.getQuantity()))) {
      throw new InsufficientResourcesException(
          "Ingredients are not sufficient for " + beverage.getIdentifier());
    }

    freeOutlet.setAvailable(false);
    beverage.getRequiredIngredients()
        .forEach(requiredIngredient -> dataManagerService.getCoffeeMachine()
            .getIngredientsStock()
            .forEach(ingredientItem -> {
              if (ingredientItem.getIngredient()
                  .getIdentifier()
                  .equals(requiredIngredient.getIngredient()
                      .getIdentifier()))
                ingredientItem.setQuantity(
                    ingredientItem.getQuantity() - requiredIngredient.getQuantity());
            }));

    freeOutlet.setAvailable(true);
  }
}
