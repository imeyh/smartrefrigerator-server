package com.porbono21PF025.smartRefrigeratorserver.entity;

import lombok.*;
import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Food")
@Table(name="Food")
public class Food {
	
	@Id
	@Column(name="food_id")
	@ApiModelProperty(example="반찬통 nfc 태그 일련 번호")
	private String id;
	
	@ManyToOne(optional = true) // 반드시 값이 필요한 경우 
	@JoinColumn(name = "shelf_id")
	private Shelf shelf_id;
	
	@ApiModelProperty(example="김치")
	private String food_name;
	@ApiModelProperty(example="200.0")
	private Float food_weight;
	@ApiModelProperty(example="1")
	private Integer food_row;
	@ApiModelProperty(example="2")
	private Integer food_col;
	
	public Food(String food_id, String food_name,Shelf shelf_id) {
		super();
		this.id = food_id;
		this.food_name = food_name;
		this.shelf_id = shelf_id;
	}
}
