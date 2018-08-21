# kafka-request-reply

There are 2 projects: Customer and Driver

## Project Customer
	* Create driver-request for each customer.
	* Print out the result (If a driver found or not).
	* Number of customers is configurable.

## Project Driver
	* Process the driver-request sent from customer.
	* Pick an available Driver _randomly_ from Driver Pool and return to customer, or NO_ONE if the pool is empty.
	* Number of drivers in pool is configurable.

