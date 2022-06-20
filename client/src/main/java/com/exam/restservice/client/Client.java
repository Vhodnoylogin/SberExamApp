package com.exam.restservice.client;

//import com.exam.restserviceg.models.Greeting;

//import models.Greeting;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Client {


    public static void testClient() {
        // Send request with GET method
        RequestAirport<String> req = new RequestAirport<>(new ParameterizedTypeReference<>() {
        });
        ResponseEntity<String> response = req.get(12L);

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Status Code: " + statusCode);

        // Status Code: 200
        if (statusCode == HttpStatus.OK) {
            // Response Body Data
            String res = response.getBody();
            System.out.println(res);
        }
    }

    public static void main(String[] args) {
        testClient();
    }
}
