package com.tenpo.challenge.mq.consumer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.tenpo.challenge.model.CalculatedRecord;
import com.tenpo.challenge.repository.CalculatedRecordRepository;

@Component
public class Consumer {

	private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());
	
	@Autowired
	private CalculatedRecordRepository calculatedRecordRepository;
	
	/**
	 * Metodo que recibe el objeto desde la cola de Rabbit y
	 * realiza la persistencia en la BD luego de un tiempo.
	 * @param message
	 */
	@RabbitListener(queues = {"${sacavix.queue.name}"})
	public void receive(@Payload Object message) {
		
		//deserializamos el objeto que nos viene desde RabbitMQ.
		CalculatedRecord record = deserializador(message);

		makeSlow();
		
		LOGGER.info("Mensaje recibido { "+ record.toString() + " }");
		
		//persistimos en la BD
		calculatedRecordRepository.save(record);
		
		LOGGER.info("Persistido en la BD { "+ record.toString() + " }");
	}

	/**
	 * Metodo que se encarga de convertir el objeto que nos llega desde Rabbit
	 * al objeto esperado del modelo.
	 * @param message
	 * @return CalculatedRecord
	 */
	private CalculatedRecord deserializador(Object message) {
		
	    ByteArrayInputStream byteStream = new ByteArrayInputStream(((Message)message).getBody());
	    ObjectInputStream objectStream = null;
	    
		try {
			objectStream = new ObjectInputStream(byteStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	    Object object = null;
	    
		try {
			object = objectStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (CalculatedRecord)object;
	}
	
	/**
	 * Metodo solamente para aplicar un delay a la cola de Rabbit.
	 */
	private void makeSlow() {
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
