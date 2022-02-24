package com.tripleaims.exhibition.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tripleaims.exhibition.dao.ArtistDAO;
import com.tripleaims.exhibition.dto.ArtistDTO;
import com.tripleaims.exhibition.util.ExhibitionConfig;

@Service
@Transactional
public class ArtistService {
	
	@Autowired
	ArtistDAO dao;
	
	public String add(Map<String, Object> paramMap){
		
		StringBuilder returnMessage = new StringBuilder();
		LocalDate date = LocalDate.now();
		
		// 폴더생성
		String folderPath = ExhibitionConfig.PROFILE_PATH + "/" + date.getYear() + "/" + date.getDayOfMonth();
		File folder = new File(folderPath);
		if(!folder.exists()) folder.mkdirs();
		
		// 파일등록
		MultipartFile profile = (MultipartFile)paramMap.get("profile");
		if(profile.getSize() > 0) {
			System.out.println("file 등록");
			String realFilename = profile.getOriginalFilename();
			System.out.println(realFilename);
			String ext = realFilename.substring(realFilename.lastIndexOf('.')+1);
			String filename = "profile_" + date.toString() + "_" + folder.listFiles().length + "." + ext;
			
			File file = new File(folder, filename);
			boolean isFileUploaded = true;
			try {
				profile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				isFileUploaded = false;
			}
			if(!isFileUploaded) { // 파일등록 실패
				returnMessage.append("<script>\n");
				returnMessage.append("alert('프로필 이미지 등록 실패');\n");
				returnMessage.append("history.back();\n");
				returnMessage.append("</script>");
				return returnMessage.toString();
			}
		}
		
		// 작가등록
		
		
		
		return returnMessage.toString();
	}
	
	
	public Map<String, Object> selectList(ArtistDTO dto) {
		List<ArtistDTO> resultList = dao.selectListArtists(dto);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", resultList);
		return resultMap;
	}
	
}
