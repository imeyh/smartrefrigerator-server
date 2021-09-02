package com.porbono21PF025.smartRefrigeratorserver.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse extends BasicResponse {
	@ApiModelProperty(example="존재하지 않는 정보입니다.")
	private String errorMessage;
	@ApiModelProperty(example="404")
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
