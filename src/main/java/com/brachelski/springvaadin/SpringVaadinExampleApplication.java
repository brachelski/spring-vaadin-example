package com.brachelski.springvaadin;

import com.brachelski.springvaadin.models.Customer;
import com.brachelski.springvaadin.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringVaadinExampleApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringVaadinExampleApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringVaadinExampleApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository repository) {
		return (args) -> {
			for (int i = 0; i <= 1000; i++) {
				repository.save(new Customer(
						"Customer First Name " + Integer.toString(i),
						"Customer Last Name " + Integer.toString(i))
				);
			}
			repository.save(new Customer("Bill", "Brasky"));
			repository.save(new Customer("Bob", "Ross"));
			repository.save(new Customer("John", "Wick"));
			repository.save(new Customer("HeyTHere", "Buddy"));
			repository.save(new Customer("Walter", "White"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findById(1L).get();
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastNameStartsWithIgnoreCase('Brasky'):");
			log.info("--------------------------------------------");
			for (Customer brasky : repository.findByLastNameStartsWithIgnoreCase("Brasky")) {
				log.info(brasky.toString());
			}
			log.info("");
		};
	}
}
