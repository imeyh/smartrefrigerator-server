package com.porbono21PF025.smartRefrigeratorserver.controller;

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

import com.porbono21PF025.smartRefrigeratorserver.dao.ShelfRepository;
import com.porbono21PF025.smartRefrigeratorserver.entity.Shelf;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor // final 객체를 Constructor Injection 해줌 (Autowired 역할)
@RequestMapping("/shelf")
public class ShelfController {
	
	private final ShelfRepository repo;
	
	@ApiOperation(value = "선반 생성", notes = "신규 선반을 생성합니다.")
	@PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Shelf postShelf(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id,
			@ApiParam(value = "선반의 세로 길이", required = true)
			@RequestParam int row,
			@ApiParam(value = "선반의 가로 길이", required = true)
			@RequestParam int col
		) {
		return repo.save(new Shelf(id,row,col));
	}
	
	@ApiOperation(value = "선반 삭제", notes = "선반을 삭제합니다.")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void removeShelf(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id
			) {
		repo.deleteById(id);
	}
	
	
	
	@ApiOperation(value = "선반 조회", notes = "shelf_id를 이용하여 선반을 조회합니다.")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Shelf findShelfById(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id
		) {
		return repo.findById(id).orElse(null);
	}
	
	@ApiOperation(value = "특정 위치의 음식 정보 제거", notes = "row, col을 전달하여 해당 위치의 음식 정보를 제거합니다.")
	@Transactional
	@PatchMapping(value = "/remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public int removeFoodAt(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id, 
			@ApiParam(value = "선반의 행 위치 지정",required = true)
			@RequestParam int row, 
			@ApiParam(value = "선반의 열 위치 지정",required = true)
			@RequestParam int col) {
		return repo.removeFoodAt(id,row,col);
	}
	
	@ApiOperation(value = "특정 위치의 음식 정보 갱신", notes = "row, col을 전달하여 해당 위치의 음식 정보를 갱신합니다.")
	@Transactional
	@PatchMapping(value = "update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public int addFoodAt(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id,
			@ApiParam(value = "새롭게 갱신될 반찬통의 NFC 태그 고유 번호",required = true)
			@RequestParam String food_id ,
			@ApiParam(value = "선반의 행 위치 지정",required = true)
			@RequestParam int row,
			@ApiParam(value = "선반의 열 위치 지정",required = true)
			@RequestParam int col,
			@ApiParam(value = "새롭게 갱신 반찬통의 무게",required = true)
			@RequestParam float weight
			) {
		return repo.addFoodAt(id,food_id,row,col,weight);
	}
}
