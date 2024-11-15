package com.javarush.lesson09.conroller;

import com.javarush.lesson09.HttpStarter;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static java.net.HttpURLConnection.HTTP_OK;

public class IndexController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        log.info("IndexController start URI={}", exchange.getRequestURI());
        InputStream index = HttpStarter.class.getResourceAsStream("index.html");
        try (index) {
            String html = new String(Objects.requireNonNull(index).readAllBytes());
            sendResponse(exchange, HTTP_OK, html);
            log.info("IndexController complete URI={} length={}", exchange.getRequestURI(), html.length());
        }
    }
}
