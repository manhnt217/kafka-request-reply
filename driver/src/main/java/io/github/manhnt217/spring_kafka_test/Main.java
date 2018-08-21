package io.github.manhnt217.spring_kafka_test;

import io.github.manhnt217.spring_kafka_test.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

/**
 * @author manhnt
 */
@SpringBootApplication
public class Main {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		SpringApplication.run(Config.class);
	}
}
