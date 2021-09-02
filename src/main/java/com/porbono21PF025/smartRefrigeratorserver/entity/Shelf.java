package com.porbono21PF025.smartRefrigeratorserver.entity;

import lombok.*;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Shelf") // 테이블 명
@Table(name="Shelf")
public class Shelf {
	
	@Id 
	@Column(name="shelf_id")
	@ApiModelProperty(example="선반 nfc 태그 일련번호")
	private String id;
	
	@Column(name="shelf_row")
	@ApiModelProperty(example="2")
	private int row;
	@Column(name="shelf_col")
	@ApiModelProperty(example="3")
	private int col;

}
