package com.tenpo.challenge.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad principal del modelo
 * @author Pato
 *
 */
@Entity
public class CalculatedRecord implements Serializable{

	/**
	 * serializable
	 */
	private static final long serialVersionUID = -3824397058672247818L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDttm; //o instant LocalDateTime
	
	@Column
	@NotNull
	private int value1;
	
	@Column
	@NotNull
	private int value2;
	
	@Column
	@NotNull
	private int valueExternalService;
	
	@Column
	private float result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCreation_dttm() {
		return creationDttm;
	}

	public void setCreation_dttm(Calendar creation_dttm) {
		this.creationDttm = creation_dttm;
	}

	public int getValue_1() {
		return value1;
	}

	public void setValue_1(int value_1) {
		this.value1 = value_1;
	}

	public int getValue_2() {
		return value2;
	}

	public void setValue_2(int value_2) {
		this.value2 = value_2;
	}

	public int getValue_external_service() {
		return valueExternalService;
	}

	public void setValue_external_service(int value_external_service) {
		this.valueExternalService = value_external_service;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "CalculatedRecord [id=" + id + ", creation_dttm=" + creationDttm + ", value_1=" + value1 + ", value_2="
				+ value2 + ", value_external_service=" + valueExternalService + ", result=" + result + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationDttm, id, result, value1, value2, valueExternalService);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalculatedRecord other = (CalculatedRecord) obj;
		return Objects.equals(creationDttm, other.creationDttm) && Objects.equals(id, other.id)
				&& Float.floatToIntBits(result) == Float.floatToIntBits(other.result) && value1 == other.value1
				&& value2 == other.value2 && valueExternalService == other.valueExternalService;
	}

	public CalculatedRecord() {
		this.creationDttm = Calendar.getInstance();
	}

	public CalculatedRecord(Long id, @NotNull int value1, @NotNull int value2,
			@NotNull int valueExternalService, float result) {

		this.id = id;
		this.creationDttm = Calendar.getInstance();
		this.value1 = value1;
		this.value2 = value2;
		this.valueExternalService = valueExternalService;
		this.result = result;
	}
	
	
}
