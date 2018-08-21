package io.github.manhnt217.spring_kafka_test.config;

import io.github.manhnt217.spring_kafka_test.consumer.CustomerConsumer;
import io.github.manhnt217.spring_kafka_test.model.Customer;
import io.github.manhnt217.spring_kafka_test.model.Driver;
import io.github.manhnt217.spring_kafka_test.repository.DriverRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author manhnt
 */

@EnableKafka
@Configuration
@ComponentScan(basePackageClasses = {CustomerConsumer.class, DriverRepository.class})
public class Config {

	@Value("${kafka.brokers}")
	private String brokerList;

	@Value("${kafka.requestTopic}")
	private String requestTopic;

	@Value("${kafka.responseTopic}")
	private String responseTopic;

	@Value("${kafka.consumer.groupId}")
	private String groupId;

	@Value("${kafka.consumer.concurrent}")
	private int concurrency;

	/*----------- CONFIG FOR RECEIVING THE REQUEST (Customer object) ----------- */

//	@Bean
//	public KafkaMessageListenerContainer<String, Driver> replyContainer(ConsumerFactory<String, Driver> cf) {
//		ContainerProperties containerProperties = new ContainerProperties(responseTopic);
//		return new KafkaMessageListenerContainer<>(cf, containerProperties);
//	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Customer>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setReplyTemplate(kafkaTemplate());
		factory.setConcurrency(concurrency);
		return factory;
	}

	@Bean
	public ConsumerFactory<String, Customer> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new JsonDeserializer<>(Customer.class));
	}

	private Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				brokerList);
		props.put(
				ConsumerConfig.GROUP_ID_CONFIG,
				groupId);
		props.put(
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		props.put(
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		return props;
	}

	/*----------- CONFIG FOR THE SENDING THE RESPONSE (Driver object) ----------- */

	//Here we use raw type for Kafka template because the request and the response use 2 different kind of objects (Driver vs Customer)
	@Bean
	public KafkaTemplate kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ProducerFactory<String, Driver> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				brokerList);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}
}
