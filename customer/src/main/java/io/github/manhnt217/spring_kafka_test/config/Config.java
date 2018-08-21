package io.github.manhnt217.spring_kafka_test.config;

import io.github.manhnt217.spring_kafka_test.model.Customer;
import io.github.manhnt217.spring_kafka_test.model.Driver;
import io.github.manhnt217.spring_kafka_test.service.DriverRequestService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author manhnt
 */

@EnableKafka
@Configuration
@ComponentScan(basePackageClasses = DriverRequestService.class)
public class Config {

	@Value("${kafka.brokers}")
	private String brokerList;

	@Value("${kafka.requestTopic}")
	private String requestTopic;

	@Value("${kafka.responseTopic}")
	private String responseTopic;

	@Value("${kafka.consumer.groupId}")
	private String groupId;

	/*----------- CONFIG FOR SENDING THE REQUEST (Customer object) ----------- */
	@Bean
	public ReplyingKafkaTemplate<String, Customer, Driver> replyKafkaTemplate(ProducerFactory<String, Customer> pf, KafkaMessageListenerContainer<String, Driver> container) {
		return new ReplyingKafkaTemplate<>(pf, container);
	}

	@Bean
	public ProducerFactory<String, Customer> producerFactory() {
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

	/*----------- CONFIG FOR RECEIVING THE RESPONSE (Driver object) ----------- */
	@Bean
	public KafkaMessageListenerContainer<String, Driver> replyContainer(ConsumerFactory<String, Driver> cf) {
		ContainerProperties containerProperties = new ContainerProperties(responseTopic);
		return new KafkaMessageListenerContainer<>(cf, containerProperties);
	}

	@Bean
	public ConsumerFactory<String, Driver> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new JsonDeserializer<>(Driver.class));
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

}
