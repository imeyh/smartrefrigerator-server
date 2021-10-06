package com.porbono21PF025.smartRefrigeratorserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Food")
@Table(name="Food")
public class Food {
	
	@Id
	@Column(name="food_id")
	private String id;
	
	@ManyToOne(optional = true) // 반드시 값이 필요한 경우 
	@JoinColumn(name = "shelf_id")
	private Shelf shelf_id;
	
	private String food_name;
	private Float food_weight;
	private Float max_weight;
	private Integer food_row;
	private Integer food_col;
	private String registered_date;
	
	
	public Food(String food_id, String food_name,String registered_date,Shelf shelf_id) {
		super();
		this.id = food_id;
		this.food_name = food_name;
		this.shelf_id = shelf_id;
		this.registered_date = registered_date;
	}
}
