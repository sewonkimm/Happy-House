package com.ssafy.happyhouse.dto;

import java.util.List;

public class UserResultDto {
	private int result;
	private UserDto dto;
	private List<UserDto> list;
	private int count;
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public UserDto getDto() {
		return dto;
	}
	public void setDto(UserDto dto) {
		this.dto = dto;
	}
	public List<UserDto> getList() {
		return list;
	}
	public void setList(List<UserDto> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}