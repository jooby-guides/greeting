package greeting;

import java.util.Optional;

import org.jooby.mvc.GET;
import org.jooby.mvc.Path;

@Path("/mvc")
public class Greetings {

  @Path({"/greeting", "/greeting/:name" })
  @GET
  public Greeting greeting(final Optional<String> name) {
    String salute = "Hello " + name.orElse("World") + "!";

    return new Greeting(salute);
  }
}
