package greeting;

import org.junit.Test;

/**
 * @author jooby generator
 */
public class AppTest extends BaseTest {

  @Test
  public void greeting() throws Exception {
    server.get("/greeting?name=Jooby")
        .expect("{\"id\":1,\"name\":\"Hello Jooby!\"}")
        .header("Content-Type", "application/json;charset=UTF-8");
  }
}
