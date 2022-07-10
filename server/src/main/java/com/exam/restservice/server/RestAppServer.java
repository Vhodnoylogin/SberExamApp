package com.exam.restservice.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestAppServer {
	protected static final Logger logger = LogManager.getLogger(RestAppServer.class);

	public static void main(String[] args) {
		SpringApplication.run(RestAppServer.class, args);
	}

}
