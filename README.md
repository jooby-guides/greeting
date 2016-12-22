[![Build Status](https://travis-ci.org/jooby-guides/greeting.svg?branch=master)](https://travis-ci.org/jooby-guides/greeting)
# greeting

You will learn how to build a simple **JSON API** with [Jooby](http://jooby.org/apidocs/org/jooby/Jooby.html).

The service will be available at:

```
http://localhost:8080/greeting
```

and respond with `JSON` response:

```json
{
  "id": 1,
  "name": "Hello World!"
}
```

[Jooby](http://jooby.org) offers two programming model:

* **script**, where routes are writing via **DSL** and lambdas.
* **mvc**, where routes are writing as class method and annotations.

In this guide you will learn how to write a simple **JSON API** using both models.

# requirements

Make sure you have all these software installed it in your computer:

* A text editor or IDE
* [JDK 8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
* [Maven 3+](http://maven.apache.org/)

# ready

Open a terminal/console and paste:

```bash
mvn archetype:generate -B -DgroupId=org.jooby.guides -DartifactId=greeting -Dversion=1.0 -DarchetypeArtifactId=jooby-archetype -DarchetypeGroupId=org.jooby -DarchetypeVersion=1.0.1
```

A simple `hello world` application is ready to run. Try now:

```
cd greeting

mvn jooby:run
```

Open a browser and type:

```
http://localhost:8080
```

> **TIP**: If you make a change `jooby:run` automatically restart and reload your application. More at [development tools](http://jooby.org/doc/devtools).

# quick preview

Before moving forward let's have a look at `App.java`:

```java
package greeting;

import org.jooby.Jooby;

public class App extends Jooby { // 1 extends Jooby

  {
    // 2 define some routes
    get("/", () -> "Hello World!");
  }

  public static void main(final String[] args) {
    // 3 run this app
    run(App::new, args);
  }

}
```

That's all you need to get up and running a simple **Hello World** [Jooby](http://jooby.org/apidocs/org/jooby/Jooby.html) application. 

# script route

Now we already see how a [Jooby](http://jooby.org/apidocs/org/jooby/Jooby.html) application looks like, we are going to create a simple greeting **JSON API**:

First `Greeting.java`:

```java
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
```

## create a route

Go to `App.java` and add this line:

```java
{
  get("/greeting", () -> new Greeting("Hello World!"));
}
```

Try it:

```
http://localhost:8080/greeting
```

You'll see `Hello World!` in your browser, not bad ugh?

Not bad at all! But if you look closely we send a `text/html` response not an `application/json` response.

Before building **JSON** response let's see how to read a HTTP parameter.

## adding a name parameter

We are going to improve our service by allowing a name parameter:

```java
...
{
  ...
  get("/greeting", req -> {
    String name = "Hello " + req.param("name").value() + "!";

    return new Greeting(name);
  });
}
```

HTTP parameters are accessible via [Request.param(String)](http://jooby.org/apidocs/org/jooby/Request.html#param-java.lang.String-) method, that is why we change a bit our route to access the [Request](http://jooby.org/apidocs/org/jooby/Request.html) object.

Try it:

```
http://localhost:8080/greeting?name=Jooby
```

What if you call the service without a ```name```? You will get a ```Bad Request(400)``` response. Let's fix that with an ```Optional``` parameter:

```java
...
{
  ...
  get("/greeting", req -> {
    String name = "Hello " + req.param("name").value("World") + "!";

    return new Greeting(name);
  });
}
```

Same as before, we ask for the HTTP parameter but this time we set a default value: ```World```.

The [Mutant.value(String)](http://jooby.org/apidocs/org/jooby/Mutant.html#value-java.lang.String-) is syntax sugar for:

```
String name = req.param("name").toOptional().orElse("World");
```

Optional parameters are represented by `java.util.Optional`.

Try it with a parameter:

    http://localhost:8080/greeting?name=Jooby

Try it without a parameter:

    http://localhost:8080/greeting

## path parameter

If you want or prefer a ```path``` parameter, you can replace the path pattern with: ```/greeting/:name``` or allow both of them:

```java
...
{
  ...
  get("/greeting", "/greeting/:name", req -> {
    String name = "Hello " + req.param("name").value("World") + "!";

    return new Greeting(name);
  });
}
```

Try it with a path parameter:

```
http://localhost:8080/greeting/Jooby
```

Try it with a query parameter:

```
http://localhost:8080/greeting?name=Jooby
```

Nice ugh?

# json

[Jooby](http://jooby.org) is a micro-web framework in order to write a **JSON** response we need one of the available [json modules](http://jooby.org/doc/parser-and-renderer).

Here we will use [jackson](https://github.com/jooby-project/jooby/tree/master/jooby-jackson) but keep in mind the process is exactly the same if you choose any other module.

## dependency

Let's add the [jackson](https://github.com/jooby-project/jooby/tree/master/jooby-jackson) dependency to your project:

```xml
<dependency>
  <groupId>org.jooby</groupId>
  <artifactId>jooby-jackson</artifactId>
  <version>1.0.1</version>
</dependency>
```

If `jooby:run` is running, please restart it. We need to force a restart due we added a new dependency.

## use

Let's [use](http://jooby.org/apidocs/org/jooby/Jooby.html#use-org.jooby.Jooby.Module-) the module in our `App.java`:

```java

import org.jooby.json.Jackson;
...

{
  use(new Jackson());

  ...

  get("/greeting", "/greeting/:name", req -> {
    String name = "Hello " + req.param("name").value("World") + "!";

    return new Greeting(name);
  });
}
```

Our service method didn't change at all! we just [use](http://jooby.org/apidocs/org/jooby/Jooby.html#use-org.jooby.Jooby.Module-) the [jackson](https://github.com/jooby-project/jooby/tree/master/jooby-jackson) module!!

Try it

    http://localhost:8080/greeting

You will get a nice **JSON** response:

```json
{
  "id": 1,
  "name": "Hello World!"
}
```

# mvc route

As a learning **exercise** we will build the same service using the **MVC** programming model.

## create a route

Create a new `Greetings.java` class like:

```java
package greeting;

import org.jooby.mvc.Path;
import org.jooby.mvc.GET;

@Path("/mvc")
public class Greetings {

  @Path("/greeting")
  @GET
  public String greeting() {
    return "Hello World!";
  }
}
```

The **MVC** programming model is similar to [Spring](http://spring.io) and/or [Jersey](https://jersey.java.net), except a `MVC` routes must be registered at at application startup time.

## registering a mvc route

Go to `App.java` and add this line:

```java
{
  ...

  use(Greetings.class);
}
```

We try to keep `reflection`, `classpath scanning` and `annotations` to minimum that is one of reason why they need to be explicitly registered.

The other reason is the **route order**, because routes are executed in the **order** they are defined.

Having said that, we do offer a service [scanner](https://github.com/jooby-project/jooby/tree/master/jooby-scanner) module that automatically register `MVC` routes.

Try it:

    http://localhost:8080/mvc/greeting

## adding a name parameter

As we do with script route we are going to add a **required** `name` parameter:

```java
@Path("/greeting")
@GET
public String greeting(String name) {
  return "Hello " + name + "!";
}
```

Try it:

    http://localhost:8080/mvc/greeting?name=Jooby

To call the service without a name we need to make the `name` parameter an optional parameter:

```java
import java.util.Optional;
...

@Path("/greeting")
@GET
public String greeting(Optional<String> name) {
  return "Hello " + name.orElse("World") + "!";
}
```

Try it with a parameter:

    http://localhost:8080/mvc/greeting?name=Jooby

Try it without:

    http://localhost:8080/mvc/greeting

## path parameter

If you want or prefer a path parameter, you can replace the path pattern with: `/greeting/:name` or allow both of them:

```java
@Path({"/greeting", "/greeting/:name"})
@GET
public String greeting(Optional<String> name) {
  return "Hello " + name.orElse("World") + "!";
}
```

Try it with a path parameter:

    http://localhost:8080/mvc/greeting/Jooby

Try it with a query parameter:

    http://localhost:8080/mvc/greeting?name=Jooby

# conclusion

Your application might looks like something similar to this:

```java
package greeting;

import java.util.concurrent.atomic.AtomicInteger;

import org.jooby.Jooby;
import org.jooby.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
```

This is an unreal and simple **JSON API** but helps to demonstrate how simple and easy is to build such application in [Jooby](http://jooby.org). **Simplicity** is one of the [Jooby](http://jooby.org) goals.

We also demonstrate the **script** and **mvc** programming models, you can pick one or mix both in a single application.

The **script** programming model is perfect for getting thing done quickly and/or for small applications. It is also possible to use the **script** routes on large applications, where you usually split routes in one or more applications and then you compose all those small application into a one.

The **mvc** programming model is a bit more verbose but probably better for large scale applications.

A common pattern for **medium** scale applications is to write the **UI** routes (those who generated HTML) using the **script** programming model while the **Business/API** routes using the **MVC** programming model. 

In short, **script** or **mvc** is matter of taste and/or depends on your background.


That's all for now, if you like what you see here please follow us at [@joobyproject](https://twitter.com/joobyproject) and [Github](https://github.com/jooby-project/jooby)

# source code

* Complete source code available at: [jooby-guides/greeting](https://github.com/jooby-guides/greeting)

# help and support

* Discuss, share ideas, ask questions at [group](https://groups.google.com/forum/#!forum/jooby-project) or [slack](https://jooby.slack.com)
* Follow us at [@joobyproject](https://twitter.com/joobyproject) and [GitHub](https://github.com/jooby-project/jooby/tree/master)

