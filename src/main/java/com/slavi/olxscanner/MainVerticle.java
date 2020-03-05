package com.slavi.olxscanner;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;

import java.util.HashMap;
import java.util.Map;

public class MainVerticle extends AbstractVerticle {
  private OLXClient olx;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    olx = new OLXClient(vertx);

    Router router = Router.router(vertx);
    HttpServer server = vertx.createHttpServer();
    server.requestHandler(router);

    router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT));
    router.get("/").handler(this::index);
    router.get("/offers/olx/:keyword").handler(this::search);

    server.listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  private void index(RoutingContext ctx) {
    ctx.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(new HashMap<String, String>()));
  }

  private void search(RoutingContext ctx) {
    String keyword = ctx.request().getParam("keyword");

    OfferListResponse response;
    olx.search(keyword, res -> {
      ctx.response()
        .putHeader("content-type", "application/json; charset=utf-8");

      if (res.succeeded()) {
        ctx.response().end(Json.encodePrettily(res.result()));
      } else {
        Map<String, String> errorResponse = new HashMap<String, String>();
        errorResponse.put("error", res.cause().getMessage());
        ctx.response().end(Json.encodePrettily(errorResponse));
      }
    });
  }

}
