package com.tenpo.challenge.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	 * Metodo que recupera el ultimo porcentaje que vino en el servicio externo.
	 * @return ultimo porcentaje utilizado
	 * @throws Exception en caso que no haya registros en la BD.
	 */
	private int getLastPercentage() throws Exception{
		
		List<CalculatedRecord> registros = calculatedRecordRepository.findAll(
				Sort.by(Direction.DESC, "creationDttm"));
		
		if(registros.isEmpty()) {
			throw new Exception("No hay ningún valor configurado para el porcentaje en la BD.");
		}
		
		return registros.get(0).getValue_external_service();
	}
	
	/**
	 * Metodo que realiza el calculo y la persistencia en la BD.
	 * @param record
	 * @param thirdPartyPercentage 
	 * @return
	 * @throws Exception 
	 */
	public CalculatedRecord createOrUpdateRecord(CalculatedRecord record, ThirdPartyPercentage thirdPartyPercentage) throws Exception {
		
		//invocar al servicio externo para recuperar el %,
		//en caso que no lo encuentre, recupera el ultimo
		int porcentaje = thirdPartyPercentage!=null?thirdPartyPercentage.getPorcentage():getLastPercentage();
		
		record.setValue_external_service(porcentaje);
		record.setResult(record.getValue_1() + record.getValue_2() +
				calcularPorcentaje(record.getValue_1() + record.getValue_2(), porcentaje));
		
		//envia el mensaje a rabbit
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

		if(!findById(id).isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		calculatedRecordRepository.deleteById(id);
		return true;
	}
	
	/**
	 * Metodo que busca un registro por id.
	 * @param id
	 * @return
	 */
	public Optional<CalculatedRecord> findById(Long id){
		
		Optional<CalculatedRecord> record = calculatedRecordRepository.findById(id);
		
		if(!record.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return record;
	}
	
	/**
	 * Envia el mensaje a rabbit.
	 * @param message
	 */
	private void sentToRabbit(CalculatedRecord message) {
		
		LOGGER.info("Mensaje { " + message.toString() + " } sera enviado...");
		this.publisher.send(message);
	}
	
}
