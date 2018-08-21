package io.github.manhnt217.spring_kafka_test;

import io.github.manhnt217.spring_kafka_test.config.Config;
import io.github.manhnt217.spring_kafka_test.model.Customer;
import io.github.manhnt217.spring_kafka_test.service.DriverRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutionException;

/**
 * @author manhnt
 */
@SpringBootApplication
public class Main {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(Config.class);
		DriverRequestService requestService = context.getBean(DriverRequestService.class);

		int numCustomer = Integer.parseInt(context.getEnvironment().getProperty("app.numCustomer"));

		for (int i = 0; i < numCustomer; i++) {
			Customer customer = new Customer("Customer#" + i, 1230000000l + i);
			new Thread(new CustomerRequest(customer, requestService)).start();
		}
	}
}
