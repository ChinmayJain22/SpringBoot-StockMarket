package com.example.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {
	
	Optional<Stock> findBycompanycode(String companycode);
	
	@Query(value = "from Stock t where stockdate BETWEEN :startdate AND :enddate")
	public List<Stock> getAllBetweenDates(@Param("startdate")Date startdate,@Param("enddate")Date enddate);

}
