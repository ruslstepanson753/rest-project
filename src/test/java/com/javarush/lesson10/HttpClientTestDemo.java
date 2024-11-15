package com.javarush.lesson10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class HttpClientTestDemo {

    @Test
    public void whenOpenIndexPage_thenReturnBody()  throws Exception {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(1))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:1234/"))
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        int statusCode = httpResponse.statusCode();
        Assertions.assertEquals(200, statusCode);

        List<String> list = httpResponse.headers().map().get("Content-Length");
        Assertions.assertTrue(list.size() > 0);
        long length = Long.parseLong(list.get(0));
        Assertions.assertTrue(length > 0);

        String body = httpResponse.body();
        Assertions.assertTrue(body.contains("</body>"));
        httpClient.close();
    }

    @Test
    public void whenSendDeleteRequest_thenReturnCorrectResponse() throws Exception {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(1))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:1234/messages/1"))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        int statusCode = httpResponse.statusCode();
        String responseBody = httpResponse.body();

        // Проверка статуса и содержимого тела в зависимости от успешности удаления
        if (statusCode == 200) {
            Assertions.assertEquals("true", responseBody, "Response body should be 'true' for successful DELETE request");
        } else if (statusCode == 405) {
            Assertions.assertEquals("false", responseBody, "Response body should be 'false' for unsuccessful DELETE request");
        } else {
            Assertions.fail("Unexpected status code: " + statusCode);
        }

        httpClient.close();
    }
}
