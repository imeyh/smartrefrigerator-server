package com.porbono21PF025.smartRefrigeratorserver.entity;

import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="UserInfo") // 테이블 명
@Table(name="User_info")
public class UserInfo {
	
	@Id
	@Column(name="user_id")
	private String id;
	
	private String name;
	private String password;
	
	@OneToOne
	@JoinColumn(name="shelf_id")
	private Shelf shelf;
	
	public UserInfo(String id, String password,String name) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

}
