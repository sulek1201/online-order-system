package com.sulek.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApplication {

	private final static Logger logger = LogManager.getLogger(OrderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
		logger.info("Application Start Running...");
		logger.debug("Application Start Running...");
		logger.error("Application Start Running...");
		logger.warn("Application Start Running...");
	}

}
