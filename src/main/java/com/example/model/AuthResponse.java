package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {

	private int statusCode;
	  private String shortmessage;
	  private String message; 
	  private String token;
	  
	  public AuthResponse(String message,int i,String shortmessage,String token) {
		  this.message = message;
			this.statusCode=i;
			this.shortmessage= shortmessage;
			 this.token = token;
			
		  
	  }
}
