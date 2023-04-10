package com.sulek.seller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SellerApplication {

	private final static Logger logger = LogManager.getLogger(SellerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SellerApplication.class, args);
		logger.info("Application Start Running...");
		logger.debug("Application Start Running...");
		logger.error("Application Start Running...");
		logger.warn("Application Start Running...");
	}

}
