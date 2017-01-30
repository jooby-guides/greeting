package greeting;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import org.jooby.test.JoobyRule;
import org.junit.Test;
import org.junit.ClassRule;

/**
 * @author jooby generator
 */
public class AppTest {

  @ClassRule
  public static JoobyRule app = new JoobyRule(new App());

  @Test
  public void greeting() throws Exception {
    get("/greeting?name=Jooby")
        .then()
        .assertThat()
        .body(equalTo("{\"id\":1,\"name\":\"Hello Jooby!\"}"))
        .contentType("application/json;charset=UTF-8");
  }
}
