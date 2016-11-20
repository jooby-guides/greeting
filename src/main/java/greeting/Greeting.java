package greeting;

import java.util.concurrent.atomic.AtomicInteger;

public class Greeting {

  static AtomicInteger idgen = new AtomicInteger();
  
  public int id;

  public String name;

  public Greeting(final String name) {
    this.id = idgen.incrementAndGet();
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
