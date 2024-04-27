package com.springtest.account;

import com.springtest.account.kafka.consumer.ClientDeletedConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	private ClientDeletedConsumer clientDeletedConsumer;

	@KafkaListener(topics = "client.deleted")
	public void listen(final String message) {this.clientDeletedConsumer.run(message);}

	@Autowired
	public void setClientDeletedConsumer(ClientDeletedConsumer clientDeletedConsumer) {
		this.clientDeletedConsumer = clientDeletedConsumer;
	}
}
