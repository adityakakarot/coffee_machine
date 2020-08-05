package Model;

public class IngredientItem {
  private Ingredient ingredient;

  private Integer quantity;

  public IngredientItem(Ingredient ingredient, Integer quantity) {
    this.ingredient = ingredient;
    this.quantity = quantity;
  }

  public Ingredient getIngredient() {
    return ingredient;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
