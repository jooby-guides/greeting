package hellorestful;

import org.jooby.Jooby;

public class App extends Jooby {

  {
    get("/", () -> "Welcome to the hello-restul guide!");
  }

  public static void main(final String[] args) throws Exception {
    new App().start(args);
  }

}
