package com.example.model;


import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityScan
@Document(collection="Stock")
public class Stock {
	
	
	 public String companycode;
	
	public double stockprice;
	
	public String stockdate;
	
	public double min;
	
	public double max;
	
	

}
