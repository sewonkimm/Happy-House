package com.ssafy.happyhouse.service;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.happyhouse.dto.NoticeDto;
import com.ssafy.happyhouse.dto.NoticeParamDto;
import com.ssafy.happyhouse.dto.NoticeResultDto;

public interface NoticeService {
	
	public NoticeResultDto noticeDetail(NoticeParamDto paramDto);
	public NoticeResultDto noticeInsert(NoticeDto dto, MultipartFile file);
	public NoticeResultDto noticeUpdate(NoticeDto dto, MultipartFile file);
	public NoticeResultDto noticeDelete(int noticeId);
	
	public NoticeResultDto noticeList(NoticeParamDto paramDto);
	public NoticeResultDto noticeListSearchWord(NoticeParamDto paramDto);
}
