package com.porbono21PF025.smartRefrigeratorserver.entity;

import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Shelf") // 테이블 명
public class Shelf {
	
	@Id 
	@Column(name="shelf_id")
	private String id;
	
	@Column(name="shelf_row")
	private int row;
	@Column(name="shelf_col")
	private int col;

}
