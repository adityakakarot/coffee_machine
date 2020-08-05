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

  CoffeeMachineService(DataManagerService dataManagerService) {
    this.dataManagerService = dataManagerService;
  }

  public void defineCoffeeMachine(Integer outletCount, Map<String, Integer> ingredients) {
    CoffeeMachine coffeeMachine = new CoffeeMachine(Stream.iterate(0, i -> i + 1).limit(outletCount)
        .map(i -> new Outlet()).collect(Collectors.toList()), ingredients.entrySet().stream().map(
        ingredient -> new IngredientItem(new Ingredient(ingredient.getKey()),
            ingredient.getValue())).collect(Collectors.toList()));

    dataManagerService.setCoffeeMachine(coffeeMachine);
  }

  synchronized public void createBeverage(Beverage beverage) throws InsufficientResourcesException {
    Outlet freeOutlet = dataManagerService.getCoffeeMachine().getOutlets().stream().filter(
        Outlet::getAvailable).findFirst().orElseThrow(
        () -> new InsufficientResourcesException("No outlet available"));

    if (!beverage.getRequiredIngredients().stream().allMatch(
        requiredIngredient -> dataManagerService.getCoffeeMachine().getIngredientsStock().stream()
            .anyMatch(ingredientItem ->
                ingredientItem.getIngredient().equals(requiredIngredient.getIngredient())
                    && ingredientItem.getQuantity() >= requiredIngredient.getQuantity()))) {
      throw new InsufficientResourcesException(
          "Not enough ingredients present in the machine to prepare beverage");
    }

    freeOutlet.setAvailable(false);
    beverage.getRequiredIngredients().forEach(
        requiredIngredient -> dataManagerService.getCoffeeMachine().getIngredientsStock()
            .forEach(ingredientItem -> {
              if (ingredientItem.getIngredient().equals(requiredIngredient.getIngredient()))
                ingredientItem.setQuantity(
                    ingredientItem.getQuantity() - requiredIngredient.getQuantity());
            }));
  }
}
