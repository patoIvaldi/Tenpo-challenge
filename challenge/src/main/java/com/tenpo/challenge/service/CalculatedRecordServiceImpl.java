package com.tenpo.challenge.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tenpo.challenge.model.CalculatedRecord;
import com.tenpo.challenge.model.ThirdPartyPercentage;
import com.tenpo.challenge.mq.consumer.Consumer;
import com.tenpo.challenge.mq.publisher.Publisher;
import com.tenpo.challenge.repository.CalculatedRecordRepository;

@Service
public class CalculatedRecordServiceImpl {

	private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());
	
	@Autowired
	CalculatedRecordRepository calculatedRecordRepository;
	
	@Autowired
	private Publisher publisher;
	
	/**
	 * Metodo que recupera todos los registros calculados.
	 * @param pageable 
	 * @return
	 */
	public Page<CalculatedRecord> getAll(Pageable pageable){
		return calculatedRecordRepository.findAll(pageable);
	}
	
	/**
	 * Metodo que realiza el calculo y la persistencia en la BD.
	 * @param record
	 * @param thirdPartyPercentage 
	 * @return
	 */
	public CalculatedRecord createOrUpdateRecord(CalculatedRecord record, ThirdPartyPercentage thirdPartyPercentage) {
		
		//invocar al servicio externo para recuperar el %
		int porcentaje = thirdPartyPercentage!=null?thirdPartyPercentage.getPorcentage():0;
		
		record.setValue_external_service(porcentaje);
		record.setResult(record.getValue_1() + record.getValue_2() +
				calcularPorcentaje(record.getValue_1() + record.getValue_2(), porcentaje));
		
		//enviaa el mensaje a rabbit
		sentToRabbit(record);
		
		return record;
	}
	
	/**
	 * Metodo que valida y realiza el calculo del porcentaje
	 * @param value
	 * @param porcentaje
	 * @return float con el resultado
	 */
	private float calcularPorcentaje(int value, int porcentaje) {
		
		//valido que la division se pueda realizar
		return value==0||porcentaje==0?0:(value * porcentaje) / (float)100;
	}

	/**
	 * Metodo que borra el registro segun el ID.
	 * @param id
	 * @return
	 */
	public boolean deleteById(Long id) {
		try {
			calculatedRecordRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Metodo que busca un registro por id.
	 * @param id
	 * @return
	 */
	public Optional<CalculatedRecord> findById(Long id){
		return calculatedRecordRepository.findById(id);
	}
	
	private void sentToRabbit(CalculatedRecord message) {
		
		LOGGER.info("Mensaje { " + message.toString() + " } sera enviado...");
		this.publisher.send(message);
	}
	
}
