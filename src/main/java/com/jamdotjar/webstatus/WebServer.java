package com.jamdotjar.webstatus;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class WebServer {
    private Undertow server;
    private final int port;

    public WebServer(int port) {this.port = port;}

    public void start() {
        server = Undertow.builder()
                .addHttpListener(port,  "localhost")
                .setHandler(buildHandlers()).build();
        server.start();
    }

    public void stop() {
        server.stop();
    }

    private HttpHandler buildHandlers() {
        HttpHandler httpHandler = new HttpHandler() {
            @Override
            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchange.getResponseSender().send("Hello World");
            }
        };
        return httpHandler;
    }
}

