package io.github.manhnt217.spring_kafka_test.service;

import io.github.manhnt217.spring_kafka_test.model.Customer;
import io.github.manhnt217.spring_kafka_test.model.Driver;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @author manhnt
 */
@Service
public class DriverRequestService {

	@Autowired
	ReplyingKafkaTemplate<String, Customer, Driver> kafkaTemplate;

	@Value("${kafka.requestTopic}")
	private String requestTopic;

	@Value("${kafka.responseTopic}")
	private String responseTopic;

	public Driver requestDriver(Customer customer) throws ExecutionException, InterruptedException {

		ProducerRecord record = new ProducerRecord<>(requestTopic, requestTopic, customer);
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, responseTopic.getBytes()));

		RequestReplyFuture<String, Customer, Driver> response = kafkaTemplate.sendAndReceive(record);

		return response.get().value();
	}
}
