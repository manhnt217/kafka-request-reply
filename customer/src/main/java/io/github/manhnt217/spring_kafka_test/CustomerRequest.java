package io.github.manhnt217.spring_kafka_test;

import io.github.manhnt217.spring_kafka_test.model.Customer;
import io.github.manhnt217.spring_kafka_test.model.Driver;
import io.github.manhnt217.spring_kafka_test.service.DriverRequestService;

import java.util.concurrent.ExecutionException;

/**
 * @author manhnt
 */
public class CustomerRequest implements Runnable {

	private DriverRequestService requestService;
	private Customer customer;

	public CustomerRequest(Customer customer, DriverRequestService requestService) {
		this.customer = customer;
		this.requestService = requestService;
	}

	@Override
	public void run() {
		try {
			System.out.printf("Customer %s is requesting for driver...\n", customer.getName());
			Driver driver = requestService.requestDriver(customer);
			if (driver.getNumber() == null || driver.getNumber() <= 0) {
				System.out.printf("No driver found for customer %s\n", customer.getName());
			} else {
				System.out.printf("Found driver #%d for customer %s\n", driver.getNumber(), customer.getName());
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
