package com.exam.restservice.client;


import com.exam.restservice.client.tasks.TaskAirports;
import com.exam.restservice.client.tasks.TaskGreeting;

public class RestAppClient {
    public static void main(String[] args) {
//        TaskGreeting.testClient();
        TaskAirports.runAirports();
    }
}
