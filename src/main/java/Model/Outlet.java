package Model;

public class Outlet {
  private Boolean isAvailable;

  public Outlet() {
    isAvailable = true;
  }

  public Boolean getAvailable() {
    return isAvailable;
  }

  public void setAvailable(Boolean available) {
    isAvailable = available;
  }
}
