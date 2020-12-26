package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.LoginParamDto;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserParamDto;
import com.ssafy.happyhouse.dto.UserResultDto;
import com.ssafy.happyhouse.service.JwtService;
import com.ssafy.happyhouse.service.UserService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class UserController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	UserService userService;
	
	private static final int SUCCESS = 1;
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping(value="/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginParamDto dto, HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		UserDto userDto = userService.login(dto);
		try {
			if(userDto != null) {	// 로그인 성공했다면 토큰을 생성
				String token = jwtService.create(userDto);
				logger.trace("로그인 토큰정보 : {}", token);
				
//				토큰 정보는 response의 헤더로 보내고 나머지는 Map에 담는다.
				resultMap.put("auth-token", token);
				resultMap.put("user-email", userDto.getUserEmail());
				resultMap.put("user-name", userDto.getUserName());
				resultMap.put("user-seq", userDto.getUserSeq());
				status = HttpStatus.OK;
			} else {
				resultMap.put("message", "로그인 실패");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		System.out.println(new ResponseEntity<Map<String, Object>>(resultMap, status));
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping(value="/logout")
	public int logout(HttpSession session) {
		
		session.removeAttribute("userDto");
		session.invalidate();
		return SUCCESS;
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<UserResultDto> register(@RequestBody UserDto dto, HttpSession session){
		UserResultDto userResultDto = userService.register(dto);
		
		if( userResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/user")
	public ResponseEntity<UserResultDto> userList(UserParamDto userParamDto) {
		UserResultDto userResultDto = null;
		
		if( userParamDto.getSearchWord() == null || userParamDto.getSearchWord().isEmpty() ) {
			userResultDto = userService.userList(userParamDto);
		}else {
			userResultDto = userService.userListSearchWord(userParamDto);
		}
		
		if( userResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/user/{userSeq}")
	private ResponseEntity<UserResultDto> userDetail(@PathVariable int userSeq){
		
		UserResultDto userResultDto = userService.userDetail(userSeq);

		if( userResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	@PutMapping(value="/user") 
	private ResponseEntity<UserResultDto> userUpdate(@RequestBody UserDto dto){
		UserResultDto userResultDto = userService.userUpdate(dto);
		
		if( userResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@DeleteMapping(value="/user/{userSeq}") 
	private ResponseEntity<UserResultDto> userDelete(@PathVariable int userSeq){
		UserResultDto userResultDto = userService.userDelete(userSeq);
		
		System.out.println(userResultDto.getResult());
		if( userResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserResultDto>(userResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
}
