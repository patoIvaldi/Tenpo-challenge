package com.tenpo.challenge.mq.publisher;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Pato
 *
 */
@Configuration
public class PublisherConfig {

	@Value("${sacavix.queue.name}")
	private String message;
	
	@Bean
	public Queue queue() {
		return new Queue(message, true);
	}
	
}
