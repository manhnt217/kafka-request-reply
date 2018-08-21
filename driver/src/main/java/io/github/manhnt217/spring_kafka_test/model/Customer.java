package io.github.manhnt217.spring_kafka_test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author manhnt
 */
public class Customer {

	@JsonProperty
	private String name;

	@JsonProperty
	private Long phoneNumber;

	public Customer() {
	}

	public Customer(String name, Long phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}



