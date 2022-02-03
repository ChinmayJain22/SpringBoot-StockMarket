package com.example.model;

import javax.validation.constraints.Size;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
@Document(collection="Company")
public class Company {

@Id
@Indexed(unique=true)
private String companycode;
	
private String companyname;
	
private String companyceo;

@Size(min=100000000)
private double companyturnover;

private String companywebsite;

private double stockprice;


}
