package com.porbono21PF025.smartRefrigeratorserver.entity;

import lombok.*;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="UserInfo") // 테이블 명
@Table(name="User_info")
public class UserInfo {
	
	@Id
	@Column(name="user_id")
	@ApiModelProperty(example="jangbisu")
	private String id;
	
	@ApiModelProperty(example="장비서")
	private String name;
	@ApiModelProperty(example="123456")
	private String password;
	
	@OneToOne
	@JoinColumn(name="shelf_id")
	@ApiModelProperty(example="연결된 선반 nfc 태그 일련번호")
	private Shelf shelf;
	
	public UserInfo(String id, String password,String name) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

}
