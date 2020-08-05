package Model;

import java.util.Set;

public class Beverage {
  private String identifier;

  private Set<IngredientItem> requiredIngredients;

  public Beverage(String identifier, Set<IngredientItem> requiredIngredients) {
    this.identifier = identifier;
    this.requiredIngredients = requiredIngredients;
  }

  public Set<IngredientItem> getRequiredIngredients() {
    return requiredIngredients;
  }
}
