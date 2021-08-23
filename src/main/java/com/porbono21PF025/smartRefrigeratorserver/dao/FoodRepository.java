package com.porbono21PF025.smartRefrigeratorserver.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.porbono21PF025.smartRefrigeratorserver.entity.Food;

public interface FoodRepository extends CrudRepository<Food,String>{
	@Modifying
	@Query(value="INSERT INTO FOOD (food_id,food_name,shelf_id) VALUES (?1,?2,?3)",nativeQuery = true)
	int registerFood(String id,String name,String shelf_id);
	
	@Query(value="SELECT * FROM FOOD WHERE shelf_id = ?1", nativeQuery = true)
	List<Food> getFoodList(String shelf_id);
	 
	@Query(value="SELECT * FROM FOOD WHERE shelf_id = ?1 AND food_row = ?2 and food_col = ?3",nativeQuery = true)
	Food getFood(String shelf_id,String food_row,String food_col);
	
}
