package io.github.manhnt217.spring_kafka_test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author manhnt
 */
public class Driver {

	@JsonProperty
	private Integer number;
	@JsonProperty
	private String name;

	public Driver() {
	}

	public Driver(Integer number, String name) {
		this.number = number;
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
