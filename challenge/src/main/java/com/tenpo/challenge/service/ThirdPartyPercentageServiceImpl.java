package com.tenpo.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tenpo.challenge.model.ThirdPartyPercentage;

/**
 * 
 * @author Pato
 * Servicio para realizar el llamado al servicio externo
 */
@Service
public class ThirdPartyPercentageServiceImpl {
	
	@Autowired
	RestTemplate restTemplate;
	
	/**
	 * Metodo que hace el llamado al servicio externo y mapea el body con el modelo.
	 * @return body con la respuesta  sino null
	 */
	public ThirdPartyPercentage getThirdPartyPercentage() {
		
		ResponseEntity<ThirdPartyPercentage> resp = new ResponseEntity<ThirdPartyPercentage>(HttpStatus.SERVICE_UNAVAILABLE);
		
		try {
			resp = restTemplate.getForEntity(
					"http://localhost:8080/api/percentage", ThirdPartyPercentage.class);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp.getStatusCode() == HttpStatus.OK? resp.getBody():null;
	}

}
