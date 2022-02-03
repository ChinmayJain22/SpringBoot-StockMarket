package com.example.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.CompanyNotFoundException;
import com.example.model.AuthResponse;
import com.example.model.Blocks;
import com.example.model.Company;
import com.example.model.Login;
import com.example.model.Msg;
import com.example.model.Stock;
import com.example.service.AuthenticationServiceImpl;
import com.example.service.JwtUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiModel()
@RestController
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	AuthenticationServiceImpl authenticationServiceImpl;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JwtUtil jwtUtil;
	
	boolean isValidToken=false;
	
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "UNAUTHORIZED!",response = Msg.class),
			@ApiResponse(code = 403, message = "Forbidden",response = Msg.class),
			@ApiResponse(code = 404, message = "NotFound"),
			@ApiResponse(code = 500, message = "Internal Server Error",response = Msg.class),
			@ApiResponse(code = 201, message = "Created",response = Msg.class)
			
	})
	@PostMapping("/api/v1.0/market/company/register")
	public ResponseEntity<Msg> register(@RequestBody Company CompanyCredentials) {

		return authenticationServiceImpl.register(CompanyCredentials);
	}

	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody Login loginDetails) {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getCompanycode(),loginDetails.getCompanyname()));
			
		}catch(CompanyNotFoundException e) {
			throw new CompanyNotFoundException("Company Not Found");
		}
		isValidToken=true;
		return authenticationServiceImpl.login(loginDetails);
	}
	
	@GetMapping("/api/v1.0/market/company/getall")
	public List<Company> getCompanyList()  {
		
		return authenticationServiceImpl.getCompanyList();
	
	}
	
	@GetMapping("/api/v1.0/market/company/info/{companycode}")
	public ResponseEntity<Msg> findBycompanycode(@PathVariable String companycode){
		return authenticationServiceImpl.getCompany(companycode);
	}
	
	@DeleteMapping("/api/v1.0/market/company/delete/{companycode}")
	public ResponseEntity<Msg> deleteBycompanycode(@PathVariable String companycode){
		return authenticationServiceImpl.deleteByCompanyCode(companycode);
	}
	
	@PostMapping("/api/v1.0/market/addstock")
		public String addstocks(@RequestHeader("Authorization") String token,@RequestBody Stock stock) throws ParseException {
		if(isValidToken && authenticationServiceImpl.getValidity(token)) {
			return authenticationServiceImpl.addstocks(token,stock);
		}
		return "You are not logged In.Please login and try again.";
		
	}
	
  
	
	@PostMapping("/api/v1.0/market/stock/get/{companycode}/{startdate}/{enddate}")
	public  List<Stock> getstocks(@RequestBody Blocks blocks) throws ParseException {
		
		return authenticationServiceImpl.getstocks(blocks);
		
	
	}
	
	
	
	
	

}
