package com.porbono21PF025.smartRefrigeratorserver.entity;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse extends BasicResponse {

	private String errorMessage;
	private String errorCode;

	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = "404";
	}
	public ErrorResponse(String errorMessage, String errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	
}
