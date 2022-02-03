package com.example.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Company;

public interface CompanyRepository  extends MongoRepository<Company, String> {

	Optional<Company> findBycompanycode(String companycode);
	Optional<Company> deleteBycompanycode(String companycode);
}
