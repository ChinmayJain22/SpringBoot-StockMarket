package com.example.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.model.Login;
import com.example.model.AuthResponse;
import com.example.model.Blocks;
import com.example.model.Company;
import com.example.model.Msg;
import com.example.model.Stock;



public interface AuthenticationService  extends UserDetailsService{

	public ResponseEntity<Msg> register(Company CompanyCredentials);
	public ResponseEntity<AuthResponse> login(Login userCredentials);
	public List<Company> getCompanyList();
	public ResponseEntity<Msg> getCompany(String companycode);
	
	public ResponseEntity<Msg> deleteByCompanyCode(String companycode);
	public List<Stock> getstocks(Blocks blocks) throws ParseException;
	public String addstocks(String token,Stock stock) throws ParseException;
	
}
