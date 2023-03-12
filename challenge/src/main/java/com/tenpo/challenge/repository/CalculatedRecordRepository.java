package com.tenpo.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tenpo.challenge.model.CalculatedRecord;

@Repository
public interface CalculatedRecordRepository extends CrudRepository<CalculatedRecord, Long>,JpaRepository<CalculatedRecord, Long>{

	
	//public Set<CalculatedRecord> findByCreationDttm(Timestamp timestamp);
	
}
