package ru.practicum.stat.base;

import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class BaseClient {

    protected final RestTemplate restTemplate;

    public BaseClient(RestTemplate rest) {
        this.restTemplate = rest;
    }

    protected ResponseEntity<Object> get(String path) {
        return sendRequest(path);
    }

    protected ResponseEntity<Object> post(Object body, String path) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(body);
        return restTemplate.postForEntity(path, requestEntity, Object.class);
    }

    private <T> ResponseEntity<Object> sendRequest(String path) {
        HttpEntity<T> requestEntity = new HttpEntity<>(null, defaultHeaders());

        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = restTemplate.exchange(path, HttpMethod.GET, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareResponse(responseEntity);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<Object> prepareResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }
}