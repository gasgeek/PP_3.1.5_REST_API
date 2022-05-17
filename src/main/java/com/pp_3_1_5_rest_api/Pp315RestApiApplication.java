package com.pp_3_1_5_rest_api;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class Pp315RestApiApplication {

    static RestTemplate restTemplate = new RestTemplate();
    static String baseUrl = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
//        SpringApplication.run(RestPp315Application.class, args);

        methodOfRestTemplate();
    }

    private static void methodOfRestTemplate() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "",
                HttpMethod.GET,
                requestEntity,
                String.class);
        responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie", responseEntity.getHeaders().get("Set-Cookie").stream().collect(Collectors.joining(";")));

        HttpEntity<Object> requestEntity2 = new HttpEntity<>(headers);
        allUsers(requestEntity2);


        User user = new User(3L, "James", "Brown", (byte) 35);
        requestEntity2 = new HttpEntity<>(user, headers);
        addUser(requestEntity2);

        allUsers(requestEntity2);

        user.setName("Thomas");
        user.setLastName("Shelby");
        user.setAge((byte) 39);
        updateUser(requestEntity2);

        allUsers(requestEntity2);

        deleteUser(requestEntity2, user.getId());

        allUsers(requestEntity2);
    }

    public static void allUsers(HttpEntity<Object> requestEntity) {
        ResponseEntity<List> responseEntity = restTemplate.exchange(baseUrl + "",
                HttpMethod.GET,
                requestEntity,
                List.class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("status code - " + statusCode);

        List userString = responseEntity.getBody();
        System.out.println("response body - " + userString);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("response headers - " + responseHeaders);

        System.out.println(responseEntity.getBody());
    }

    public static void addUser(HttpEntity<Object> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "",
                HttpMethod.POST,
                requestEntity,
                String.class);

        responseEnt(responseEntity);
    }

    private static void updateUser(HttpEntity<Object> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "",
                HttpMethod.PUT,
                requestEntity,
                String.class);

        responseEnt(responseEntity);
    }

    private static void deleteUser(HttpEntity<Object> requestEntity, Long id) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/" + id,
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        responseEnt(responseEntity);
    }

    private static void responseEnt(ResponseEntity<String> responseEntity) {
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("status code - " + statusCode);

        String userString = responseEntity.getBody();
        System.out.println("response body - " + userString);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("response headers - " + responseHeaders);
    }
}