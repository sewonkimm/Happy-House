package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserParamDto;

@Mapper
public interface UserDao {
	public UserDto login(String userEmail);
	public int register(UserDto userDto);
	
	public List<UserDto> userList(UserParamDto userParamDto);
	public int userListTotalCount();
	
	public List<UserDto> userListSearchWord(UserParamDto userParamDto);
	public int userListSearchWordTotalCount(UserParamDto userParamDto);
	
	public UserDto userDetail(int userSeq);
	public int userUpdate(UserDto userDto);	
	public int userDelete(int userSeq);	
}
