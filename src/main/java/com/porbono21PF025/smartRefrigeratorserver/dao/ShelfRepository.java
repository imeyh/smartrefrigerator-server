package com.porbono21PF025.smartRefrigeratorserver.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.porbono21PF025.smartRefrigeratorserver.entity.Shelf;

@Repository
public interface ShelfRepository extends CrudRepository<Shelf,String> {
	
	@Modifying
	@Query(value="UPDATE Food Set food_row = NULL, food_col = NULL WHERE shelf_id = ?1 and food_row = ?2 and food_col = ?3", nativeQuery = true)
	int removeFoodAt(String id, int row, int col);
	
	@Modifying
	@Query(value="UPDATE Food Set food_row = ?3, food_col = ?4, food_weight = ?5, max_weight =?6 WHERE shelf_id = ?1 and food_id = ?2", nativeQuery = true)
	int addFoodAt(String id, String food_id, int row, int col, float weight,float max_weight);
	
	
}
