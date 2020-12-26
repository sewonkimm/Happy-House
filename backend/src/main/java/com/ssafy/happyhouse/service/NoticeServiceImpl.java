package com.ssafy.happyhouse.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.happyhouse.dao.NoticeDao;
import com.ssafy.happyhouse.dto.NoticeDto;
import com.ssafy.happyhouse.dto.NoticeFileDto;
import com.ssafy.happyhouse.dto.NoticeParamDto;
import com.ssafy.happyhouse.dto.NoticeResultDto;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	NoticeDao dao;
	String uploadFolder = "upload";
	
	String uploadPath = System.getProperty("user.dir") + File.separator +
						"src" + File.separator +
						"main" + File.separator +
						"resources" + File.separator +
						"static";
	
	@Override
	public NoticeResultDto noticeDetail(NoticeParamDto paramDto) {
		
		NoticeResultDto resultDto = new NoticeResultDto();
		
		try {		
			NoticeDto dto = dao.noticeDetail(paramDto);
			List<NoticeFileDto> fileList = dao.noticeDetailFileList(dto.getBoardId());
			
			dto.setFileList(fileList);
			resultDto.setResult(1);
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult(-1);
		}
		
		return resultDto;
	}

	@Override
	public NoticeResultDto noticeInsert(NoticeDto dto, MultipartFile file) {
		
		NoticeResultDto resultDto = new NoticeResultDto();
		System.out.println(File.separator);
		
		try {
			dao.noticeInsert(dto);
			
			if(file != null && !file.isEmpty()) {
				int noticeId = dto.getBoardId();
				
				File uploadDir = new File(uploadPath + File.separator + uploadFolder);
				if(!uploadDir.exists()) uploadDir.mkdir();
				
				String fileName = file.getOriginalFilename();
				
				UUID uuid = UUID.randomUUID();
				
				String extension = FilenameUtils.getExtension(fileName);
				
				String savingFileName = uuid + "." + extension; 
				
				File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				
				System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				file.transferTo(destFile);
		    
			    NoticeFileDto fileDto = new NoticeFileDto();
			    fileDto.setBoardId(noticeId);
			    fileDto.setFileName(fileName);
			    fileDto.setFileSize(file.getSize());
				fileDto.setFileContentType(file.getContentType());
				String noticeFileUrl = uploadFolder + "/" + savingFileName;
				fileDto.setFileUrl(noticeFileUrl);
				
				dao.noticeFileInsert(fileDto);
				
				// noticeDetailFileList
				List<NoticeFileDto> fileList = dao.noticeDetailFileList(dto.getBoardId());
				if(fileList == null || fileList.size() == 0) {
					resultDto.setFileList(null);
				}
				resultDto.setFileList(fileList);
			}			
			
			resultDto.setResult(1);
			resultDto.setDto(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setResult(-1);
		}
		
		return resultDto;
	}

	@Override
	public NoticeResultDto noticeUpdate(NoticeDto dto, MultipartFile uploadFile) {
		
		NoticeResultDto resultDto = new NoticeResultDto();

		try {
			dao.noticeUpdate(dto);
			
			if( uploadFile != null && ! uploadFile.isEmpty() ) {
										
				File uploadDir = new File(uploadPath + File.separator + uploadFolder);
				if (!uploadDir.exists()) uploadDir.mkdir();
				
		    	List<String> fileUrlList = dao.noticeFileUrlDeleteList(dto.getBoardId());	
				for(String fileUrl : fileUrlList) {	
					File file = new File(uploadPath + File.separator, fileUrl);
					if(file.exists()) {
						file.delete();
					}
				}

		    	dao.noticeFileDelete(dto.getBoardId());
				
		    	String fileName = uploadFile.getOriginalFilename();
	    
				UUID uuid = UUID.randomUUID();

				String extension = FilenameUtils.getExtension(fileName);
			
				String savingFileName = uuid + "." + extension;

				File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				uploadFile.transferTo(destFile);

			    NoticeFileDto fileDto = new NoticeFileDto();
			    fileDto.setBoardId(dto.getBoardId());
			    fileDto.setFileName(fileName);
			    fileDto.setFileSize(uploadFile.getSize());
				fileDto.setFileContentType(uploadFile.getContentType());
				String noticeFileUrl = uploadFolder + "/" + savingFileName;
				fileDto.setFileUrl(noticeFileUrl);
				
				dao.noticeFileInsert(fileDto);
			}
			resultDto.setResult(1);
			
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult(-1);
		}
		
		return resultDto;
		
	}

	@Override
	public NoticeResultDto noticeDelete(int noticeId) {
		
		NoticeResultDto resultDto = new NoticeResultDto();
		
		try {
			List<String> fileUrlList = dao.noticeFileUrlDeleteList(noticeId);
			for(String fileUrl : fileUrlList) {
				File file = new File(uploadPath + File.separator, fileUrl);				
				if(file.exists()) {
					file.delete();
				}
			}
			
			dao.noticeFileDelete(noticeId);
			dao.noticeDelete(noticeId);		
			resultDto.setResult(1);
			
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult(-1);
		}
		
		return resultDto;
	}

	@Override
	public NoticeResultDto noticeList(NoticeParamDto paramDto) {
		
		NoticeResultDto resultDto = new NoticeResultDto();
		
		try {
			List<NoticeDto> list = dao.noticeList(paramDto);
			// noticeDetailFileList
			for(int i=0; i<list.size(); i++) {
				List<NoticeFileDto> fileList = dao.noticeDetailFileList(list.get(i).getBoardId());
				if(fileList == null || fileList.size() == 0) {
					list.get(i).setFileList(null);
				}
				list.get(i).setFileList(fileList);
			}
			
			int count = dao.noticeListTotalCount();			
			resultDto.setList(list);
			resultDto.setCount(count);
			resultDto.setResult(1);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult(-1);
		}
		
		return resultDto;
	}

	@Override
	public NoticeResultDto noticeListSearchWord(NoticeParamDto paramDto) {

		NoticeResultDto resultDto = new NoticeResultDto();
		
		try {
			List<NoticeDto> list = dao.noticeListSearchWord(paramDto);
			// noticeDetailFileList
			for (int i = 0; i < list.size(); i++) {
				List<NoticeFileDto> fileList = dao.noticeDetailFileList(list.get(i).getBoardId());
				if (fileList == null || fileList.size() == 0) {
					list.get(i).setFileList(null);
				}
				list.get(i).setFileList(fileList);
			}
						
			int count = dao.noticeListSearchWordTotalCount(paramDto);
			
			resultDto.setList(list);
			resultDto.setCount(count);
			
			resultDto.setResult(1);
		
		}catch(Exception e) {
			e.printStackTrace();
			resultDto.setResult(-1);
		}
		
		return resultDto;
	}

}
