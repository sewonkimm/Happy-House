package com.ssafy.happyhouse.dto;

import java.time.LocalDateTime;

public class UserDto {
	private int userSeq;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userProfileImgUrl;
	private LocalDateTime userRegDt;
	private int userType;
	
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserProfileImgUrl() {
		return userProfileImgUrl;
	}
	public void setUserProfileImgUrl(String userProfileImgUrl) {
		if( userProfileImgUrl == null || "null".equals(userProfileImgUrl) || "".equals(userProfileImgUrl)) {
			this.userProfileImgUrl = "/img/noProfile.png";
		}else {
			this.userProfileImgUrl = userProfileImgUrl;
		}
	}
	public LocalDateTime getUserRegDt() {
		return userRegDt;
	}
	public void setUserRegDt(LocalDateTime userRegDt) {
		this.userRegDt = userRegDt;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	
}
