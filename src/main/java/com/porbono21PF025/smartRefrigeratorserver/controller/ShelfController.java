package com.porbono21PF025.smartRefrigeratorserver.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.porbono21PF025.smartRefrigeratorserver.dao.ShelfRepository;
import com.porbono21PF025.smartRefrigeratorserver.entity.BasicResponse;
import com.porbono21PF025.smartRefrigeratorserver.entity.CommonResponse;
import com.porbono21PF025.smartRefrigeratorserver.entity.ErrorResponse;
import com.porbono21PF025.smartRefrigeratorserver.entity.Food;
import com.porbono21PF025.smartRefrigeratorserver.entity.Shelf;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor // final 객체를 Constructor Injection 해줌 (Autowired 역할)
@RequestMapping("/shelf")
public class ShelfController {
	
	private final ShelfRepository repo;
	private final FoodRepository foodRepo;
	
	@ApiOperation(value = "선반 생성", notes = "신규 선반을 생성합니다.")
	@PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> postShelf(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id,
			@ApiParam(value = "선반의 세로 길이", required = true)
			@RequestParam int row,
			@ApiParam(value = "선반의 가로 길이", required = true)
			@RequestParam int col
		) {
		
		Shelf shelf = repo.findById(id).orElse(null);
		// 선반 이미 존재  
		if(shelf!=null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("이미 존재하는 정보입니다.","409"));
		}
		
		shelf = repo.save(new Shelf(id,row,col)); 
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<Shelf>(shelf));
	}
	
	@ApiOperation(value = "선반 삭제", notes = "선반을 삭제합니다.")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> removeShelf(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id
			) {
		Shelf shelf = repo.findById(id).orElse(null);
		// 선반이 디비에 없음   
		if(shelf==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 정보입니다."));
		}
		
		
		repo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	@ApiOperation(value = "선반 조회", notes = "shelf_id를 이용하여 선반을 조회합니다.")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> findShelfById(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id
		) {
		
		// 선반이 디비에 존재안함   
		Shelf shelf = repo.findById(id).orElse(null);
		if(shelf==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 정보입니다."));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<Shelf>(shelf));
	}
	
	@ApiOperation(value = "얼음 정보 수정", notes = "shelf_id를 이용하여 얼음 정보를 수정합니다.")
	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> findIce(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id,
			@ApiParam(value = "얼음 정보", required = true)
			@RequestParam boolean ice
		) {
		
		// 선반이 디비에 존재안함   
		Shelf shelf = repo.findById(id).orElse(null);
		if(shelf==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 선반 정보입니다."));
		}
		
		shelf.setIce(ice);
		repo.save(shelf);
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "특정 위치의 음식 정보 제거", notes = "row, col을 전달하여 해당 위치의 음식 정보를 제거합니다.")
	@Transactional
	@PatchMapping(value = "/remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> removeFoodAt(
			@ApiParam(value = "선반 NFC 태그 고유 번호",required = true)
			@PathVariable String id, 
			@ApiParam(value = "선반의 행 위치 지정",required = true)
			@RequestParam int row, 
			@ApiParam(value = "선반의 열 위치 지정",required = true)
			@RequestParam int col) {
		// 선반이 디비에 존재안함   
		Shelf shelf = repo.findById(id).orElse(null);
		if(shelf==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 선반 정보입니다."));
		}
		
		int success = repo.removeFoodAt(id, row, col);
		
		if (success == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("해당 위치에 음식이 존재하지 않습니다."));
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "특정 위치의 음식 정보 갱신", notes = "row, col을 전달하여 해당 위치의 음식 정보를 갱신합니다.")
	@Transactional
	@PatchMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BasicResponse> addFoodAt(
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
		
		Shelf shelf = repo.findById(id).orElse(null);
		if(shelf==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("존재하지 않는 선반 정보입니다."));
		}
		
		Food food = foodRepo.getFood(id, row, col);
		
		// 이미 해당 위치에 반찬통이 존재하는 경우
		if(food != null) {
			repo.removeFoodAt(id, row, col);
		}
		
		// 새로운 반찬 정보가 등록된 정보인지 확인
		Food new_food = foodRepo.findById(food_id).orElse(null);
		float max_weight = 0.0f;
		if (new_food == null) {
			SimpleDateFormat format = new SimpleDateFormat ( "yyyy - MM - dd");
			String today = format.format(new Date());
			foodRepo.registerFood(food_id,"이름 없음",row,col,today,id); 
		}else {
			max_weight = new_food.getMax_weight();
		}
		
		if(max_weight == 0.0f || max_weight < weight) {
			max_weight = weight;
		}
		
		int success = repo.addFoodAt(id,food_id,row,col,weight,max_weight); 
		
		if (success == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("해당 음식정보가 존재하지 않습니다."));
		}
		
		return ResponseEntity.noContent().build();
	}
}
