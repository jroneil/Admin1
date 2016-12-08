package com.admin1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.admin1.purchaseorder.OrderStatus;
import com.admin1.purchaseorder.PurchaseOrder;
import com.admin1.purchaseorder.PurchaseOrderRepository;

@ComponentScan(basePackages = { "com.admin1" })
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(PurchaseOrderRepository repository) {
		return (args) -> {
			// save a couple of purchaseOrders
			repository.save( new PurchaseOrder("JoeO",1L,123L,OrderStatus.COMPLETE));
			repository.save(new PurchaseOrder("FredS",3L,123L,OrderStatus.COMPLETE));
			repository.save(new PurchaseOrder("JoeO",4L,456L,OrderStatus.COMPLETE));
			repository.save(new PurchaseOrder("SamX",3L,789l,OrderStatus.COMPLETE));
			repository.save(new PurchaseOrder("JulesO",1L,325L,OrderStatus.COMPLETE));
			// fetch all purchaseOrders
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (PurchaseOrder purchaseOrder : repository.findAll()) {
				log.info(purchaseOrder.toString());
			}
			log.info("");

			// fetch an individual purchaseOrder by ID
			PurchaseOrder purchaseOrder = repository.findOne(1L);
			log.info("PurchaseOrder found with findOne(1L):");
			log.info("--------------------------------");
			log.info(purchaseOrder.toString());
			log.info("");

			// fetch purchaseOrders by last name
			log.info("PurchaseOrder found with findByUserName('JoeO'):");
			log.info("--------------------------------------------");
			for (PurchaseOrder JoeO : repository.findByUserName("JoeO")) {
				log.info(JoeO.toString());
			}
			log.info("");
		};
	}
	

}
