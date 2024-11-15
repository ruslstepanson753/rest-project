package com.javarush.lesson09.conroller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class BaseController implements HttpHandler {
    protected void sendResponse(HttpExchange exchange, int httpOk, Object data) throws IOException {
        byte[] bytes = data.toString().getBytes();
        exchange.sendResponseHeaders(httpOk, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}
