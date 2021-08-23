package com.porbono21PF025.smartRefrigeratorserver.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.porbono21PF025.smartRefrigeratorserver.entity.UserInfo;


public interface UserRepository extends CrudRepository<UserInfo,String> {
	
	UserInfo findByIdAndPassword(String id, String password);
	
	@Modifying
	@Query(value="UPDATE User_info SET shelf_id = ?2 WHERE user_id = ?1",nativeQuery = true)
	int connectToShelf(String id,String shelf_id);
}
