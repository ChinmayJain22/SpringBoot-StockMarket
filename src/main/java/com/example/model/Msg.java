package com.example.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Msg {
	private int statusCode;
	  private String shortmessage;
	  private String message; 
	
	  
	  public Msg(String message,int i,String shortmessage) {
			super();
			this.message = message;
			this.statusCode=i;
			this.shortmessage= shortmessage;
		}
	  
	

	
	  
}
