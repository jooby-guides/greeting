package greeting;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.jooby.mvc.Path;
import org.jooby.mvc.GET;

@Path("/mvc")
public class Greetings {

  @Path({"/greeting", "/greeting/:name" })
  @GET
  public Greeting greeting(Optional<String> name) {
    String salute = "Hello " + name.orElse("World") + "!";

    return new Greeting(salute);
  }
}
