package com.javarush.lesson09.conroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.lesson09.model.MessageOwnClass;
import com.javarush.lesson09.service.MessageService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static java.net.HttpURLConnection.*;

public class MessageController extends BaseController {
    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    public MessageController(ObjectMapper objectMapper, MessageService messageService) {
        this.objectMapper = objectMapper;
        this.messageService = messageService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String id = exchange.getRequestURI().toString().replaceAll(".*/messages/", "");
        switch (method) {
            case "GET" -> {
                Object obj = !id.isEmpty()
                        ? messageService.get(Long.parseLong(id))
                        : messageService.getAll().values();
                Object data = objectMapper.writeValueAsString(obj);
                sendResponse(exchange, HTTP_OK, data);
            }
            case "POST" -> {
                MessageOwnClass message = objectMapper.readValue(exchange.getRequestBody(), MessageOwnClass.class);
                Boolean result = messageService.post(message);
                sendResponse(exchange, result ? HTTP_OK : HTTP_BAD_METHOD, result);
            }

            case "PUT" -> {
                MessageOwnClass message = objectMapper.readValue(exchange.getRequestBody(), MessageOwnClass.class);
                Boolean result = messageService.put(message);
                sendResponse(exchange, result ? HTTP_OK : HTTP_BAD_METHOD, result);
            }

            case "DELETE" -> {
                MessageOwnClass forDelete = messageService.get(Long.parseLong(id));
                Boolean result = forDelete != null && messageService.delete(forDelete);
                sendResponse(exchange, result ? HTTP_OK : HTTP_BAD_METHOD, result);
            }
            default -> exchange.sendResponseHeaders(HTTP_NOT_IMPLEMENTED, 0);
        }
    }


}
