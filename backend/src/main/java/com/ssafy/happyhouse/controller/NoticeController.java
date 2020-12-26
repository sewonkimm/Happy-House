package com.ssafy.happyhouse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.happyhouse.dto.NoticeDto;
import com.ssafy.happyhouse.dto.NoticeParamDto;
import com.ssafy.happyhouse.dto.NoticeResultDto;
import com.ssafy.happyhouse.service.NoticeService;

@RestController
public class NoticeController {
	
	private static final int SUCCESS = 1;
	
	@Autowired
	NoticeService service;
	
	@GetMapping(value="/notice")
	private ResponseEntity<NoticeResultDto> noticeList(NoticeParamDto paramDto){
		
		NoticeResultDto resultDto;
		
		if( paramDto.getSearchWord() == null || paramDto.getSearchWord().isEmpty() ) {
			resultDto = service.noticeList(paramDto);
		}else {
			resultDto = service.noticeListSearchWord(paramDto);
		}
		
		if( resultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<NoticeResultDto>(resultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<NoticeResultDto>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/notice/{boardId}")
	private ResponseEntity<NoticeResultDto> noticeDetail(@PathVariable int boardId, HttpSession session){

		NoticeParamDto noticeParamDto = new NoticeParamDto();
		noticeParamDto.setBoardId(boardId);
		
		NoticeResultDto noticeResultDto = service.noticeDetail(noticeParamDto);

		if( noticeResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	@PostMapping(value="/notice")
	private ResponseEntity<NoticeResultDto> noticeInsert(
			NoticeDto noticeDto, 
			@RequestParam(value="file", required = false) MultipartFile file) {
		
		NoticeResultDto noticeResultDto = service.noticeInsert(noticeDto, file);
		
		if( noticeResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	@PostMapping(value="/notice/{boardId}") 
	private ResponseEntity<NoticeResultDto> noticeUpdate(
			NoticeDto noticeDto, 
			@RequestParam(value="file", required = false) MultipartFile file){

		NoticeResultDto noticeResultDto = service.noticeUpdate(noticeDto, file);
		
		if( noticeResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@DeleteMapping(value="/notice/{boardId}") 
	private ResponseEntity<NoticeResultDto> noticeDelete(@PathVariable(value="boardId") int boardId){
		NoticeResultDto noticeResultDto = service.noticeDelete(boardId);
		
		if( noticeResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<NoticeResultDto>(noticeResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
}
