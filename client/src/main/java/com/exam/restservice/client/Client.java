package com.exam.restservice.client;

//import com.exam.restserviceg.models.Greeting;

//import models.Greeting;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class Client {


    protected static final String URL_BASE = "http://localhost:8080";
    protected static final String URL_RUN = URL_BASE + "/";
    protected static final String URL_ANY_RUN = URL_BASE + "/greeting";


    public static void simpleReq() {
        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

//        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        // Request to return XML format
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("my_other_key", "my_other_value");

        // HttpEntity<Employee[]>: To get result as Employee[].
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(
                URL_RUN, HttpMethod.GET
                , entity, String.class
        );

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);

        // Status Code: 200
        if (statusCode == HttpStatus.OK) {
            // Response Body Data
            String res = response.getBody();
            System.out.println(res);
        }
    }

    public static void mortGreetend() {
        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        // Request to return XML format
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("my_other_key", "my_other_value");

        // HttpEntity<Employee[]>: To get result as Employee[].
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(URL_ANY_RUN)
                .queryParam("qql", "{qwer}")
                .queryParam("qaz", "{qaz}")
                .queryParam("qazwsx", "{xswzaq}")
                .encode()
                .toUriString();

        Map<String, String> eert = new HashMap<>();
        eert.put("qwer", "1234");
        eert.put("qaz", "klj");
        eert.put("xswzaq", "qwertyui");
        eert.put("njk", "09865");

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.getForEntity(
                urlTemplate, String.class, eert
        );

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);

        // Status Code: 200
        if (statusCode == HttpStatus.OK) {
            // Response Body Data
            String res = response.getBody();
            System.out.println(res);
        }
    }

    public static void main(String[] args) {
        mortGreetend();
    }
}
