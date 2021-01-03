package com.raf.airportticketservice.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

@Configuration
public class Config {

	@Value("${activemq.broker-url}")
	public String brokerUrl;

	@Bean
	public Queue usersQueue() {
		return new ActiveMQQueue("users.queue");
	}
	
	@Bean
	public Queue ticketsQueue() {
		return new ActiveMQQueue("tickets.queue");
	}
	
	@Bean
	public Queue flightsQueue() {
		return new ActiveMQQueue("flights.queue");
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL(brokerUrl);
		return factory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(activeMQConnectionFactory());
	}

}
