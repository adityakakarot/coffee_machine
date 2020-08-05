import java.util.List;
import java.util.Set;

public class CoffeeMachine {
  final List<Outlet> outlets;

  Set<IngredientItem> ingredientsStock;

  public CoffeeMachine(List<Outlet> outlets, Set<IngredientItem> ingredientsStock) {
    this.outlets = outlets;
    this.ingredientsStock = ingredientsStock;
  }

  public List<Outlet> getOutlets() {
    return outlets;
  }

  public Set<IngredientItem> getIngredientsStock() {
    return ingredientsStock;
  }

  public void setIngredientsStock(Set<IngredientItem> ingredientsStock) {
    this.ingredientsStock = ingredientsStock;
  }
}
