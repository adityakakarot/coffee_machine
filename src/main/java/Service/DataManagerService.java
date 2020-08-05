package Service;

import Model.CoffeeMachine;

public class DataManagerService {
  private CoffeeMachine coffeeMachine;

  public CoffeeMachine getCoffeeMachine() {
    return coffeeMachine;
  }

  public void setCoffeeMachine(CoffeeMachine coffeeMachine) {
    this.coffeeMachine = coffeeMachine;
  }
}
