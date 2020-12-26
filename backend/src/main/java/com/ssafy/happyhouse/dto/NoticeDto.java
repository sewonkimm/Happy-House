package com.ssafy.happyhouse.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class NoticeDto {
	private int boardId;
	private int userSeq;
	private String userName;
	private String userProfileImgUrl;
	private String title;
	private String content;
	private LocalDateTime regDt;

	private List<NoticeFileDto> fileList;

	public NoticeDto() {};

	public NoticeDto(int userSeq, String title, String content) {
		this.userSeq = userSeq;
		this.title = title;
		this.content = content;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

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

	public String getUserProfileImgUrl() {
		return userProfileImgUrl;
	}

	public void setUserProfileImgUrl(String userProfileImgUrl) {

		if (userProfileImgUrl == null || "null".equals(userProfileImgUrl) || "".equals(userProfileImgUrl)) {
			this.userProfileImgUrl = "/img/noProfile.png";
		} else {
			this.userProfileImgUrl = userProfileImgUrl;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getRegDt() {
		return regDt;
	}

	public void setRegDt(Date regDt) {
		this.regDt = LocalDateTime.ofInstant(regDt.toInstant(), ZoneId.systemDefault()); // for Mybatis Date Mapping
	}

	public List<NoticeFileDto> getFileList() {
		return fileList;
	}

	public void setFileList(List<NoticeFileDto> fileList) {
		this.fileList = fileList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[BoardDto - boardId : ");
		sb.append(this.boardId);
		sb.append(", userSeq : ");
		sb.append(this.userSeq);
		sb.append(", userName : ");
		sb.append(this.userName);
		sb.append(", userProfileImgUrl : ");
		sb.append(this.userProfileImgUrl);
		sb.append(", title : ");
		sb.append(this.title);
		sb.append(", content : ");
		sb.append(this.content);
		sb.append(", regDt : ");
		sb.append(this.regDt);
		sb.append("]");

		return sb.toString();
	}
}