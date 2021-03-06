package perf;

import ratpack.launch.*;
import ratpack.handling.*;
import ratpack.func.*;
import ratpack.server.*;
import ratpack.perf.incl.*;
import ratpack.registry.*;

public class HandlerFactory implements ratpack.launch.HandlerFactory {

  public Handler create(Registry registry) throws Exception {
    return Handlers.chain(registry.get(ServerConfig.class), chain -> {
        chain
          .handler("stop", new StopHandler())
          .handler("render", ctx -> ctx.render("ok"))
          .handler("direct", ctx -> ctx.getResponse().send("ok"));

        for (int i = 0; i < 100; ++i) {
          chain.handler("handler" + i, ctx -> {
            throw new RuntimeException("unexpected");
          });
        }

        chain.handler("manyHandlers", ctx -> ctx.getResponse().send());
      }
    );
  }

}
