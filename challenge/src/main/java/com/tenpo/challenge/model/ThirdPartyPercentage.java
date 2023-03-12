package com.tenpo.challenge.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder
public class ThirdPartyPercentage {

	
	private int porcentage;;

	public int getPorcentage() {
		return porcentage;
	}

	public void setPorcentage(int porcentage) {
		this.porcentage = porcentage;
	}

	public ThirdPartyPercentage() {
		
	}
	
//	private Calendar timestamp;
	
}
