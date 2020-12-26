package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.LoginParamDto;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserParamDto;
import com.ssafy.happyhouse.dto.UserResultDto;

public interface UserService {
	public UserDto login(LoginParamDto userDto);
	public UserResultDto register(UserDto userDto);
	
	public UserResultDto userList(UserParamDto userParamDto);
	public UserResultDto userListSearchWord(UserParamDto userParamDto);
	
	public UserResultDto userDetail(int userSeq);
	public UserResultDto userUpdate(UserDto userDto);
	public UserResultDto userDelete(int userSeq);
}
