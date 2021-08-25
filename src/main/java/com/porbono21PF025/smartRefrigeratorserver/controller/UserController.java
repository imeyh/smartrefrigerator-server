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

import com.porbono21PF025.smartRefrigeratorserver.dao.UserRepository;
import com.porbono21PF025.smartRefrigeratorserver.entity.UserInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor // final 객체를 Constructor Injection 해줌 (Autowired 역할)
@RequestMapping("/user")
public class UserController {
	
	private final UserRepository repo;
	
	
	@ApiOperation(value = "사용자 로그인", notes = "사용자 id와 비밀번호로 로그인합니다.")
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfo login(
			@ApiParam(value = "사용자 아이디", required = true)
			@PathVariable("id") String id,
			@ApiParam(value = "사용자 비밀번호", required = true)
			@RequestParam("password") String password) {
		return repo.findByIdAndPassword(id, password);
	}
	
	@ApiOperation(value = "사용자 생성", notes = "사용자 아이디, 비밀번호, 이름을 전달 받아 신규 사용자를 생성합니다.")
	@PostMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfo register(
			@ApiParam(value = "신규 사용자 아이디", required = true)
			@PathVariable("id") String id, 
			@ApiParam(value = "신규 사용자 비밀번호", required = true)
			@RequestParam("password") String password,
			@ApiParam(value = "신규 사용자 이름", required = true)
			@RequestParam("name") String name) {
		
		UserInfo user = repo.findById(id).orElse(null);
		
		if (user == null) {
			return repo.save(new UserInfo(id,password,name));
		}
		
		return null;
				
		
	}
	
	@ApiOperation(value = "사용자 삭제", notes = "사용자 아이디를 통해 사용자 정보를 삭제합니다.")
	@DeleteMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public void removeUserById(
			@ApiParam(value = "사용자 아이디", required = true)
			@PathVariable("id") String id) {
		repo.deleteById(id);
	}
	
	@ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보를 수정합니다.")
	@PatchMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public UserInfo updateUser(
			@ApiParam(value = "사용자 아이디", required = true)
			@PathVariable("id") String id, 
			@ApiParam(value = "수정할 사용자 비밀번호", required = true)
			@RequestParam("password") String password,
			@ApiParam(value = "수정할 사용자 이름", required = true)
			@RequestParam("name") String name) {
		return repo.save(new UserInfo(id,password,name));
	}
	
	@ApiOperation(value = "냉장고 등록", notes = "shlef id를 통해 사용자에게 냉장고를 등록합니다.")
	@Transactional // JPA 사용 시 update나 delete문을 쿼리로 직접 사용하는 경우 명 -> 트랜잭션을 직접처리 
	@PatchMapping(value="/connect/{id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	public int connectToShelf(
			@ApiParam(value = "사용자 아이디", required = true)
			@PathVariable("id") String id,
			@ApiParam(value = "등록할 냉장고(선반)의 NFC 태그 고유 번호", required = true)
			@RequestParam("shelf_id") String shelf_id) {
		return repo.connectToShelf(id, shelf_id);
	}
	
	
	
	
}
