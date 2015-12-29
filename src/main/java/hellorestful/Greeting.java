package hellorestful;

public class Greeting {

  public int id;

  public String name;

  public Greeting(final int id, final String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
