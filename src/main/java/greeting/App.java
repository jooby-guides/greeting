package greeting;

import org.jooby.Jooby;
import org.jooby.json.Jackson;

public class App extends Jooby {

  {
    /** JSON: */
    use(new Jackson());

    /**
     * GET /greeting -> Hello World!
     * GET /greeting/Jooby -> Hello Jooby!
     * GET /greeting?name=Jooby -> Hello Jooby!
     */
    get("/greeting", "/greeting/:name", req -> {
      String name = "Hello " + req.param("name").value("World") + "!";

      return new Greeting(name);
    });

    /**
     * MVC version
     */
    use(Greetings.class);
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
