package com.tenpo.challenge.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tenpo.challenge.model.CalculatedRecord;
import com.tenpo.challenge.repository.CalculatedRecordRepository;

@Service
public class CalculatedRecordServiceImpl {

	@Autowired
	CalculatedRecordRepository calculatedRecordRepository;
	
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
	 * @return
	 */
	public CalculatedRecord createOrUpdateRecord(CalculatedRecord record) {
		
		//invocar al servicio externo para recuperar el %
		int porcentaje = 10;
		
		record.setResult(record.getValue_1() + record.getValue_2() +
				calcularPorcentaje(record.getValue_1() + record.getValue_2(), porcentaje));
		
		return calculatedRecordRepository.save(record);
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
}
