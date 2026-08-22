package com.jamdotjar.webstatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamdotjar.webstatus.status.StatusService;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebServer {
    private Undertow server;
    private final int port;
    private StatusService statusService;

    public WebServer(int port) {this.port = port;}

    public static String pageData;

    private static String readResource(String path) {
        try {
            InputStream in = WebServer.class.getClassLoader().getResourceAsStream(path);
            if (in == null) {
                throw new RuntimeException("Resource not found: " + path);
            }
            return new String(in.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read resource: " + path, e);
        }
    }

    public void start(StatusService statusService) {
        this.statusService = statusService;
        pageData = readResource("web/index.html");
        server = Undertow.builder()
                .addHttpListener(port, "localhost")
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
                if (exchange.getRelativePath().equals("/api/status")){
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                    exchange.getResponseSender().send(statusService.updateJson());
                    return;
                }

                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
                exchange.getResponseSender().send(pageData);
            }
        };
        return httpHandler;
    }
}

