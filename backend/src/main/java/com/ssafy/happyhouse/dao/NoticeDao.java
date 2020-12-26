package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.NoticeDto;
import com.ssafy.happyhouse.dto.NoticeFileDto;
import com.ssafy.happyhouse.dto.NoticeParamDto;

@Mapper
public interface NoticeDao {
	
	public NoticeDto noticeDetail(NoticeParamDto noticeParamDto);
	public List<NoticeDto> noticeList(NoticeParamDto boardParamDto);
	public int noticeListTotalCount();
	
	public int noticeInsert(NoticeDto dto);
	public int noticeUpdate(NoticeDto dto);
	public int noticeDelete(int noticeId);
	
	public List<NoticeDto> noticeListSearchWord(NoticeParamDto boardParamDto);
	public int noticeListSearchWordTotalCount(NoticeParamDto boardParamDto);
	
	public List<NoticeFileDto> noticeDetailFileList(int noticeId);
	public int noticeFileInsert(NoticeFileDto dto);
	public int noticeFileDelete(int noticeId);
	public List<String> noticeFileUrlDeleteList(int noticeId);	
}
