package com.tenpo.challenge.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.challenge.model.CalculatedRecord;
import com.tenpo.challenge.model.ThirdPartyPercentage;
import com.tenpo.challenge.service.CalculatedRecordServiceImpl;
import com.tenpo.challenge.service.ThirdPartyPercentageServiceImpl;

@RestController
@RequestMapping("/api")
public class CalculatedRecordController {

	@Autowired
	private CalculatedRecordServiceImpl calculatedRecordServiceImpl;
	
	@Autowired
	private ThirdPartyPercentageServiceImpl thirdPartyPercentageServiceImpl;
	
	
	@GetMapping("/registros")
	public Page<CalculatedRecord> allRecords(@PageableDefault(size = 5, page = 0)Pageable pageable){
		return calculatedRecordServiceImpl.getAll(pageable);
	}
	
	@PostMapping("/registro")
	public CalculatedRecord createOrUpdateRecord(@RequestBody CalculatedRecord record) {
		
		ThirdPartyPercentage thirdPartyPercentage = thirdPartyPercentageServiceImpl.getThirdPartyPercentage();
		
		return calculatedRecordServiceImpl.createOrUpdateRecord(record,thirdPartyPercentage);
	}
	
	@DeleteMapping("/registro/{id}")
	public boolean deleteRecord(@PathVariable("id") Long id) {
		return calculatedRecordServiceImpl.deleteById(id);
	}
	
	@GetMapping("/registro/{id}")
	public Optional<CalculatedRecord> findById(@PathVariable("id") Long id){
		return this.calculatedRecordServiceImpl.findById(id);
	}
	
	//Su utilidad es solo como mock al servicio externo
	@GetMapping("/percentage")
	public ThirdPartyPercentage getPercentage(){
		
		ThirdPartyPercentage thirdPartyObject = new ThirdPartyPercentage();
		thirdPartyObject.setPorcentage(10);
		
		return thirdPartyObject;
	}
	
	
}
