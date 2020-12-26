package com.ssafy.happyhouse.dto;

import java.util.List;

public class NoticeResultDto {

	private int result;
	private NoticeDto dto;
	private List<NoticeFileDto> fileList;
	private List<NoticeDto> list;
	private int count;
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public NoticeDto getDto() {
		return dto;
	}
	public void setDto(NoticeDto dto) {
		this.dto = dto;
	}
	public List<NoticeFileDto> getFileList() {
		return fileList;
	}
	public void setFileList(List<NoticeFileDto> fileList) {
		this.fileList = fileList;
	}
	public List<NoticeDto> getList() {
		return list;
	}
	public void setList(List<NoticeDto> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}
