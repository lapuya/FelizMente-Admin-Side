package es.ite.felizmente.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Address {
	private String street;
	private String number;
	private String region;
	private String zipCode;
	private String country;

}
