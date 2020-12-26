package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.UserDao;
import com.ssafy.happyhouse.dto.LoginParamDto;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.dto.UserParamDto;
import com.ssafy.happyhouse.dto.UserResultDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	@Override
	public UserDto login(LoginParamDto dto) {
		UserDto userDto = userDao.login(dto.getUserEmail());

		// 비밀번호 확인
		if (userDto != null && userDto.getUserPassword().equals(dto.getUserPassword())) {
			return userDto;
		} else {
			return null;
		}
	}

	@Override
	public UserResultDto register(UserDto userDto) {
		
		UserResultDto userResultDto = new UserResultDto();
		
		if(userDao.register(userDto) == 1) {
			userResultDto.setResult(SUCCESS);
			userResultDto.setDto(userDto);
		}
		else userResultDto.setResult(FAIL);
		return userResultDto;
	}

	@Override
	public UserResultDto userList(UserParamDto userParamDto) {
		UserResultDto userResultDto = new UserResultDto();
		
		try {
			List<UserDto> list = userDao.userList(userParamDto);
			int count = userDao.userListTotalCount();
			userResultDto.setList(list);
			userResultDto.setCount(count);
			userResultDto.setResult(SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}

	@Override
	public UserResultDto userListSearchWord(UserParamDto userParamDto) {
		UserResultDto userResultDto = new UserResultDto();
		
		try {
			List<UserDto> list = userDao.userListSearchWord(userParamDto);
			int count = userDao.userListSearchWordTotalCount(userParamDto);

			userResultDto.setList(list);
			userResultDto.setCount(count);
			userResultDto.setResult(SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}

		return userResultDto;
	}

	@Override
	public UserResultDto userDetail(int userSeq) {
		UserResultDto userResultDto = new UserResultDto();

		try {
			UserDto userDto = userDao.userDetail(userSeq);
			userResultDto.setDto(userDto);
			userResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}

		return userResultDto;
	}

	@Override
	public UserResultDto userUpdate(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();

		try {
			userDao.userUpdate(userDto);
			userResultDto.setResult(SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}

		return userResultDto;
	}

	@Override
	public UserResultDto userDelete(int userSeq) {
		UserResultDto userResultDto = new UserResultDto();
		
		try {
			userDao.userDelete(userSeq);
			userResultDto.setResult(SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}

}
