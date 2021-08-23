package com.porbono21PF025.smartRefrigeratorserver.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.porbono21PF025.smartRefrigeratorserver.dao.FoodRepository;
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
	public int registerFood(
			@ApiParam(value = "반찬통 NFC 태그 고유 번호",required = true)
			@PathVariable("id") String id, 
			@ApiParam(value = "반찬통에 지정할 사용자 정의 이름",required = true)
			@RequestParam("food_name") String food_name,
			@ApiParam(value = "반찬통이 추가될 선반의 NFC 태그 고유 번호",required = true)
			@RequestParam("shelf_id") String shelf_id) {

		return repo.registerFood(id,food_name,shelf_id);
	}
	
	@ApiOperation(value = "전체 반찬통 조회", notes = "선반에 등록된 모든 반찬통의 정보를 조회합니다.")
	@GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Food> getFoodList(
			@ApiParam(value = "조회할 선반의 NFC 태그 고유 번호",required = true)
			@RequestParam("shelf_id") String shelf_id
			) {
		return repo.getFoodList(shelf_id);
	}
	
	@ApiOperation(value = "특정 위치 반찬통 조회", notes = "선반의 특정 위치에 있는 반찬통의 정보를 조회합니다.")
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public Food getFood(
			@ApiParam(value = "조회할 선반의 NFC 태그 고유 번호",required = true)
			@RequestParam("shelf_id") String shelf_id,
			@ApiParam(value = "조회할 행의 위치",required = true)
			@RequestParam("food_row") String food_row,
			@ApiParam(value = "조회할 열의 위치",required = true)
			@RequestParam("food_col") String food_col
			) {
		return repo.getFood(shelf_id,food_row,food_col);
	}
	
	@ApiOperation(value = "반찬통 영구 삭제", notes = "특정 반찬통을 삭제합니다.")
	@DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteFood(
			@ApiParam(value = "삭제할 반찬통의 NFC 태그 고유 번호", required = true)
			@PathVariable("id") String id
			) {
		repo.deleteById(id);
	}
	
	@ApiOperation(value = "반찬통 정보 갱신", notes = "특정 반찬통의 정보를 갱신합니다.")
	@PatchMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateFood(
			@ApiParam(value = "갱신할 반찬통의 NFC 태그 고유 번호", required = true)
			@PathVariable("id") String id,
			@ApiParam(value = "반찬통의 이름 변경", required = false)
			@RequestParam(value = "name", required = false) String name,
			@ApiParam(value = "반찬통이 속한 선반의 고유 번호 변경 (권장 않음)", required = false)
			@RequestParam(value = "shelf_id",required = false) String shelf_id,
			@ApiParam(value = "반찬통이 속한 행의 위치 변경 (권장 않음)", required = false)
			@RequestParam(value = "row",required = false) int row,
			@ApiParam(value = "반찬통이 속한 열의 위치 변경 (권장 않음)", required = false)
			@RequestParam(value = "col",required = false) int col,
			@ApiParam(value = "반찬통의 무게 변경 (권장 않음)", required = false)
			@RequestParam(value = "weight",required = false) float weight
			) {
		repo.deleteById(id);
	}
	
	@ApiOperation(value = "반찬통 조회", notes = "반찬통의 고유번호를 통해 정보를 조회합니다.")
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Food getFoodById(
			@ApiParam(value = "조회할 반찬통의 NFC 태그 고유 번호", required = false)
			@PathVariable("id") String id
			) {
		return repo.findById(id).orElse(null);
	}
	
}
