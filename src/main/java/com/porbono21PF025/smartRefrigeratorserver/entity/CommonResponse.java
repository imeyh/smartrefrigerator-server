package com.porbono21PF025.smartRefrigeratorserver.entity;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CommonResponse<T> extends BasicResponse {
	@ApiModelProperty(example="1")
	private int count;
	private T data;

	public CommonResponse(T data) {
		this.data = data;
		if(data instanceof List) {
			this.count = ((List<?>)data).size();
		} else {
			this.count = 1;
		}
	}
}
