package Model;

import java.util.List;

public class CoffeeMachine {
  private final List<Outlet> outlets;

  private List<IngredientItem> ingredientsStock;

  public CoffeeMachine(List<Outlet> outlets, List<IngredientItem> ingredientsStock) {
    this.outlets = outlets;
    this.ingredientsStock = ingredientsStock;
  }

  public List<Outlet> getOutlets() {
    return outlets;
  }

  public List<IngredientItem> getIngredientsStock() {
    return ingredientsStock;
  }

  public void setIngredientsStock(List<IngredientItem> ingredientsStock) {
    this.ingredientsStock = ingredientsStock;
  }
}
