package com.porbono21PF025.smartRefrigeratorserver.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.porbono21PF025.smartRefrigeratorserver.dao.FoodRepository;
import com.porbono21PF025.smartRefrigeratorserver.entity.BasicResponse;
import com.porbono21PF025.smartRefrigeratorserver.entity.CommonResponse;
import com.porbono21PF025.smartRefrigeratorserver.entity.ErrorResponse;
import com.porbono21PF025.smartRefrigeratorserver.entity.Food;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // final 객체를 Constructor Injection 해줌 (Autowired 역할)
@RequestMapping("/food")
public class FoodController {
	private final FoodRepository repo;
	
	@ApiOperation(value = "반찬통 생성", notes = "신규 반찬통을 생성합니다.")
	@Transactional
	@PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> registerFood(
			@ApiParam(value = "반찬통 NFC 태그 고유 번호",required = true)
			@PathVariable("id") String id, 
			@ApiParam(value = "반찬통에 지정할 사용자 정의 이름",required = true)
			@RequestParam("food_name") String food_name,
			@ApiParam(value = "반찬통의 위치(행, 0~1)",required = true)
			@RequestParam("food_row") int food_row,
			@ApiParam(value = "반찬통의 위치(열, 0~2)",required = true)
			@RequestParam("food_col") int food_col,
			@ApiParam(value = "반찬통 등록 날짜",required = true)
			@RequestParam("registered_date") String registerd_date,
			@ApiParam(value = "반찬통이 추가될 선반의 NFC 태그 고유 번호",required = true)
			@RequestParam("shelf_id") String shelf_id) {
		
		Food food = repo.findById(id).orElse(null);
		
		if(food != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("이미 존재하는 반찬통 정보입니다.","409"));
		}
		
		int success = repo.registerFood(id,food_name,food_row,food_col,registerd_date,shelf_id); 
		
		if (success == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 선반 정보입니다."));
		}
			
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "전체 반찬통 조회", notes = "선반에 등록된 모든 반찬통의 정보를 조회합니다.")
	@GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> getFoodList(
			@ApiParam(value = "조회할 선반의 NFC 태그 고유 번호",required = true)
			@RequestParam("shelf_id") String shelf_id
			) {
		List<Food> foods = repo.getFoodList(shelf_id);
		return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<List<Food>>(foods));
	}
	
	@ApiOperation(value = "특정 위치 반찬통 조회", notes = "선반의 특정 위치에 있는 반찬통의 정보를 조회합니다.")
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> getFood(
			@ApiParam(value = "조회할 선반의 NFC 태그 고유 번호",required = true)
			@RequestParam("shelf_id") String shelf_id,
			@ApiParam(value = "조회할 행의 위치",required = true)
			@RequestParam("food_row") int food_row,
			@ApiParam(value = "조회할 열의 위치",required = true)
			@RequestParam("food_col") int food_col
			) {
		
		Food food = repo.getFood(shelf_id,food_row,food_col);
		return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<Food>(food));
		
	}
	
	@ApiOperation(value = "반찬통 영구 삭제", notes = "특정 반찬통을 삭제합니다.")
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> deleteFood(
			@ApiParam(value = "삭제할 반찬통의 NFC 태그 고유 번호", required = true)
			@PathVariable("id") String id
			) {
		
		Food food = repo.findById(id).orElse(null);
		
		if(food == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 반찬통 정보입니다."));
		}
		repo.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "반찬통 정보 갱신", notes = "특정 반찬통의 정보를 갱신합니다.")
	@PatchMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> updateFood(
			@ApiParam(value = "갱신할 반찬통의 NFC 태그 고유 번호", required = true)
			@PathVariable("id") String id,
			@ApiParam(value = "반찬통의 이름 변경", required = true)
			@RequestParam(value = "name", required = true) String name
			) {
		
		Food food = repo.findById(id).orElse(null);
		if(food == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 반찬통 정보입니다."));
		}
		food.setFood_name(name);
	
		repo.save(food);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "반찬통 조회", notes = "반찬통의 고유번호를 통해 정보를 조회합니다.")
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> getFoodById(
			@ApiParam(value = "조회할 반찬통의 NFC 태그 고유 번호", required = false)
			@PathVariable("id") String id
			) {
		Food food = repo.findById(id).orElse(null);
		
		if(food == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 반찬통 정보입니다."));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<Food>(food));
	}
	
}
