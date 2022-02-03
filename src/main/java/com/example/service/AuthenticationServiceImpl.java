package com.example.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.model.AuthResponse;
import com.example.model.Blocks;
import com.example.model.Login;
import com.example.exception.CompanyNotFoundException;
import com.example.model.Company;
import com.example.model.Msg;
import com.example.model.Stock;
import com.example.repository.CompanyRepository;
import com.example.repository.StockRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
@Autowired
CompanyRepository companyrepo;


@Autowired
StockRepository stockrepo;


@Autowired
JwtUtil jwtUtil;


public UserDetails loadUserByUsername(String companycode) throws CompanyNotFoundException {
	Optional<Company> companyData=companyrepo.findBycompanycode(companycode);
	if(companyData.isEmpty()) {
		throw new CompanyNotFoundException("Company Not Found");
	}
	Company company=companyData.get();
	ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
	return new User(company.getCompanycode(), company.getCompanyname(), list);
	
}


@Override
public ResponseEntity<Msg> register(Company CompanyCredentials) {
	companyrepo.save(CompanyCredentials);
	return ResponseEntity.ok().body(new Msg("customer registered successfully", 0000, "success"));
			
}

public boolean getValidity(String token) {
	// TODO Auto-generated method stub
	return jwtUtil.validateToken(token);
}

@Override
public ResponseEntity<AuthResponse> login(Login loginDetails) {
	UserDetails customer=loadUserByUsername(loginDetails.getCompanycode());
	String token=jwtUtil.generateToken(customer);
	AuthResponse res=new AuthResponse();
	res.setToken(token);
	
	return ResponseEntity.ok().body(new AuthResponse("customer login successfully", 0000, "success", token));
	
}


@Override
public List<Company> getCompanyList() {
	List<Company> getcompanylist = new ArrayList<>();
	companyrepo.findAll().forEach(getcompanylist:: add);
	return getcompanylist;
}


@Override
public ResponseEntity<Msg> getCompany(String companycode) {
	Optional<Company> company = companyrepo.findBycompanycode(companycode);
	if(company.isPresent()) {
		return ResponseEntity.ok().body(new Msg("Company Present in the list", 0000, "success"));
	}else {
		return ResponseEntity.notFound().build();

	}
	
}


@Override
public ResponseEntity<Msg> deleteByCompanyCode(String companycode) {
	try {
		companyrepo.deleteBycompanycode(companycode);
		return  ResponseEntity.ok().body(new Msg("Company deleted from the list", 0000, "success"));
	}catch(Exception e) {
		return ResponseEntity.ok().body(new Msg("Internal Server error", 500, "Failure"));
	}
}



@Override
public String addstocks(String token, Stock stock) throws ParseException {
	// TODO Auto-generated method stub
	String companycode=jwtUtil.extractUsername(token);
	Optional<Company> company=companyrepo.findBycompanycode(companycode);
	if(!company.isEmpty()) {
		company.get().setStockprice(stock.getStockprice());
		companyrepo.deleteBycompanycode(companycode);
		
		companyrepo.save(company.get());
		
		stockrepo.save(stock);
		return "Stock added Successfully";
	}
	
	return "Cannot process now.Try again later";
	
}


@Override
public List<Stock> getstocks(Blocks blocks) {

	
	ArrayList<Stock> stock = (ArrayList<Stock>) stockrepo.findAll();
	List<Stock> list = new ArrayList<Stock>();
	
	String sdate1 = blocks.getStartdate().replaceAll("-", "");
	Long strdate = Long.parseLong(sdate1);
	
	String edate1 = blocks.getEnddate().replaceAll("-", "");
	Long endate = Long.parseLong(edate1);
	System.out.println(strdate);
	System.out.println(endate);
	if(stock!=null) {
		
		stock.forEach(temp ->{
			String date1 = temp.getStockdate().replaceAll("-", "");
			Long wdate = Long.parseLong(date1);
			System.out.println(date1);
			System.out.println(temp.getStockdate());
			System.out.println(temp.getCompanycode());
			
			if(temp.getCompanycode().equals(blocks.getCompanycode()) && wdate >= strdate && wdate <= endate ) {
				list.add(temp);
				
			}
		});	
	}
	
//	java.util.Date utilstartDate = new java.util.Date(blocks.getStartdate().getTime());
//	java.util.Date utilendDate = new java.util.Date(blocks.getEnddate().getTime());
	return list;
	
	
	

}


}
