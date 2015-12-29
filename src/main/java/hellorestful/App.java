package hellorestful;

import java.util.concurrent.atomic.AtomicInteger;

import org.jooby.Jooby;
import org.jooby.json.Jackson;

public class App extends Jooby {

  {
    use(new Jackson());

    AtomicInteger idgen = new AtomicInteger();

    get("/greeting", "/greeting/:name", req -> {
      String name = "Hello " + req.param("name").value() + "!";
      return new Greeting(idgen.incrementAndGet(), name);
    });
  }

  public static void main(final String[] args) throws Exception {
    new App().start(args);
  }

}
