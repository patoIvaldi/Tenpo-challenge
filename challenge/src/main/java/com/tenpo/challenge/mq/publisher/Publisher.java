package com.tenpo.challenge.mq.publisher;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Publicador de mensajes en rabbit.
 * @author Pato
 *
 */
@Component
@EnableRabbit
public class Publisher {

	@Autowired
	private RabbitTemplate rabbitTemplate = new RabbitTemplate();
	
	@Autowired
	private Queue queue;
	
	/**
	 * Metodo que se encarga de hacer el envio del mensaje a la cola definida en Rabbit.
	 * @param message
	 */
	public void send(Object message) {
		rabbitTemplate.convertAndSend(queue.getName(),message);
	}
	
}
