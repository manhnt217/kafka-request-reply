package io.github.manhnt217.spring_kafka_test.repository;

import io.github.manhnt217.spring_kafka_test.model.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * @author manhnt
 */
@Component
public class DriverRepository {

	@Value("${app.numDriver}")
	private int numDriver;

	private ArrayList<Driver> driverPool;

	@PostConstruct
	public void init() {

		driverPool = new ArrayList<>();

		for (int i = 1; i <= numDriver; i++) {
			driverPool.add(new Driver(i, "Driver#" + i));
		}
	}

	public Driver pickRandom() {

		// A bit dirty when using double-checked locking
		if (driverPool.size() == 0) {
			return Driver.NO_ONE;
		}

		synchronized (driverPool) {
			if (driverPool.size() == 0) {
				return Driver.NO_ONE;
			}
			return driverPool.remove((int) Math.random() * driverPool.size());
		}
	}
}
