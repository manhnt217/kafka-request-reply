package io.github.manhnt217.spring_kafka_test.consumer;

import io.github.manhnt217.spring_kafka_test.model.Customer;
import io.github.manhnt217.spring_kafka_test.model.Driver;
import io.github.manhnt217.spring_kafka_test.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author manhnt
 */
@Component
public class CustomerConsumer {

	@Autowired
	DriverRepository repository;

	@KafkaListener(topics = "${kafka.requestTopic}")
	@SendTo
	public Driver processRequest(Customer customer) {
		return repository.pickRandom();
	}
}
